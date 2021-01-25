package com.github.anji.plus.gaea.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.github.anji.plus.gaea.cache.CacheHelper;
import com.github.anji.plus.gaea.curd.extension.DatasourcePasswordFactoryPostProcessor;
import com.github.anji.plus.gaea.curd.mapper.injected.CustomSqlInjector;
import com.github.anji.plus.gaea.event.listener.ExceptionApplicationListener;
import com.github.anji.plus.gaea.event.listener.LoginApplicationListener;
import com.github.anji.plus.gaea.i18.MessageLocaleResolver;
import com.github.anji.plus.gaea.i18.MessageSourceHolder;
import com.github.anji.plus.gaea.intercept.AccessKeyInterceptor;
import com.github.anji.plus.gaea.utils.ApplicationContextUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 盖亚自动装配
 * @author lr
 * @since 2021-01-11
 */
@Configuration
@EnableConfigurationProperties(GaeaProperties.class)
public class GaeaAutoConfiguration {

    /**
     * Web配置
     */
    @Configuration
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
     * spring上下文工具类
     * @return
     */
    @Bean
    public ApplicationContextUtils applicationContextUtils() {
        return new ApplicationContextUtils();
    }


    @Bean
    public CacheHelper cacheHelper() {
        return new CacheHelper();
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
     * 数据库加密
     * @return
     */
    @Bean
    @ConditionalOnClass(DataSourceProperties.class)
    public DatasourcePasswordFactoryPostProcessor datasourcePasswordFactoryPostProcessor() {
        return new DatasourcePasswordFactoryPostProcessor();
    }

    /**
     * 持久层mybatis-plus自动装配
     *
     * @author lr
     * @since 2020-12-09
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

    /**
     * 国际化
     *
     * @author lr
     * @since 2020-12-09
     */
    @Configuration
    @ConditionalOnClass(LocaleResolver.class)
    @ConditionalOnMissingBean(MessageLocaleResolver.class)
    @ComponentScan(value = {"com.anji.plus.starter.exception.advice", "com.anji.plus.starter.advice"})
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
