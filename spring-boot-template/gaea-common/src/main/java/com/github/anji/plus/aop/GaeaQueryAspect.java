package com.github.anji.plus.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.anji.plus.common.MagicValueConstants;
import com.github.anji.plus.dto.BaseQueryBO;
import com.github.anji.plus.dto.DynamicQueryBo;
import com.github.anji.plus.feign.AuthServiceClient;
import com.github.anji.plus.service.commoncondition.ICommonConditionService;
import com.github.anji.plus.util.DynamicQueryUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 功能描述：
 * 高级查询相关
 * @Author: peiyanni
 * @Date: 2021/2/3 12:42
 */
@Aspect
@Component
@Slf4j
public class GaeaQueryAspect {
    @Autowired
    private ICommonConditionService commonConditionService;

    // 配置织入点
    @Pointcut("@annotation(com.github.anji.plus.aop.GaeaQuery)")
    public void logPointCut() {
    }

    @Around(value = "logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            //需要进行高级查询条件的封装，
            String jsonObj = argsArrayToJsonObj(joinPoint.getArgs());
            if (StringUtils.isEmpty(jsonObj)) {
                return joinPoint.proceed(joinPoint.getArgs());
            }
            JSONObject jsonObject=JSONObject.parseObject(jsonObj);
            Long commonId =jsonObject.getLong(MagicValueConstants.COMMONID);
            BaseQueryBO baseQueryBO=JSON.parseObject(jsonObj,BaseQueryBO.class);
            //是否有常用查询条件
            if (null != commonId) {
                List<DynamicQueryBo> dynamicQueryBoList= commonConditionService.getDynamicQueryBoListById(commonId,null);
                baseQueryBO.setDynamicQueryBos(dynamicQueryBoList);
            }
            QueryWrapper queryWrapper = DynamicQueryUtil.getSortQueryWrapper(baseQueryBO);
            jsonObject.put(MagicValueConstants.QUERYWRAPPER,queryWrapper);
            Object[] args=getArg(joinPoint.getArgs(),jsonObject);
            return joinPoint.proceed(args);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("--高级查询出现异常---");
            return joinPoint.proceed();
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

    private Object[] getArg(Object[] paramsArray,JSONObject newQueryObj){
        Object[] resultObject=new Object[paramsArray.length];
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
                if (paramsArray[i] instanceof BaseQueryBO) {
                    resultObject[i]=JSON.parseObject(newQueryObj.toJSONString(),paramsArray[i].getClass());
                }else{
                    resultObject[i]=paramsArray[i];
                }
            }
        }
        return resultObject;
    }

}
