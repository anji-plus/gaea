package com.anji.mirror.gateway.filter;

import com.anji.mirror.common.enums.RepCodeEnum;
import com.anji.mirror.common.model.GatewayResponse;
import com.anji.mirror.gateway.model.GatewayRequest;
import com.anji.mirror.gateway.utils.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

@Component
public class TimeFilter implements GlobalFilter, Ordered {

    private static Logger logger = LoggerFactory.getLogger(TimeFilter.class);

    //服务器允许的客户端时间误差，秒
    @Value("${customer.request.timestampGap:300}")
    private Integer requestTimestampGap;

    @Override
    public int getOrder() {
        return -4;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //根据第一个filter预处理的url，判断是否需要走本filter，不需要的直接放行。
        boolean shouldFilter = (boolean) exchange.getAttribute("shouldTimeFilter");
        if(shouldFilter == false){
            return chain.filter(exchange);
        }

        GatewayRequest gatewayRequest = (GatewayRequest)exchange.getAttribute("GatewayRequest");
        ServerHttpRequest request = exchange.getRequest();

        if(StringUtils.isBlank(gatewayRequest.getTime())){
            logger.error("request body 中time不能为空");
            return ResponseUtil.setResponseBody(exchange, GatewayResponse.fail(RepCodeEnum.GATEWAY_TIMESTAMP_NULL_ERROR));
        }
        Long timel = null;
        try{
            timel = Long.parseLong(gatewayRequest.getTime());
        }catch (Exception e){
            return ResponseUtil.setResponseBody(exchange, GatewayResponse.fail(RepCodeEnum.GATEWAY_TIME_FORMAT_ERROR));
        }
        // 验证时间戳
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        Timestamp requestTime = new Timestamp(timel);
        if(requestTimestampGap == null){
            requestTimestampGap = 300;
        }
        if (Math.abs((nowTime.getTime() - requestTime.getTime()) / 1000) > requestTimestampGap.intValue()) {
            return ResponseUtil.setResponseBody(exchange, GatewayResponse.fail(RepCodeEnum.GATEWAY_TIMESTAMP_ERROR));
        }
        logger.debug("--------------------------时间戳误差验证通过---------------");

        return chain.filter(exchange);
    }

}
