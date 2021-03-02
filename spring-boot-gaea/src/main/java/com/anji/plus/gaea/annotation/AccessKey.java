package com.anji.plus.gaea.annotation;

import java.lang.annotation.*;

/**
 * 验证数据的唯一性
 * @author lr
 * @since 2021-01-12
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessKey {

    /**
     * 获取accessKey值的字段
     * @return
     */
    String key() default "id";
}
