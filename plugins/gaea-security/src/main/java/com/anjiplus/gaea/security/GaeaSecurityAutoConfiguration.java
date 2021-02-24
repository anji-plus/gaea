package com.anjiplus.gaea.security;

import com.anjiplus.gaea.security.handler.GaeaFilterExceptionHandler;
import com.anjiplus.gaea.security.security.GaeaUserDetailsService;
import com.anjiplus.gaea.security.security.JwtTokenAuthenticationFilter;
import com.github.anji.plus.gaea.annotation.condition.ConditionalOnGaeaComponent;
import com.anjiplus.gaea.security.security.SecurityConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 盖亚自动装配类
 * @AutoConfigurationPackage 添加当前类所在包加入到AutoConfigurationPackages，保证该包及其子包下的实体类被扫描到
 * @author lr
 * @since 2021-01-11
 */

@ConditionalOnClass(WebSecurityConfigurerAdapter.class)
@AutoConfigurationPackage
@ComponentScan(basePackages = {"com.anjiplus.gaea.security.bean"})
@EnableConfigurationProperties(GaeaSecurityProperties.class)
@ConditionalOnGaeaComponent(GaeaSecurityProperties.COMPONENT_NAME)
@Import(SecurityConfiguration.class)
public class GaeaSecurityAutoConfiguration {

    /**
     * token校验过滤器
     * @return
     */
    @Bean
    public JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter() {
        return new JwtTokenAuthenticationFilter();
    }

    /**
     * 过滤器异常处理
     * @return
     */
    @Bean
    public GaeaFilterExceptionHandler filterExceptionHandler() {
        return new GaeaFilterExceptionHandler();
    }

    /**
     * 自定义，从数据库获取用户信息
     *
     * @return
     */
    @Bean
    public UserDetailsService gaeaUserDetailsService() {
        return new GaeaUserDetailsService();
    }
}
