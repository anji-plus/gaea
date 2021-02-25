package com.anji.plus.util;

import java.security.MessageDigest;

/**
 * @author peiyanni
 * @version V1.0
 * @Title: MD5工具类
 * @Description: MD5工具类
 * @date 2021-02-07
 */
public final class MD5Util {

    private static MD5Util md5Util = null;

    private static final String MD5_SALT = "gaea";

    public static synchronized MD5Util getInstance() {

        if (md5Util == null) {
            md5Util = new MD5Util();
        }
        return md5Util;
    }

    private MD5Util() {

    }

    /**
     * 获取指定字符串的md5值
     * @param dataStr 明文
     * @return String
     */
    public static String encrypt(String dataStr) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte[] s = m.digest();
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < s.length; i++) {
                result.append(Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6));
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取指定字符串的md5值, md5(str+salt)次MD5
     * @param dataStr 明文
     * @return String
     */
    public static String encryptBySalt(String dataStr) {
        try {
            dataStr = dataStr + MD5_SALT;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte[] s = m.digest();
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < s.length; i++) {
                result.append(Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6));
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
