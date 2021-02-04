package com.github.anji.plus.gaea.curd.dto;

import java.lang.annotation.*;

/**
 * 查询条件注解
 * @author lr
 * @since 2021-01-12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Query {
    /**
     * 查询条件，默认相等
     * @return
     */
    QueryEnum value() default QueryEnum.EQ;

    /**
     * 是否参与where条件，默认是true
     * @return
     */
    boolean where() default true;

    /**
     * 查询字段，当前字段的值取查表中哪个字段
     * @return
     */
    String column() default "";
}
