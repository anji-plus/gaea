package com.github.anji.plus.gaea.utils;

import com.github.anji.plus.gaea.exception.BusinessExceptionBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * 系统断言
 * @author lirui
 * @since 2021-01-12
 */
public abstract class GaeaAssert {

    /**
     * 目标对象判断非空，如果为空，抛出异常
     * @param value 判断的目标对象
     * @param code 异常码
     * @param args
     */
    public static void notNull(Object value,String code,Object... args) {
        if (value == null) {
            throw BusinessExceptionBuilder.build(code,args);
        }
    }

    /**
     * 目标字符串判断非空或非空字符串，是则抛出异常
     * @param value 判断的目标对象
     * @param code 异常码
     * @param args
     */
    public static void notEmpty(String value,String code,Object... args) {
        if (StringUtils.isBlank(value)) {
            throw BusinessExceptionBuilder.build(code,args);
        }
    }

    /**
     * 目标集合是否为空，是则抛出异常
     * @param value 判断的目标对象
     * @param code 异常码
     * @param args
     */
    public static void notEmpty(Collection value, String code, Object... args) {
        if (value == null || value.isEmpty()) {
            throw BusinessExceptionBuilder.build(code,args);
        }
    }


    /**
     * 目标表达式是否为true，否则抛出异常
     * @param value 判断的目标对象
     * @param code 异常码
     * @param args
     */
    public static void isTrue(boolean value, String code, Object... args) {
        if (!value) {
            throw BusinessExceptionBuilder.build(code,args);
        }
    }

    /**
     * 目标表达式是否为false，否则抛出异常
     * @param value 判断的目标对象
     * @param code 异常码
     * @param args
     */
    public static void isFalse(boolean value, String code, Object... args) {
        if (value) {
            throw BusinessExceptionBuilder.build(code,args);
        }
    }

}
