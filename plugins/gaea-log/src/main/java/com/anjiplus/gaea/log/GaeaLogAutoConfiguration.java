package com.anjiplus.gaea.log;

import com.anjiplus.gaea.log.aspect.GaeaAuditLogAspect;
import com.anjiplus.gaea.log.config.GaeaAuditLogProperties;
import com.anji.plus.gaea.annotation.condition.ConditionalOnGaeaComponent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

/**
 * 日志组件
 * @author lr
 * @since 2021-01-15
 */
@Configuration
@EnableConfigurationProperties(GaeaAuditLogProperties.class)
@ConditionalOnGaeaComponent(GaeaAuditLogProperties.COMPONENT_NAME)
public class GaeaLogAutoConfiguration {
    @Bean
    public GaeaAuditLogAspect auditLogAspect(){
       return new GaeaAuditLogAspect();
    }
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
        //线程池维护线程的最少数量
        poolTaskExecutor.setCorePoolSize(5);

        //线程池维护线程的最大数量
        poolTaskExecutor.setMaxPoolSize(1000);

        //线程池所使用的缓冲队列
        poolTaskExecutor.setQueueCapacity(200);

        //线程池维护线程所允许的空闲时间
        poolTaskExecutor.setKeepAliveSeconds(30000);

        poolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        return poolTaskExecutor;
    }
}
