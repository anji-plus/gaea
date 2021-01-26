package com.anjiplus.gaea.log.component;

import com.anji.plus.starter.utils.ApplicationContextUtils;
import com.anjiplus.gaea.log.GaeaAuditLogProperties;
import com.anjiplus.gaea.log.event.AuditLogApplicationEvent;
import com.anjiplus.gaea.log.LogOperation;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lr
 * @since 2021-01-18
 */
public class LogInterceptor extends HandlerInterceptorAdapter {

    private GaeaAuditLogProperties gaeaAuditLogProperties;

    public LogInterceptor(GaeaAuditLogProperties gaeaAuditLogProperties) {
        this.gaeaAuditLogProperties = gaeaAuditLogProperties;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Method method = handlerMethod.getMethod();

        Map<String, String[]> parameterMap = request.getParameterMap();

        Map<String, String> params = parameterMap.entrySet().stream()
                .collect(Collectors.toMap(k -> k.getKey(), value -> value.getValue()[0]));

        System.out.println("*************************盖亚组件自带******************************");
        System.out.println();
        System.out.println("请求参数:" + params);
        System.out.println("日志监控：" + method.getName());
        System.out.println();
        System.out.println("*************************盖亚组件自带******************************");
        System.out.println();

        LogOperation logOperation = new LogOperation();
        logOperation.setMethod(method.getName());
        logOperation.setParmas(params.toString());
        logOperation.setPath(request.getRequestURI());

        if (gaeaAuditLogProperties.isPublishEvent()) {
            ApplicationContextUtils.publishEvent(new AuditLogApplicationEvent(logOperation));
        }

    }
}
