package com.anji.plus.gaea.annotation;

import java.lang.annotation.*;

/**
 * 聚合索引
 * @author lr
 * @since 2021-01-12
 */

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UnionUnique {

    /**
     * 列
     * @return
     */
    String column() default "";

    /**
     * 组,必填
     * @return
     */
    String group();
}
