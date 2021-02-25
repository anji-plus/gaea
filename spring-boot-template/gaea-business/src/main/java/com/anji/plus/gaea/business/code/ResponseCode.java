package com.anji.plus.gaea.business.code;

/**
 * 响应码
 * @author lr
 * @since 2021-02-22
 */
public interface ResponseCode {

    /**
     * 字典编码重复
     */
    String DICT_CODE_REPEAT = "500-00001";

    /**
     * 数字字典国际化标识不能为null
     */
    String DICT_CODE_LOCALE_NULL = "500-00002";
}
