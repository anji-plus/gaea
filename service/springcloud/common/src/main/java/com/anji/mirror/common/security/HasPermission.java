package com.anji.mirror.common.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HasPermission {

    //@HasPermission("roleManage:edit | roleManage:add")
    //@HasPermission("roleManage:edit & roleManage:add")
    String value() default "";
}
