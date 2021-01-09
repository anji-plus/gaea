package com.anji.mirror.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.anji.mirror.common.model.LogVO;
import com.anji.mirror.common.utils.StringUtils;
import com.anji.mirror.gateway.feign.AuthServiceFeignClient;
import com.anji.mirror.gateway.model.GatewayRequest;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class LogFilter implements GlobalFilter, Ordered {

    private static Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Autowired
    private AuthServiceFeignClient authServiceFeignClient;

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //根据第一个filter预处理的url，判断是否需要走本filter，不需要的直接放行。
        boolean shouldFilter = (boolean) exchange.getAttribute("shouldLogFilter");
        if(shouldFilter == false){
            return chain.filter(exchange);
        }

        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ServerHttpResponseDecorator response = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                String contentType = originalResponse.getHeaders().getFirst("Content-Type");
                if (Objects.equals(getStatusCode(), HttpStatus.OK) && body instanceof Flux
                        && org.apache.commons.lang3.StringUtils.isNotEmpty(contentType) && contentType.contains("json")) {
                    Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        // probably should reuse buffers
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        //释放掉内存
                        DataBufferUtils.release(dataBuffer);
                        try {
                            baos.write(content);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return bufferFactory.wrap(content);
                    }));
                }
                return super.writeWith(body);
            }

            @Override
            public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
                return writeWith(Flux.from(body).flatMapSequential(p -> p));
            }
        };


        return chain.filter(exchange.mutate().response(response).build())
                .then(Mono.fromRunnable(() -> {
                    try {
                        GatewayRequest gatewayRequest = (GatewayRequest)exchange.getAttribute("GatewayRequest");
                        //登录接口，用户名从data中取
                        //{"password":"VhMxwd1xNFroM1DEHSOI3g==","loginName":"admin","captchaVerification":"Qhi3o5FjpsTCP2mgqp6KVom6jMSLIvd0VaIg9SWxsOxuO2hte0CO+ME/boN3vxi+HhGqfAwoCK+tUs463TnPig=="}
                        String userName = gatewayRequest.getUserName();
                        String apiPath = gatewayRequest.getServletPath();
                        if(StringUtils.endsWith(apiPath, "/user/login") && gatewayRequest.getData() != null){
                            userName = gatewayRequest.getData().getString("loginName");
                        }
                        //获取请求参数
                        String reqDataStr = "";
                        if (gatewayRequest != null && gatewayRequest.getData() != null) {
                            reqDataStr = JSONObject.toJSONString(gatewayRequest.getData());
                        }

                        LogVO logVO = new LogVO();
                        logVO.setUserId(gatewayRequest.getUserId());
                        logVO.setUserName(userName);
                        logVO.setRequestUrl(apiPath);
                        logVO.setPageTitle("");//authService中log方法，会读取redis中每个页面的pageTitle
                        logVO.setRequestParam(reqDataStr);
                        logVO.setResponseParam("");
                        logVO.setSourceIp(gatewayRequest.getSourceIP());
                        logVO.setRequestTime(LocalDateTime.now());
                        try {
                            logVO.setResponseParam(baos.toString());
                        } catch (Exception e) {
                            logVO.setResponseParam("unknow");
                        }
                        authServiceFeignClient.saveOperatorLog(logVO);
                    } catch (Exception e) {
                    }
                }));
    }
}
