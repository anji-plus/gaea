package com.github.anji.plus.gaea.annotation.resolver;

/**
 * 数据脱敏枚举
 *
 * @author lr
 * @since 2021-02-04
 */
public enum MaskEnum {

    /**
     * 手机号
     */
    IPHONE("(\\d{3})\\d{4}(\\d{4})","$1****$2"),

    /**
     * 座机
     */
    TEL("(\\d{2,6}-?)\\d{4}","$1****"),

    /**
     * 邮箱
     */
    EMAIL("(\\w{0,4})\\w+(@\\w+)", "$1*****$2"),

    /**
     * 银行卡号
     */
    ACCOUNT_NO("(\\d{4})\\d+(\\d{4})","$1****$2"),

    /**
     * 身份证
     */
    CARD_ID("(\\d{6})\\d+(\\d{4})","$1****$2"),


    /**
     * 通用
     */
    COMMON("(\\w{%s})\\w+(\\w{%s})","$1****$2");

    /**
     * 匹配表达式
     */
    private String pattern;

    /**
     * 替换星号位
     */
    private String replacement;

    MaskEnum(String pattern,String replacement) {
        this.pattern = pattern;
        this.replacement = replacement;
    }


    public String getPattern() {
        return pattern;
    }

    public String getReplacement() {
        return replacement;
    }}
