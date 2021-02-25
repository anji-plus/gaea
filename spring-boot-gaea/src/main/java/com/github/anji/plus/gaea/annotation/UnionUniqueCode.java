package com.github.anji.plus.gaea.annotation;

import java.lang.annotation.*;

/**
 * 聚合索引错误提示
 * @author lr
 * @since 2021-01-12
 */

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(UnionUniqueCodes.class)
@Documented
public @interface UnionUniqueCode {

    /**
     * 组,必填
     * @return
     */
    String group();

    /**
     * 错误码，用于确定当出现重复时提示信息
     * @return
     */
    String code();
}
