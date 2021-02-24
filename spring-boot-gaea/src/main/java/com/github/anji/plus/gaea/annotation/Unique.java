package com.github.anji.plus.gaea.annotation;

import java.lang.annotation.*;

/**
 * 单一索引索引
 * @author lr
 * @since 2021-01-12
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Unique {

    /**
     * 列
     * @return
     */
    String column() default "";

    /**
     * 当违反唯一索引时，返回的错误码
     * @return
     */
    String code() default "";
}
