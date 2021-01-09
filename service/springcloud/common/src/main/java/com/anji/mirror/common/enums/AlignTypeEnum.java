package com.anji.mirror.common.enums;

public enum AlignTypeEnum {

    AUTO(0, "自动"),
    LEFT(1, "靠左"),
    CENTER(2, "居中"),
    RIGHT(3, "靠右");
    public final int    code;
    public final String label;

    AlignTypeEnum(int code, String label) {
        this.code = code;
        this.label = label;
    }
}
