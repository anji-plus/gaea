package com.anji.mirror.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.anji.mirror.common.enums.RepCodeEnum;
import com.anji.mirror.common.model.GatewayResponse;
import com.anji.mirror.gateway.model.GatewayRequest;
import com.anji.mirror.gateway.utils.ResponseUtil;
import com.anji.mirror.gateway.utils.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class SignFilter implements GlobalFilter, Ordered {

    private static Logger logger = LoggerFactory.getLogger(SignFilter.class);

    @Override
    public int getOrder() {
        return -3;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //根据第一个filter预处理的url，判断是否需要走本filter，不需要的直接放行。
        boolean shouldFilter = (boolean) exchange.getAttribute("shouldSignFilter");
        if(shouldFilter == false){
            return chain.filter(exchange);
        }

        GatewayRequest gatewayRequest = (GatewayRequest)exchange.getAttribute("GatewayRequest");
        ServerHttpRequest request = exchange.getRequest();


        if (!validateSign(gatewayRequest)) {
            return ResponseUtil.setResponseBody(exchange, GatewayResponse.fail(RepCodeEnum.GATEWAY_SIGN_ERROR));
        }

        logger.debug("--------------------------验签通过");
        return chain.filter(exchange);
    }


    private boolean validateSign(GatewayRequest gatewayRequest) {

        JSONObject data = gatewayRequest.getData(); //参与签名，按key升序，排除null值，用&拼接
        String time = gatewayRequest.getTime(); //参与签名，在签名串开始
        String token = gatewayRequest.getToken(); //参与签名，在签名串末尾
        List<String> ignoreKeyList = gatewayRequest.getIgnoreKeyList();  //data中哪些key不参与签名

        //计算签名
        String insertString = String.format("time=%s", time);
        String afterString = String.format("&token=%s", token);
        String sign = SignUtil.md5Sign(data, insertString, afterString, ignoreKeyList);

        //验签
        boolean isValid = false;
        if (sign.equalsIgnoreCase(gatewayRequest.getSign())) {
            isValid = true;
        }
        return isValid;
    }
}
