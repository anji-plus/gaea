package com.github.anji.plus.gaea.exception;

/**
 * 异常构建类
 * @author lirui
 * @since 2021-01-08 10:29
 */
public class BusinessExceptionBuilder {

    /**
     * 构建code
     * @param code 异常码
     * @return
     */
    public static BusinessException build(String code){
        return new BusinessException(code);
    }

    /**
     * 构建code和args
     * @param code 异常码
     * @param args 异常参数
     * @return
     */
    public static BusinessException build(String code ,Object... args){
        return new BusinessException(code, args);
    }


}
