package com.anji.plus.config;

import com.anji.plus.runner.ApplicationInitRunner;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 权限模块配置类
 * @author lr
 * @since 2021-03-01
 */
@Configuration
public class AuthAutoConfiguration {

    /**
     * restful客户端
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 应用启动后执行，用于初始化请求信息到权限表中
     * @return
     */
    @Bean
    public ApplicationInitRunner applicationInitRunner() {
        return new ApplicationInitRunner();
    }



}
