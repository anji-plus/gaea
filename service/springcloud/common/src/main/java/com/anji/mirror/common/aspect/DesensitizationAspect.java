package com.anji.mirror.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DesensitizationAspect {

    private static final Logger log = LoggerFactory.getLogger(DesensitizationAspect.class);

//    @Pointcut("execution(* com.haitong.nla.analysis.controller.*Controller.*(..))")
//    private void controllerMethodAspect() {
//    }


    @Pointcut("@annotation(com.anji.mirror.common.annotation.Desensitization)")
    public void desensitizationPointCut() {
    }

    @AfterReturning(pointcut = "desensitizationPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        System.out.println(String.valueOf(jsonResult));


    }
    @AfterThrowing(value = "desensitizationPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        System.out.println(e.getMessage());
    }


}
