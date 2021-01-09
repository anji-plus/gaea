package com.anji.mirror.common.utils;

public class StringUtils extends org.apache.commons.lang3.StringUtils{


    /**判断一个字符是否是汉字
     * @param c
     * @return 是汉字(true), 不是汉字(false)
     */
    public static boolean isChineseChar(char c) {
        return String.valueOf(c).matches("[\u4e00-\u9fa5]");
    }

    /**判断字符串是否包含中文
     * @param cs
     * @return
     */
    public static boolean containChinese(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (isChineseChar(cs.charAt(i))) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }


    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为星号<例子：李**>
     *
     * @param fullName 姓名
     * @return
     */
    public static String maskChineseName(String fullName) {
        if (StringUtils.isBlank(fullName)) {
            return "";
        }
        String name = StringUtils.left(fullName, 1);
        return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
    }

    /**
     * [中文姓名] 只显示姓氏，其他隐藏为星号<例子：欧阳娜娜  ： 欧阳**>
     *
     * @param familyName  姓氏
     * @param givenName   名字
     * @return
     */
    public static String maskChineseName(String familyName, String givenName) {
        if (StringUtils.isBlank(familyName) || StringUtils.isBlank(givenName)) {
            return "";
        }
        if(familyName.length()>1){
            String name = StringUtils.left(familyName, familyName.length());
            return StringUtils.rightPad(name, StringUtils.length(familyName+givenName), "*");
        }
        return maskChineseName(familyName + givenName);
    }

    /**
     * [身份证号] 显示最后四位，其他隐藏。共计18位或者15位。<例子：*************5762>
     *
     * @param cardNum
     * @return
     */
    public static String maskCardNum(String cardNum) {
        if (StringUtils.isBlank(cardNum)) {
            return "";
        }
        String num = StringUtils.right(cardNum, 4);
        return StringUtils.leftPad(num, StringUtils.length(cardNum), "*");
    }


    /**
     * [身份证号] 前六位，后四位，其他用星号隐藏每位1个星号<例子:451002********1647>
     *
     * @param cardNum
     * @return
     */
    public static String maskCardNumWithF6T4(String cardNum){
        if (StringUtils.isBlank(cardNum)) {
            return "";
        }
        return StringUtils.left(cardNum, 6).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(cardNum, 4), StringUtils.length(cardNum), "*"), "******"));
    }

    /**
     * [固定电话] 后四位，其他隐藏<例子：****1234>
     *
     * @param num
     * @return
     */
    public static String maskPhone(String num) {
        if (StringUtils.isBlank(num)) {
            return "";
        }
        return StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*");
    }

    /**
     * [手机号码] 前三位，后四位，其他隐藏<例子:138******1234>
     *
     * @param num
     * @return
     */
    public static String maskPhoneWithF3T4(String num) {
        if (StringUtils.isBlank(num)) {
            return "";
        }
        return StringUtils.left(num, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*"), "***"));
    }

    /**
     * [地址] 只显示到地区，不显示详细地址；我们要对个人信息增强保护<例子：北京市海淀区****>
     *
     * @param address
     * @param sensitiveSize
     *            敏感信息长度
     * @return
     */
    public static String maskAddress(String address, int sensitiveSize) {
        if (StringUtils.isBlank(address)) {
            return "";
        }
        int length = StringUtils.length(address);
        return StringUtils.rightPad(StringUtils.left(address, length - sensitiveSize), length, "*");
    }

    /**
     * [电子邮箱] 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示<例子:g**@163.com>
     *
     * @param email
     * @return
     */
    public static String maskEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return "";
        }
        int index = StringUtils.indexOf(email, "@");
        if (index <= 1)
            return email;
        else
            return StringUtils.rightPad(StringUtils.left(email, 1), index, "*").concat(StringUtils.mid(email, index, StringUtils.length(email)));
    }

    /**
     * [银行卡号] 前六位，后四位，其他用星号隐藏每位1个星号<例子:6222600**********1234>
     *
     * @param cardNum
     * @return
     */
    public static String maskBankCard(String cardNum) {
        if (StringUtils.isBlank(cardNum)) {
            return "";
        }
        return StringUtils.left(cardNum, 6).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(cardNum, 4), StringUtils.length(cardNum), "*"), "******"));
    }

    /**
     * [公司开户银行联号] 公司开户银行联行号,显示前两位，其他用星号隐藏，每位1个星号<例子:12********>
     *
     * @param code
     * @return
     */
    public static String maskCnapsCode(String code) {
        if (StringUtils.isBlank(code)) {
            return "";
        }
        return StringUtils.rightPad(StringUtils.left(code, 2), StringUtils.length(code), "*");
    }

    /**
     * 根据起止位置配置脱敏范围
     *
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static String mask(String str, int start, int end){
        if (StringUtils.isBlank(str)) {
            return "";
        }

        //脱敏没有设置起始位置，进入默认模式
        if (-1 == start  && -1 == end ) {
            //手机脱敏
            if (ValidateUtil.isMobile(str)) {
                return maskPhoneWithF3T4(str);
            }
            //长度15-19位，默认走银行卡脱敏
            else if (str.length() >= 15 && str.length() <=19) {
                return maskBankCard(str);
            }
            //邮箱脱敏
            else if (ValidateUtil.isEmail(str)) {
                return maskEmail(str);
            }
            //身份证脱敏
            else if (ValidateUtil.isIDCard(str)) {
                return maskCardNum(str);
            }
            //对字符串中间三分之一部分加密
            else {
                int len = str.length()/3;
                return str.substring(0, len) + org.apache.commons.lang3.StringUtils.leftPad("", len, "*") + str.substring(2*len, str.length());
            }
        }

        //非默认设置
        if (start >= end || start > str.length() || end > str.length() ) {
            throw new RuntimeException("起止位置参数配置错误或者超过字符串长度");
        }
        return str.substring(0, start) + org.apache.commons.lang3.StringUtils.leftPad("", end-start, "*") + str.substring(end, str.length());
    }

}
