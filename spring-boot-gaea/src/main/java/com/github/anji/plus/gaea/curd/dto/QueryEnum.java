package com.github.anji.plus.gaea.curd.dto;

/**
 * 查询条件
 * @author lr
 * @since 2021-01-12
 */
public enum QueryEnum {
    /**
     * 查询条件为相等
     */
    EQ,

    /**
     * 查询条件为like
     */
    LIKE,

    /**
     * 查询条件大于
     */
    GT,

    /**
     * 查询条件小于
     */
    LT,

    /**
     * 数据库IN
     */
    IN,

    /**
     *
     */
    BWT
}
