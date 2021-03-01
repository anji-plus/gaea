package com.anji.plus.gaea.init;

import com.anji.plus.gaea.controller.GaeaBootController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.anji.plus.gaea.constant.GaeaConstant.*;

/**
 * 获取所有请求Url，供权限管理使用
 * @author lr
 * @since 2021-02-26
 */
public class InitRequestUrlMappings {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Value("${spring.application.name:}")
    private String applicationName;


    /**
     * 获取当前应用中所有请求信息
     * @return
     */
    public List<RequestInfo> getRequestInfos() {
        //请求所有请求处理方法
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();

        List<RequestInfo> list = new ArrayList<>();

        handlerMethods.entrySet().stream().forEach(entry -> {
            RequestInfo requestInfo = new RequestInfo();
            HandlerMethod value = entry.getValue();
            //忽略到自带的GaeaBootController
            if (value.getBeanType() == GaeaBootController.class) {
                return;
            }

            requestInfo.setAuthority(value.getBean() + URL_SPLIT + value.getMethod().getName());
            requestInfo.setBeanName(value.getBean().toString());
            requestInfo.setApplicationName(applicationName);

            RequestMappingInfo requestMappingInfo = entry.getKey();

            Optional<RequestMethod> requestMethodOptional = requestMappingInfo.getMethodsCondition().getMethods().stream().findFirst();

            //判断请求方法是否存在
            if (!requestMethodOptional.isPresent()) {
                return;
            }

            //请求方法
            RequestMethod requestMethod = requestMethodOptional.get();

            Optional<String> pathOptional = requestMappingInfo.getPatternsCondition().getPatterns().stream().findFirst();

            if (!pathOptional.isPresent()) {
                return;
            }

            String path = pathOptional.get();

            //当出现动态参数时将/{xxx}替换为/**
            if (path.contains(URL_MARK)) {
                path = path.replaceAll(URL_REGEX, URL_REPLACEMENT);
            }

            requestInfo.setPath(requestMethod + URL_SPLIT + path);

            list.add(requestInfo);

        });

        return list;
    }

    /**
     * 请求信息
     */
    public static class RequestInfo {

        /**
         * 应用名称
         */
        private String applicationName;

        /**
         * 实例Bean
         */
        private String beanName;

        /**
         * 权限
         */
        private String authority;

        /**
         * 请求路径path
         */
        private String path;

        public String getApplicationName() {
            return applicationName;
        }

        public void setApplicationName(String applicationName) {
            this.applicationName = applicationName;
        }

        public String getAuthority() {
            return authority;
        }

        public void setAuthority(String authority) {
            this.authority = authority;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getBeanName() {
            return beanName;
        }

        public void setBeanName(String beanName) {
            this.beanName = beanName;
        }
    }
}
