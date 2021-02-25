package com.anji.plus.gaea.auth;

import com.anji.plus.gaea.annotation.enabled.EnabledGaeaConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * auth模块
 * @author lr
 * @since 2020-01-28
 */
@SpringBootApplication(scanBasePackages = {
        "com.anji.plus.gaea.auth",
        "com.anji.plus.gaea.common"
})
@EnableFeignClients
@EnabledGaeaConfiguration
public class AuthApplication {
    public static void main( String[] args ) {
        SpringApplication.run(AuthApplication.class);
    }

}
