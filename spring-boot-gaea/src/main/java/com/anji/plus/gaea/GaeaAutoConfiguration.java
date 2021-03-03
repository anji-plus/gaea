package com.anji.plus.gaea;

import com.alibaba.fastjson.JSONObject;
import com.anji.plus.gaea.bean.ResponseBean;
import com.anji.plus.gaea.cache.CacheHelper;
import com.anji.plus.gaea.code.ResponseCode;
import com.anji.plus.gaea.config.MybatisPlusMetaObjectHandler;
import com.anji.plus.gaea.constant.GaeaConstant;
import com.anji.plus.gaea.curd.mapper.injected.CustomSqlInjector;
import com.anji.plus.gaea.event.listener.ExceptionApplicationListener;
import com.anji.plus.gaea.event.listener.LoginApplicationListener;
import com.anji.plus.gaea.holder.UserContentHolder;
import com.anji.plus.gaea.holder.UserContext;
import com.anji.plus.gaea.i18.MessageLocaleResolver;
import com.anji.plus.gaea.i18.MessageSourceHolder;
import com.anji.plus.gaea.init.InitRequestUrlMappings;
import com.anji.plus.gaea.intercept.AccessKeyInterceptor;
import com.anji.plus.gaea.utils.ApplicationContextUtils;
import com.anji.plus.gaea.utils.JwtUtils;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;

/**
 * 盖亚自动装配
 * @author lr
 * @since 2021-01-11
 */
@Configuration
@EnableConfigurationProperties(GaeaProperties.class)
public class GaeaAutoConfiguration {

    /**
     * spring上下文工具类
     * @return
     */
    @Bean
    public ApplicationContextUtils applicationContextUtils() {
        return new ApplicationContextUtils();
    }

    @Bean
    @ConditionalOnClass(RedisAutoConfiguration.class)
    public CacheHelper cacheHelper() {
        return new CacheHelper();
    }


    /**
     * web服务器环境,网关不加载
     */
    @Configuration
    @ConditionalOnClass(WebMvcConfigurer.class)
    @ComponentScan(value = {"com.anji.plus.gaea.controller", "com.anji.plus.gaea.exception.advice"})
    public static class WebGaeaAutoConfiguration {
        /**
         * 获取当前应用所有的RequestMapping信息，用于权限配置
         * @return
         */
        @Bean
        public InitRequestUrlMappings initRequestUrlMappings() {
            return new InitRequestUrlMappings();
        }

        /**
         * 解析token用户名
         * @return
         */
        @Bean
        public FilterRegistrationBean registrationBean() {
            FilterRegistrationBean registrationBean = new FilterRegistrationBean();
            registrationBean.setFilter((request, response, chain) -> {

                if (request instanceof HttpServletRequest) {
                    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                    String authorization = httpServletRequest.getHeader(GaeaConstant.Authorization);
                    if (StringUtils.isNotBlank(authorization)) {
                        try {
                            String username = JwtUtils.getUsername(authorization);
                            UserContext userContext = new UserContext();
                            userContext.setUsername(username);
                            //放入上下文
                            UserContentHolder.setContext(userContext);
                        } catch (TokenExpiredException tokenExpiredException) {

                            ResponseBean responseBean = ResponseBean.builder().code(ResponseCode.FAIL_CODE).message("The Token has expired").build();
                            response.getWriter().print(JSONObject.toJSONString(responseBean));
                            return;
                        }

                    }
                }
                chain.doFilter(request, response);
            });
            registrationBean.addUrlPatterns("/*");
            registrationBean.setName("LogCostFilter");
            registrationBean.setOrder(1);
            return registrationBean;
        }

        /**
         * 国际化
         *
         * @author lr
         * @since 2021-01-01
         */
        @Configuration
        @ConditionalOnClass(LocaleResolver.class)
        @ConditionalOnMissingBean(MessageLocaleResolver.class)
        public class MessageI18AutoConfiguration {

            /**
             * 根据请求头识别国际化locale
             *
             * @return
             */
            @Bean
            public MessageLocaleResolver localeResolver() {
                return new MessageLocaleResolver();
            }

            /**
             * 国际化
             *
             * @return
             */
            @Bean
            public MessageSourceHolder messageSourceHolder() {
                return new MessageSourceHolder();
            }
        }
    }

    /**
     * 登录监听
     * @return
     */
    @Bean
    public LoginApplicationListener loginApplicationListener() {
        return new LoginApplicationListener();
    }

    /**
     * 异常监听
     * @return
     */
    @Bean
    public ExceptionApplicationListener exceptionApplicationListener() {
        return new ExceptionApplicationListener();
    }

    /**
     * Web配置
     */
    @Configuration
    @ConditionalOnClass(WebMvcConfigurer.class)
    public static class GaeaWebMvcConfigurer implements WebMvcConfigurer{

        /**
         * 拦截器
         * @param registry
         */
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            InterceptorRegistration interceptorRegistration = registry.addInterceptor(new AccessKeyInterceptor());
            interceptorRegistration.addPathPatterns("/**");
        }
    }

    /**
     * 持久层mybatis-plus自动装配
     *
     * @author lr
     * @since 2021-01-01
     */
    @Configuration
    @ConditionalOnClass(MybatisPlusAutoConfiguration.class)
    public class GaeaMybatisPlusAutoConfiguration {
        /**
         * 乐观锁，需要在version字段上加@Version
         *
         * @return
         */
        @Bean
        public OptimisticLockerInterceptor optimisticLockerInterceptor() {
            return new OptimisticLockerInterceptor();
        }

        /**
         * 填充sql
         *
         * @return
         */
        @Bean
        public CustomSqlInjector customSqlInjector() {
            return new CustomSqlInjector();
        }

        /**
         * 分页
         *
         * @return
         */
        @Bean
        public PaginationInterceptor paginationInterceptor() {
            return new PaginationInterceptor();
        }

        /**
         * 默认填充
         *
         * @return
         */
        @Bean
        public MybatisPlusMetaObjectHandler mybatisPlusMetaObjectHandler() {
            return new MybatisPlusMetaObjectHandler();
        }
    }
}
