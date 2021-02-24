package com.anjiplus.gaea.security.security;

import com.anjiplus.gaea.security.security.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.PartialUpdate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * security 安全配置
 *
 * @author lr
 * @since 2021-01-25
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

    @Autowired
    private UserDetailsService userDetailsService;


    /**
     * 自定义userDetailService
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * 未登录
     *
     * @return
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new GaeaAccessDeniedHandler();
    }

    /**
     * 权限校验未通过处理
     *
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new GaeaAuthenticationEntryPoint();
    }

    /**
     * 登录成功处理
     *
     * @return
     */
    @Bean
    public GaeaLoginSuccessHandler gaeaLoginSuccessHandler() {
        return new GaeaLoginSuccessHandler();
    }

    /**
     * 登录失败处理
     * @return
     */
    @Bean
    public GaeaLoginFailureHandler gaeaLoginFailureHandler() {
        return new GaeaLoginFailureHandler();
    }

    /**
     * 登出成功处理
     *
     * @return
     */
    @Bean
    public GaeaLogoutSuccessHandler gaeaLogoutSuccessHandler() {
        return new GaeaLogoutSuccessHandler();
    }


    /**
     * 登录逻辑处理
     * @return
     */
    @Bean
    public GaeaUsernamePasswordAuthenticationFilter gaeaUsernamePasswordAuthenticationFilter() {

        AuthenticationManager authenticationManager = null;
        try {
            authenticationManager = this.authenticationManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new GaeaUsernamePasswordAuthenticationFilter(authenticationManager,
                gaeaLoginSuccessHandler(), gaeaLoginFailureHandler());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login/**","/logout", "/health", "/user/loginCode/**").permitAll()
                .mvcMatchers(HttpMethod.GET,"/dict/item/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(gaeaLoginSuccessHandler())
                .failureHandler(gaeaLoginFailureHandler())
                .and()
                .logout().logoutSuccessHandler(gaeaLogoutSuccessHandler());

        http.addFilterBefore(
                gaeaUsernamePasswordAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
