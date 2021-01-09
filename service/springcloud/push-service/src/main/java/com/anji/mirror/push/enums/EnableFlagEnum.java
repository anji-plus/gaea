package com.anji.mirror.push.enums;

import lombok.Getter;

/**
 * @author anji gaea teams
 * @since 2020/10/13 13:26
 * 禁用启用。0--已禁用 1--已启用
 */
@Getter
public enum EnableFlagEnum {

    DISABLE(0, "已禁用"),
    ENABLE(1, "已启用");

    private Integer codeValue;
    private String name;

    EnableFlagEnum(Integer codeValue, String name) {
        this.codeValue = codeValue;
        this.name = name;
    }

}
