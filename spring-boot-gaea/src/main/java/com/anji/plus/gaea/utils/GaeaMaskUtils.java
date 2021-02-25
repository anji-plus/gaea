package com.anji.plus.gaea.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 数据脱敏
 * @author lr
 * @since 2021-02-04
 */
public class GaeaMaskUtils {

    /**
     * 默认的脱敏规则
     */
    public static final String defaultPattern = "(\\w{1})\\w+(\\w{1})";

    /**
     * 数据脱敏
     * @param source
     * @param reg 正则表达式
     * @param replacement 替换表达式
     * @return
     */
    public static String mask(String source, String reg, String replacement) {
        return source.replaceAll(reg, replacement);
    }


    public static void main(String[] args) {
//        String mask = iphoneMask("13218911314");

//        String mask = emailMask("lr@163.com");
        String mask = "(\\w{1})\\w+(\\w{1})";


        String format = "(\\d{2,6}-?)\\d{4}";
        String mask1 = mask("121111", mask, "$1***$2");
        System.out.println(mask1);



        String s = StringUtils.leftPad("11", 4, '*');


        System.out.println(s);

    }
}
