package com.github.anji.plus.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.anji.plus.common.MagicValueConstants;
import com.github.anji.plus.dto.BaseQueryBO;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 功能描述：
 *
 * @Author: peiyanni
 * @Date: 2021/2/3 12:42
 */
@Aspect
@Component
public class GaeaQueryAspect {

    // 配置织入点
    @Pointcut("@annotation(com.github.anji.plus.aop.GaeaQuery)")
    public void logPointCut() {
    }

    @Around(value = "logPointCut()")
    public void doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            GaeaQuery gaeaQuery = getAnnotationLog(joinPoint);
            if (null == gaeaQuery) {
                joinPoint.proceed(joinPoint.getArgs());
                return;
            }
            //需要进行高级查询条件的封装，
            String jsonObj = argsArrayToJsonObj(joinPoint.getArgs());
            if (StringUtils.isEmpty(jsonObj)) {
                joinPoint.proceed(joinPoint.getArgs());
                return;
            }
            BaseQueryBO baseQueryBO = JSON.parseObject(jsonObj, BaseQueryBO.class);
            //是否有常用查询条件
            Long commonId = baseQueryBO.getCommonId();
            if (null != commonId) {

            }


        } catch (Exception e) {

        }

    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private GaeaQuery getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(GaeaQuery.class);
        }
        return null;
    }


    /**
     * 参数获取 BaseQueryBO转换为json字符串
     */
    private String argsArrayToJsonObj(Object[] paramsArray) {
        String jsonObj = null;
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
                if (!(paramsArray[i] instanceof BaseQueryBO)) {
                    continue;
                }
                return JSON.toJSONString(paramsArray[i]);
            }
        }
        return jsonObj;
    }


}
