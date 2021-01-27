package com.github.anji.plus.gaea.constant;

/**
 * 操作枚举
 * @author lirui
 * @since 2020-11-23
 */
public enum BaseOperationEnum {

    /**
     * 插入操作枚举
     */
    INSERT(1),
    /**
     * 更新操作枚举
     */
    UPDATE(2),
    /**
     * 删除操作枚举
     */
    DELETE(3),
    /**
     * 批量删除操作枚举
     */
    DELETE_BATCH(4);


    private int value;

    BaseOperationEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }}
