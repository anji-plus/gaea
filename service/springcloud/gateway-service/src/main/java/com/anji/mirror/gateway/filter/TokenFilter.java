package com.anji.mirror.gateway.filter;

import com.anji.mirror.common.enums.RepCodeEnum;
import com.anji.mirror.common.model.GatewayResponse;
import com.anji.mirror.common.security.Authentication;
import com.anji.mirror.gateway.model.GatewayRequest;
import com.anji.mirror.gateway.feign.AuthServiceFeignClient;
import com.anji.mirror.gateway.utils.RequestUtil;
import com.anji.mirror.gateway.utils.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class TokenFilter implements GlobalFilter, Ordered {

    private static Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Autowired
    private AuthServiceFeignClient authServiceFeignClient;

    @Override
    public int getOrder() {
        return -2;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //根据第一个filter预处理的url，判断是否需要走本filter，不需要的直接放行。
        boolean shouldFilter = (boolean) exchange.getAttribute("shouldTokenFilter");
        if(shouldFilter == false){
            return chain.filter(exchange);
        }

        GatewayRequest gatewayRequest = (GatewayRequest)exchange.getAttribute("GatewayRequest");
        ServerHttpRequest request = exchange.getRequest();

        //判断token是否有效
        if(StringUtils.isBlank(gatewayRequest.getToken())){
            return ResponseUtil.setResponseBody(exchange, GatewayResponse.fail(RepCodeEnum.GATEWAY_AUTH_ERROR));
        }
        Authentication authentication = authServiceFeignClient.getUserAuthByToken(gatewayRequest.getToken(), gatewayRequest.getServletPath());
        if(!authentication.isTokenIsValid()){
            return ResponseUtil.setResponseBody(exchange, GatewayResponse.fail(RepCodeEnum.GATEWAY_TOKEN_ERROR));
        }
        //判断权限是否正常
        if(!authentication.isHasPermission()){
            return ResponseUtil.setResponseBody(exchange, GatewayResponse.fail(RepCodeEnum.GATEWAY_AUTH_ERROR));
        }

        gatewayRequest.setUserId(authentication.getUserId());
        gatewayRequest.setUserName(authentication.getLoginName());
        gatewayRequest.setOrgIdList(authentication.getOrgIdList());
        gatewayRequest.setOrgCodeList(authentication.getOrgCodeList());
        exchange.getAttributes().put("GatewayRequest", gatewayRequest);
        logger.debug("-------------------------- token & auth 验证通过");

        //转换请求实体，将GatewayRequest中参数，转换民RquestModel传递给后端服务
        ServerHttpRequestDecorator mutatedRequest = RequestUtil.setRequestBody(exchange, gatewayRequest);
        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

}
