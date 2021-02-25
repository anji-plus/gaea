package com.github.anji.plus.gaea.annotation;

import java.lang.annotation.*;

/**
 * Controller里的参数xxxParam里的字段有值时会自动添加到查询条件，
 * 有些情况下不需要将有值的加到查询条件，即可用此注解
 * @author lr
 * @since 2021-02-24
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ParamIgnore {
}
