package com.github.anji.plus.gaea.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 配置权限
 * @author lirui
 * @since 2020-11-23
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {

    /**
     * 权限名称
     * @return
     */
    String value() default "";

    /**
     * 是否激活权限校验，默认激活
     * @return
     */
    boolean enable() default true;
}
