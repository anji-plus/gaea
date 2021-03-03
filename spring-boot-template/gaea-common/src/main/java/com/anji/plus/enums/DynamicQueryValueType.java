package com.anji.plus.enums;

/**
 * <pre>
 * BaseConst
 * </pre>
 * 高級查詢、动态查询的值类型枚举
 *
 * @author peiyanni
 * @version DynamicQueryValueType.java
 */
public enum DynamicQueryValueType {

    CHARACTER(1,"字符串"), NUMBER(2, "数字"), DATE(3, "日期");

    private static final DynamicQueryValueType[] VALUES = DynamicQueryValueType.values();

    /**
     * 值
     */
    private final int value;

    /**
     * 内容
     */
    private final String text;

    DynamicQueryValueType(int value, String text) {
        this.value = value;
        this.text = text;
    }

    /**
     * 获取枚举值
     * @param v
     * @return
     */
    public static DynamicQueryValueType getEnum(Integer v) {
        if (v == null) {
            return null;
        }
        for (DynamicQueryValueType p : VALUES) {
            if (p.value == v.intValue()) {
                return p;
            }
        }
        return null;
    }
}
