package com.anji.mirror.common.annotation;

import java.lang.annotation.*;

/**
 * 加在敏感字段字段上，实现解密、加密、脱敏
 * Created by qiufl on 2020/11/25.
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CryptoMask {

    /**
     * 是否脱敏
     */
    boolean needMask() default true;

    /**
     * 是否加密存储 true 加密 false 解密
     */
    boolean needEncrypt() default true;

    /**
     * 返回内容敏感字段加*
     * 加*开始位置
     */
    int start() default -1;

    /**
     * 返回内容敏感字段加*
     * 加*结束位置
     */
    int end() default -1;
}
