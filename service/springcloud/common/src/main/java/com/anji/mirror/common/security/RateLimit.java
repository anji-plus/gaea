package com.anji.mirror.common.security;

import java.lang.annotation.*;

/**
 * 自定义限流注解
 * Created by raodeming on 2020/9/3.
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

    /**
     * 统计的时间窗口
     */
    int unitSecond() default 60;

    /**
     * 允许最大次数
     */
    int maximum() default 200;

    /**
     * 在单位时间窗口内，访问次数超过maximum，将冻结 frozenSecond 秒
     */
    int frozenSecond() default 1;
}
