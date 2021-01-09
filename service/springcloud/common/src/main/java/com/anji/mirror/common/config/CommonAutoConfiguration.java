package com.anji.mirror.common.config;

import com.anji.mirror.common.security.SecurityPermissionScan;
import com.anji.mirror.common.service.RedisService;
import com.anji.mirror.common.service.impl.RedisServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 在springboot中自动装配权限扫描器
 * @author anji gaea teams
 * @since 2020-08-20
 */
@Configuration
@AutoConfigureAfter({RedisTemplate.class, StringRedisTemplate.class})
@ConditionalOnMissingClass(value = {"org.springframework.cloud.gateway.config.GatewayAutoConfiguration"})
public class CommonAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(RedisService.class)
    public RedisService redisService(){
        return new RedisServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SecurityPermissionScan.class)
    public SecurityPermissionScan SecurityPermissionScan(){
        return new SecurityPermissionScan();
    }
}
