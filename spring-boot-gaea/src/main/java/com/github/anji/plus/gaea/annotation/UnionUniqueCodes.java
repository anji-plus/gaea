package com.github.anji.plus.gaea.annotation;

import java.lang.annotation.*;

/**
 * 聚合索引
 * @author lirui
 * @since 2020-11-23
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
