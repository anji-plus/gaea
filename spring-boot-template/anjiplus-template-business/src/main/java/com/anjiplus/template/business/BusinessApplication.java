package com.anjiplus.template.business;

import com.anji.plus.gaea.annotation.enabled.EnabledGaeaConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 业务模板
 * @author lr
 * @since 2021-02-03
 */
@SpringBootApplication
@EnableFeignClients
@EnabledGaeaConfiguration
public class BusinessApplication {
    public static void main( String[] args ) {
        SpringApplication.run(BusinessApplication.class);
    }
}
