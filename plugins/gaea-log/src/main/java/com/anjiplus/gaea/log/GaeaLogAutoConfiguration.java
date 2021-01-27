package com.anjiplus.gaea.log;

import com.anjiplus.gaea.log.aspect.GaeaAuditLogAspect;
import com.github.anji.plus.gaea.annotation.condition.ConditionalOnGaeaComponent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

}
