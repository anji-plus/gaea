package com.anji.plus.gaea.common.enums;

/**
 * <pre>
 * BaseConst
 * </pre>
 * 高級查詢、动态查询的关系运算枚举
 *
 * @author peiyanni
 * @version DynamicQueryOperatorType.java
 */
public enum DynamicQueryOperatorType {

    EQ("EQ"), NE("NE"), GT("GT"), GE("GE"), LT("LT"), LE("LE"), IN("IN"), LIKE("LIKE");

    private static final DynamicQueryOperatorType[] VALUES = DynamicQueryOperatorType.values();


    /**
     * 内容
     */
    private final String text;

    DynamicQueryOperatorType(String text) {
        this.text = text;
    }

    /**
     * 获取枚举值
     *
     * @param text
     * @return
     */
    public static DynamicQueryOperatorType getEnum(String text) {
        for (DynamicQueryOperatorType p : VALUES) {
            if (p.text.equalsIgnoreCase(text)) {
                return p;
            }
        }
        return null;
    }
}
