package com.anji.mirror.push.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.anji.mirror.push.interceptor.IpInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    /**
     * config api response serialize use fastjson
     * datetime ---> timestamps
     *
     * @return
     */
    //@Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        fastConverter.setSupportedMediaTypes(Arrays.asList(
                new MediaType[]{
                        MediaType.APPLICATION_JSON_UTF8,
                        MediaType.TEXT_PLAIN,
                        MediaType.TEXT_HTML,
                        MediaType.TEXT_XML,
                        MediaType.APPLICATION_OCTET_STREAM}));
        fastConverter.setFeatures(SerializerFeature.DisableCircularReferenceDetect);
        return fastConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverter());
    }

    /**
     * 指定url进入拦截器(签名认证)
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipInterceptor()).addPathPatterns("/captcha/get");
    }

    @Value("${captcha.limit.ip.frequency:10000}")
    private String ipFrequency;

    @Bean
    public IpInterceptor ipInterceptor() {
        return new IpInterceptor(ipFrequency);
    }

//    @Value("${logger.monitor.api.url:https://mirror.anji-plus.com/api/monitor/httpCollect}")
//    private String loggerServiceUrl = "https://mirror.anji-plus.com/api/monitor/httpCollect";
//    @Value("${logger.monitor.api.encryptKey:zWsN6xZB8eSLKP1o}")
//    private String encryptKey = "zWsN6xZB8eSLKP1o";
//    // 所属项目编码，睛灵平台分配
//    private String projectCode = "mirror_prod";

//    @Bean
//    public LogClient logClient(){
//        LogClient logClient = new LogClient();
//        log.info("logClient,url:{}",loggerServiceUrl);
//        logClient.init(projectCode, loggerServiceUrl, encryptKey);
//        return logClient;
//    }

}
