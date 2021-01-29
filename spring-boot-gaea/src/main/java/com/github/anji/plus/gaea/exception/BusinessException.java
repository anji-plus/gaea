package com.github.anji.plus.gaea.exception;

/**
 * 业务异常封装类
 * @author lirui
 * @since 2021-01-12
 */

public class BusinessException extends RuntimeException{
    /**
     * 异常码
     */
    private String code;

    /**
     * 异常参数
     */
    private Object[] args;

    BusinessException(String code) {
        this.code = code;
    }

    public BusinessException(String code, Object[] args) {
        this.code = code;
        this.args = args;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object[] getArgs() {
        if(args != null) {
            return args.clone();
        }else {
            return null;
        }

    }

    public void setArgs(Object[] args) {
        if(args != null) {
            this.args = args.clone();
        }
    }
}
