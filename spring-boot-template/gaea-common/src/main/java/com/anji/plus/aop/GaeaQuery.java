package com.anji.plus.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 功能描述：
 * 盖亚-高级查询aop
 * @Author: peiyanni
 * @Date: 2021/2/3 12:34
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Documented
public @interface GaeaQuery {
}
