package com.anji.mirror.gateway.utils;

import com.alibaba.fastjson.JSONObject;
import com.anji.mirror.gateway.model.GatewayRequest;
import io.netty.buffer.ByteBufAllocator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class RequestUtil {
    public static Logger logger = LoggerFactory.getLogger(RequestUtil.class);

    /**获取ip地址
     * @param request
     * @return
     */
    public static String getIpAddr(ServerHttpRequest request) {
        String ip = request.getHeaders().getFirst("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddress().getAddress().getHostAddress();
        }
        if(ip.contains(",")){
            String[] ipArray = ip.split(",");
            String userIP = ipArray[0];
            ip = userIP.trim();
        }
        return ip;
    }

    /** 设置请求头
     * @param exchange
     * @param headKey
     * @param headVal
     * @return
     */
    public static ServerWebExchange setHeader(ServerWebExchange exchange, String headKey, String headVal){
        if(StringUtils.isBlank(headKey)){
            return exchange;
        }
        ServerHttpRequest mutableReq = exchange.getRequest().mutate().header(headKey, headVal).build();
        ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
        return mutableExchange;
    }

    /** 设置请求头
     * @param exchange
     * @param map
     * @return
     */
    public static ServerWebExchange setHeader(ServerWebExchange exchange, Map<String,String> map){
        if(map == null || map.isEmpty()){
            return exchange;
        }

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        for(Map.Entry<String,String> entry : map.entrySet()){
            String key = entry.getKey();
            String val = entry.getValue();
            multiValueMap.add(key, val);
        }

        ServerHttpRequest mutableReq = exchange.getRequest().mutate().headers(httpHeaders -> {
            httpHeaders.addAll(multiValueMap);
        }).build();
        ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
        return mutableExchange;
    }

    /** 获取提交的请求体
     * @param exchange
     * @return
     */
    public static String getRequestString(ServerWebExchange exchange){
        AtomicReference<String> bodyRef = new AtomicReference<>();

        Flux<DataBuffer> requestBody = exchange.getRequest().getBody();
        requestBody.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            DataBufferUtils.release(buffer);
            bodyRef.set(charBuffer.toString());
        });
        String bodyString = bodyRef.get();
        return bodyString;
    }

    /** 设置请求体
     * @param exchange
     * @return
     */
    public static ServerHttpRequestDecorator setRequestBody(ServerWebExchange exchange, GatewayRequest gatewayRequest){
        JSONObject reqData= new JSONObject();

        reqData.put("currentPage", gatewayRequest.getCurrentPage());
        reqData.put("pageSize", gatewayRequest.getPageSize());
        reqData.put("orderBy", gatewayRequest.getOrderBy());

        reqData.put("opToken", gatewayRequest.getToken());
        reqData.put("opUserId", gatewayRequest.getUserId());
        reqData.put("opUserName", gatewayRequest.getUserName());
        reqData.put("opOrgIdList", gatewayRequest.getOrgIdList());
        reqData.put("opOrgCodeList", gatewayRequest.getOrgCodeList());
        reqData.put("opSourceIP", gatewayRequest.getSourceIP());
        reqData.put("opIsFrom", gatewayRequest.getIsFrom());
        reqData.put("data", JsonTrimUtil.jsonStrTrim(gatewayRequest.getData()));
        String requestBody = JSONObject.toJSONString(reqData);
        byte[] requestBodyBytes = requestBody.getBytes();

        ServerHttpRequestDecorator serverHttpRequestDecorator = new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public Flux<DataBuffer> getBody() {
                Flux<DataBuffer> requestBody = super.getBody();
                return requestBody.map(dataBuffer -> {
                    NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
                    DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(requestBodyBytes.length);
                    buffer.write(requestBodyBytes);
                    return buffer;
                });
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.addAll(super.getHeaders());
                httpHeaders.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(requestBodyBytes.length));
                return httpHeaders;
            }
        };

        return serverHttpRequestDecorator;
    }
}
