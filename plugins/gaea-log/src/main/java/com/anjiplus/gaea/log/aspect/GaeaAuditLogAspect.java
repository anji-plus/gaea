package com.anjiplus.gaea.log.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anjiplus.gaea.log.config.GaeaAuditLogProperties;
import com.anjiplus.gaea.log.annotation.GaeaAuditLog;
import com.anjiplus.gaea.log.event.AuditLogApplicationEvent;
import com.github.anji.plus.gaea.utils.ApplicationContextUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * 操作日志切面处理
 * Created by gaea on 2021/1/27.
 */
@Aspect
@Component
public class GaeaAuditLogAspect {
    private static final Logger log = LoggerFactory.getLogger(GaeaAuditLogAspect.class);
    @Autowired
    private GaeaAuditLogProperties gaeaAuditLogProperties;

    @Autowired
    RestTemplate restTemplate;

    // 配置织入点
    @Pointcut("@annotation(com.anjiplus.gaea.log.annotation.GaeaAuditLog)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try {
            // 获得注解
            GaeaAuditLog controllerLog = getAnnotationLog(joinPoint);
            if (null == controllerLog) {
                return;
            }
            LogOperation operLog = new LogOperation();
            // 返回参数
            operLog.setResponseParam(JSONObject.toJSONString(jsonResult));
            if (e != null) {
                operLog.setResponseParam(e.getMessage());
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            //设置url
            operLog.setRequestUrl(getRequest().getRequestURI());
            // 设置请求方式
            operLog.setRequestMethod(getRequest().getMethod());
            operLog.setRequestTime(new Date());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, operLog);
            log.info("---盖亚日志组件---解析数据--{}", JSON.toJSONString(operLog));
            restTemplateCallback(operLog,getRequest());
            if (gaeaAuditLogProperties.isPublishEvent()) {
                ApplicationContextUtils.publishEvent(new AuditLogApplicationEvent(this, operLog));
            }
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==盖亚日志组件异常=={}", e.getMessage());
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, GaeaAuditLog log, LogOperation operLog) throws Exception {
        // 设置标题
        operLog.setPageTitle(log.pageTitle());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, operLog);
        } else {
            operLog.setResponseParam(null);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, LogOperation operLog) throws Exception {
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operLog.setRequestParam(params);
            try {
                JSONObject jsonObject = JSONObject.parseObject(params);
                operLog.setSourceIp(jsonObject.getString("opSourceIP"));
            } catch (Exception e) {
                log.warn("解析请求json失败", e);
            }
        }else if(HttpMethod.GET.name().equals(requestMethod)){
            Map<String,String[]> paramMap= getRequest().getParameterMap();
            operLog.setRequestParam(JSON.toJSONString(paramMap));
        }else {
            Map<?, ?> paramsMap = (Map<?, ?>) getRequest().getAttribute("HandlerMapping" + ".uriTemplateVariables");
            if(null!=paramsMap){
                operLog.setRequestParam(paramsMap.toString());
            }
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private GaeaAuditLog getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(GaeaAuditLog.class);
        }
        return null;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
                if (!isFilterObject(paramsArray[i])) {
                    try {
                        Object jsonObj = JSON.toJSON(paramsArray[i]);
                        params += jsonObj.toString() + " ";
                    } catch (Exception e) {
                    }
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    public boolean isFilterObject(final Object o) {
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }

    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 微服务模式下，通过回调方法传输日志数据
     *
     * @param logOperation
     */
    private void restTemplateCallback(LogOperation logOperation,HttpServletRequest request) {
        String url = gaeaAuditLogProperties.getCallbackUrl();
        if (StringUtils.isEmpty(url)) {
            return;
        }
        try {
            log.info("--盖亚日志组件--回调方法--url={}", url);
            HttpHeaders headers_new = new HttpHeaders();
            headers_new.setContentType(MediaType.APPLICATION_JSON);
            headers_new.set("Accept", "application/json;charset=UTF-8");
            headers_new.set("Authorization",request.getHeader("Authorization"));
            HttpEntity entity = new HttpEntity(logOperation, headers_new);
            JSONObject responseBody = restTemplate.postForObject(url, entity, JSONObject.class);
            log.info("--盖亚日志组件--回调方法返回{}", responseBody);
        } catch (Exception e) {
            log.error("----盖亚日志组件回调出现异常{}------", e.getMessage());
        }
    }
}
