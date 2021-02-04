package com.github.anji.plus;

import com.github.anji.plus.gaea.annotation.enabled.EnabledGaeaConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * auth模块
 * @author lirui
 * @since 2020-01-28
 */
@SpringBootApplication
@EnabledGaeaConfiguration
public class AuthApplication {
    public static void main( String[] args ) {
        SpringApplication.run(AuthApplication.class);
    }
}
