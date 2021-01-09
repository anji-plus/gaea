package com.anji.mirror.common.annotation;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 * Created by raodeming on 2020/9/3.
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     *页面或按钮标题
     * @return 标题
     */
    String pageTitle() default "";

    /**
     * 是否保存请求的参数和响应参数
     */
    boolean isSaveRequestData() default true;
}
