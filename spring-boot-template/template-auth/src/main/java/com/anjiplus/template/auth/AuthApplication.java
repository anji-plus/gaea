package com.anjiplus.template.auth;

import com.anji.plus.gaea.annotation.enabled.EnabledGaeaConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * auth模块
 * @author lr
 * @since 2020-01-28
 */
@SpringBootApplication(scanBasePackages = {
        "com.anjiplus.template.auth",
        "com.anjiplus.template.common"
})
@EnableFeignClients
@EnabledGaeaConfiguration
public class AuthApplication {
    public static void main( String[] args ) {
        SpringApplication.run(AuthApplication.class);
    }

}
