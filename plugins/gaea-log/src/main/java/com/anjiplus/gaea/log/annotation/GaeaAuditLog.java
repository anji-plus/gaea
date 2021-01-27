package com.anjiplus.gaea.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义操作日志记录注解
 * Created by gaeateam on 2020/1/26.
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GaeaAuditLog {

    /**
     *页面或按钮标题
     * @return 标题
     */
    String pageTitle() default "";


}