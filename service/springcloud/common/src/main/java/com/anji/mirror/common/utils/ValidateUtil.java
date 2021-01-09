package com.anji.mirror.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {
    public static final String EMAIL_EXPRESSION = "^([_A-Za-z0-9-])+@(([A-Za-z0-9-])+\\.)+([a-zA-Z0-9]{2,4})+";

    public static final String MOBILE_EXPRESSION = "^1[3|4|5|6|7|8][0-9]{9}";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 验证字符串是否为空值
     * 
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (null == str) || (str.trim().length() == 0);
    }

    public static boolean isNotEmpty(String str) {
        return (str != null) && (str.length() > 0);
    }

    public static boolean isNotEmpty(Number id) {
        return id != null;
    }

    /**
     * 验证数组
     * 
     * @param array
     * @return
     */
    public static boolean isEmpty(Object[] array) {
        return (array == null) || (array.length == 0);
    }

    /**
     * 验证集合
     * 
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null) || (collection.size() == 0);
    }

    /**
     * 验证map是否为空
     * 
     * @param map
     * @return
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null) || (map.size() == 0);
    }

    public static boolean isLong(String str) {
        if (isEmpty(str)) {
            return false;
        }
        try {
            Long.valueOf(str);
        } catch (NumberFormatException ne) {
            return false;
        }
        return true;
    }

    /**
     * 验证是否为日期类型
     * 
     * @param value
     * @param locale
     * @return
     */
    public static boolean isDate(String value, Locale locale) {
        if (value == null) {
            return false;
        }

        DateFormat formatter = null;
        if (locale != null) {
            formatter = DateFormat.getDateInstance(3, locale);
        } else {
            formatter = DateFormat.getDateInstance(3, Locale.getDefault());
        }

        formatter.setLenient(false);
        try {
            formatter.parse(value);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    public static boolean isDate(String value, String datePattern, boolean strict) {
        if ((value == null) || (datePattern == null) || (datePattern.length() <= 0)) {
            return false;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        formatter.setLenient(false);
        try {
            formatter.parse(value);
        } catch (ParseException e) {
            return false;
        }

        return (!strict) || (datePattern.length() == value.length());
    }

    /**
     * 验证字符串是否为email地址
     * 
     * @param value
     * @return
     */
    public static boolean isEmail(String value) {
        return validExpression(value, EMAIL_EXPRESSION);
    }

    public static boolean isBankAccount(String value) {
        if (!validExpression(value, "^[0-9]{16,19}")) {
            return false;
        }
        LuhnUtil luhn = new LuhnUtil(value);

        if (luhn.check()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 正则表达试的验证
     * @param value
     * @param expression
     * @return
     */
    public static boolean validExpression(String value, String expression) {
        return value.matches(expression);
    }

    /**
     * 验证手机号码
     *
     * 移动号码段:139、138、137、136、135、134、147、150、151、152、157、158、159、178、182、183、184、187、188
     * 联通号码段:130、131、132、136、155、156、185、186、145、176
     * 电信号码段:133、153、173、177、180、181、189
     * 虚拟运营商:170、171
     * @param cellphone
     * @return
     */
    public static boolean isMobile(String value) {
        //return validExpression(value, MOBILE_EXPRESSION);

        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0-9])|(17[0,1,3,6-8]))\\d{8}$";
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(value);
        return matcher.matches();
    }
   
    /**
     * 验证是否为url地址
     * 
     * @param url
     * @return
     */
    public static boolean isUrl(String url) {
        if (isEmpty(url)) {
            return false;
        }
        String regEx = "^((https?|ftp|mms):\\/\\/)?([A-z0-9]+[_\\-]?[A-z0-9]+\\.)*[A-z0-9]+\\-?[A-z0-9]+\\.[A-z]{2,}(\\/.*)*\\/?";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(url);
        return matcher.matches();
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 验证一个字符串是否为IPV4
     * 是否能以小数点分成四段
     * 每段是否都是数字
     * 每段数字是否都在0-255之间
     * @param ip
     * @return
     */
    public static boolean isCorrectIP(String ip) {
        if (isEmpty(ip)) {
            return false;
        }
        String regEx = "(?<=(\\b|\\D))(((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)"
                +"|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)"
                +"|(25[0-5]))(?=(\\b|\\D))";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(ip);
        return matcher.matches();
    }
}