package com.anji.plus.gaea.constant;

/**
 * 是否标识即0,1
 * @author lr
 * @since 2021-01-12
 */
public enum Enabled {

    YES(1),NO(0);

    private Integer value;

    Enabled(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }}
