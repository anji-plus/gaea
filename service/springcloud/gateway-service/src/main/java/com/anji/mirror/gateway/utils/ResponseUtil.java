package com.anji.mirror.gateway.utils;

import com.anji.mirror.common.model.GatewayResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;

public class ResponseUtil {

    public static Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

    public static Mono<Void> setResponseBody(ServerWebExchange exchange, GatewayResponse gatewayResponse) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        originalResponse.setStatusCode(HttpStatus.OK);
        originalResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        byte[] response = null;
        try
        {
            if(gatewayResponse == null){
                gatewayResponse = new GatewayResponse();
            }
            response = gatewayResponse.toJsonString().getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            logger.error("error: {}", e);
        }
        DataBuffer buffer = originalResponse.bufferFactory().wrap(response);
        return originalResponse.writeWith(Flux.just(buffer));
    }
}
