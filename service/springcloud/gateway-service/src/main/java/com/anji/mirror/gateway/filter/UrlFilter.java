package com.anji.mirror.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.anji.mirror.common.enums.RepCodeEnum;
import com.anji.mirror.common.model.GatewayResponse;
import com.anji.mirror.gateway.config.RequestConfig;
import com.anji.mirror.gateway.model.DeliverBox;
import com.anji.mirror.gateway.model.GatewayRequest;
import com.anji.mirror.gateway.utils.RequestUtil;
import com.anji.mirror.gateway.utils.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.DefaultServerRequest;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class UrlFilter implements GlobalFilter, Ordered {

    private static Logger logger = LoggerFactory.getLogger(UrlFilter.class);

    @Autowired
    private RequestConfig requestConfig;

    //网关透明传输的url，不做任何处理
    private Pattern TRANSPARENT_URLS_PATTERN;
    //不需要验证sign的接口
    private Pattern SKIP_VALIDATE_SIGN_PATTERN;
    //不需要验证time的接口
    private Pattern SKIP_VALIDATE_TIME_PATTERN;
    //不需要验证token的接口
    private Pattern SKIP_VALIDATE_TOKEN_PATTERN;
    //不需要验证auth的接口
    private Pattern SKIP_VALIDATE_AUTH_PATTERN;
    //不需要保存log的接口
    private Pattern SKIP_VALIDATE_LOG_PATTERN;
    //服务器拒绝访问ip名单
    private String rejectIPList;

    /** 根据黑名单，生成正则
     * @param skipUrlList
     * @return
     */
    private Pattern fitByList(List<String> skipUrlList){
        if(skipUrlList == null || skipUrlList.size() == 0){
            return Pattern.compile(".*().*");
        }
        StringBuffer patternString = new StringBuffer();
        patternString.append(".*(");

        skipUrlList.stream().forEach(url ->{
            patternString.append(url);
            patternString.append("|");
        });
        if(skipUrlList.size()>0){
            patternString.deleteCharAt(patternString.length()-1);
        }
        patternString.append(").*");

        return Pattern.compile(patternString.toString());
    }

    @Override
    public int getOrder() {
        return -5;
    }

    @PostConstruct
    private void postConstruct() {
        TRANSPARENT_URLS_PATTERN = fitByList(requestConfig.getTransparentUrls());
        SKIP_VALIDATE_SIGN_PATTERN = fitByList(requestConfig.getSkipValidateSign());
        SKIP_VALIDATE_TIME_PATTERN = fitByList(requestConfig.getSkipValidateTimestamp());
        SKIP_VALIDATE_TOKEN_PATTERN = fitByList(requestConfig.getSkipValidateToken());
        SKIP_VALIDATE_AUTH_PATTERN = fitByList(requestConfig.getSkipValidateAuth());
        SKIP_VALIDATE_LOG_PATTERN = fitByList(requestConfig.getSkipValidateLog());
        rejectIPList = requestConfig.getRejectIPList();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String ipAddr = RequestUtil.getIpAddr(request);
        String requestURL = request.getURI().toString();        // http://localhost:8080/auth-service/user/login
        String servletPath = request.getPath().value();         // /auth-service/user/login
        String requestMethod = request.getMethodValue();        // POST GET

        logger.info(String.format("--------------------------收到来自IP为 %s 的请求%s request to %s", ipAddr, requestMethod, requestURL));

        //透明传输
        boolean transparentFlag = TRANSPARENT_URLS_PATTERN.matcher(servletPath).matches();
        if(transparentFlag){
            exchange.getAttributes().put("shouldTimeFilter", false);
            exchange.getAttributes().put("shouldSignFilter", false);
            exchange.getAttributes().put("shouldTokenFilter", false);
            exchange.getAttributes().put("shouldAuthFilter", false);
            exchange.getAttributes().put("shouldLogFilter", false);
            return chain.filter(exchange);
        }

        //IP黑名单阻止访问
        if(StringUtils.isNotBlank(rejectIPList) && rejectIPList.indexOf(ipAddr)>=0){
            return ResponseUtil.setResponseBody(exchange, GatewayResponse.fail(RepCodeEnum.GATEWAY_REJECT_IP_ERROR));
        }

        Map<String,String> map = new HashMap<>();
        // get请求不过滤，非json不过滤，请求包原样转发
        // application/x-www-form-urlencoded, multipart/form-data, application/json
        if (StringUtils.equalsIgnoreCase(requestMethod,"GET")
                || request.getHeaders() == null
                || request.getHeaders().getContentType() == null
                || StringUtils.contains(request.getHeaders().getContentType().toString(), "application/json") == false){
            exchange.getAttributes().put("shouldTimeFilter", false);
            exchange.getAttributes().put("shouldSignFilter", false);
            exchange.getAttributes().put("shouldTokenFilter", false);
            exchange.getAttributes().put("shouldAuthFilter", false);
            exchange.getAttributes().put("shouldLogFilter", true);
            return chain.filter(exchange);
        }

        exchange.getAttributes().put("shouldTimeFilter", true);
        exchange.getAttributes().put("shouldSignFilter", true);
        exchange.getAttributes().put("shouldTokenFilter", true);
        exchange.getAttributes().put("shouldAuthFilter", true);
        exchange.getAttributes().put("shouldLogFilter", true);

        //读请求包
        ServerRequest serverRequest = new DefaultServerRequest(exchange);
        Mono<String> modifiedBody = serverRequest.bodyToMono(String.class)
                .flatMap(body -> {
                    //第一步，先执行到这行代码
                    resolvRequestBody(exchange, body, servletPath, ipAddr);
                    return Mono.just(body);
                });
        BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());
        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
        return bodyInserter.insert(outputMessage, new BodyInserterContext())
                .then(Mono.defer(() -> {
                    //第二步，再执行到这里
                    DeliverBox deliverBox = (DeliverBox)exchange.getAttributes().get("DeliverBox");
                    if(deliverBox.isFail()) {
                        return ResponseUtil.setResponseBody(exchange, deliverBox.getGatewayResponse());
                    }

                    ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(exchange.getRequest()) {
                        @Override
                        public HttpHeaders getHeaders() {
                            return super.getHeaders();
                        }
                        @Override
                        public Flux<DataBuffer> getBody() {
                            return outputMessage.getBody();
                        }
                    };

                    return chain.filter(exchange.mutate().request(decorator).build());
                }));

    }

    private void resolvRequestBody(ServerWebExchange exchange, String requestBody, String servletPath, String ipAddr){
        // 获取请求报文 {"currentPage":1, "pageSize":10, "data":{"password":"123456","userName":"admin"},"sign":"a304a7f296f565b6d2009797f68180f0","timestamp":"1542456453355","opToken":""}
        if(StringUtils.isBlank(requestBody)){
            exchange.getAttributes().put("DeliverBox", DeliverBox.fail(GatewayResponse.fail(RepCodeEnum.GATEWAY_PARM_ERROR)));
            return;
        }

        GatewayRequest gatewayRequest = JSONObject.parseObject(requestBody, GatewayRequest.class);
        gatewayRequest.setServletPath(servletPath);
        gatewayRequest.setSourceIP(ipAddr);
        exchange.getAttributes().put("GatewayRequest", gatewayRequest);
        logger.debug("--------------------------requestString:{}", requestBody);

        // 设置是否需要启用TimeFilter
        boolean shouldTimeFilter = !SKIP_VALIDATE_TIME_PATTERN.matcher(servletPath).matches();
        exchange.getAttributes().put("shouldTimeFilter", shouldTimeFilter);
        if(shouldTimeFilter && StringUtils.isBlank(gatewayRequest.getTime())){
            exchange.getAttributes().put("DeliverBox", DeliverBox.fail(GatewayResponse.fail(RepCodeEnum.GATEWAY_TIMESTAMP_NULL_ERROR)));
            return;
        }

        // 设置是否需要启用SignFilter
        boolean shouldSignFilter = !SKIP_VALIDATE_SIGN_PATTERN.matcher(servletPath).matches();
        exchange.getAttributes().put("shouldSignFilter", shouldSignFilter);
        if(shouldSignFilter){
            if(StringUtils.isBlank(gatewayRequest.getTime())){
                exchange.getAttributes().put("DeliverBox", DeliverBox.fail(GatewayResponse.fail(RepCodeEnum.GATEWAY_TIMESTAMP_NULL_ERROR)));
                return;
            }
            if(StringUtils.isBlank(gatewayRequest.getSign())){
                exchange.getAttributes().put("DeliverBox", DeliverBox.fail(GatewayResponse.fail(RepCodeEnum.GATEWAY_SIGN_NULL_ERROR)));
                return;
            }
            if(StringUtils.isBlank(gatewayRequest.getToken())){
                exchange.getAttributes().put("DeliverBox", DeliverBox.fail(GatewayResponse.fail(RepCodeEnum.GATEWAY_TOKEN_NULL_ERROR)));
                return;
            }
            if(gatewayRequest.getData() == null){
                exchange.getAttributes().put("DeliverBox", DeliverBox.fail(GatewayResponse.fail(RepCodeEnum.GATEWAY_DATA_NULL_ERROR)));
                return;
            }
        }

        // 设置是否需要启用TokenFilter
        boolean shouldTokenFilter = !SKIP_VALIDATE_TOKEN_PATTERN.matcher(servletPath).matches();
        exchange.getAttributes().put("shouldTokenFilter", shouldTokenFilter);
        if(shouldTokenFilter && StringUtils.isBlank(gatewayRequest.getToken())){
            exchange.getAttributes().put("DeliverBox", DeliverBox.fail(GatewayResponse.fail(RepCodeEnum.GATEWAY_TOKEN_NULL_ERROR)));
            return;
        }

        // 设置是否需要启用AuthFilter
        boolean shouldAuthFilter = !SKIP_VALIDATE_AUTH_PATTERN.matcher(servletPath).matches();
        exchange.getAttributes().put("shouldAuthFilter", shouldAuthFilter);

        // 设置是否需要启用LogFilter
        boolean shouldLogFilter = !SKIP_VALIDATE_LOG_PATTERN.matcher(servletPath).matches();
        exchange.getAttributes().put("shouldLogFilter", shouldLogFilter);

        exchange.getAttributes().put("DeliverBox", DeliverBox.success());
    }

}
