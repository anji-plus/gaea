package com.github.anji.plus.gaea.cache;

/**
 * redis中的key
 *
 * @author lirui
 * @since 2020-11-23
 */
public enum RedisKeyEnum {

    /**
     * 数据字典
     */
    DICT_PREFIX("base:dict:prefix:", "数据字典");

    public String key;
    public String message;

    RedisKeyEnum(String key, String message) {
        this.key = key;
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }
}
