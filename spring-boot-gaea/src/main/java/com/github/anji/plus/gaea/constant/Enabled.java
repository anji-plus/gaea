package com.github.anji.plus.gaea.constant;

/**
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
