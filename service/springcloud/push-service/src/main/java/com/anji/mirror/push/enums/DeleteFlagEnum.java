package com.anji.mirror.push.enums;

import lombok.Getter;

/**
 * @author anji gaea teams
 * @since 2020/10/13 13:26
 * 0--未删除 1--已删除
 */
@Getter
public enum DeleteFlagEnum {

    UNDELETED(0, "未删除"),
    DELETED(1, "已删除");

    private Integer codeValue;
    private String name;

    DeleteFlagEnum(Integer codeValue, String name) {
        this.codeValue = codeValue;
        this.name = name;
    }

}
