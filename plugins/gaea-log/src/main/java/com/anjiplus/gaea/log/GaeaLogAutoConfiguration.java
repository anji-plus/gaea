package com.anjiplus.gaea.log;

import com.anjiplus.gaea.log.component.LogInterceptor;
import com.github.anji.plus.gaea.annotation.condition.ConditionalOnGaeaComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 日志组件
 * @author lr
 * @since 2021-01-15
 */
@Configuration
@EnableConfigurationProperties(GaeaAuditLogProperties.class)
@ConditionalOnGaeaComponent(GaeaAuditLogProperties.COMPONENT_NAME)
public class GaeaLogAutoConfiguration {

    /**
     * 日志
     * @author lr
     * @since 2021-01-18
     */
    @Configuration
    public class LogWebMvcConfigurer implements WebMvcConfigurer {

        @Autowired
        private GaeaAuditLogProperties gaeaAuditLogProperties;

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new LogInterceptor(gaeaAuditLogProperties));
        }
    }

}
