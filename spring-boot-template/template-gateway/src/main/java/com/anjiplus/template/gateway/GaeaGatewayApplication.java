package com.anjiplus.template.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关启动类
 * @author lr
 * @since 2021-02-01
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GaeaGatewayApplication {
    public static void main( String[] args ) {
        SpringApplication.run(GaeaGatewayApplication.class);
    }
}
