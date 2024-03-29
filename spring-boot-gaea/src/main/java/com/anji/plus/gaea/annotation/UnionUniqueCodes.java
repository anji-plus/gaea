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
public @interface UnionUniqueCodes {

    /**
     * 列
     * @return
     */
    UnionUniqueCode[] value();
}
