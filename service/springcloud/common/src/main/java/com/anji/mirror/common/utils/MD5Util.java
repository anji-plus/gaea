package com.anji.mirror.common.utils;

import java.security.MessageDigest;

/**
 * @author anji gaea teams
 * @version V1.0
 * @Title: MD5工具类
 * @Description: MD5工具类
 * @date 2018-10-16T23:18:46.352
 */
public final class MD5Util {

    private static MD5Util md5Util = null;

    private static final String MD5_SALT = "QAZWSXEDCRFV09876543210";

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
     * @author anji gaea teams
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
     * 获取指定字符串的md5值, md5(md5(str+salt))两次MD5
     * @author anji gaea teams
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
            String realResult = encrypt(result.toString()); // 第二次MD5
            return realResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        String s = "reqData" +
                "{\"chartId\":156,\"correlationId\":\"\",\"groupData\":\"\",\"opUserId\":56,\"showChartSize\":\"big\",\"showParticles\": null,\"showXMaxTimeStr\":\"\"}" +
                "time" +
                "1562814896291" +
                "token" +
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1NiIsImV4cCI6MTU2NTQwNjY3Mn0.0yrWiHn2wG9wXKMjyV0RlokBaFzTwGJtSLOFFRe--UdQAVOWkddw2G9v0oWU7fUdfvUu8VQQ1HTv4z8JRYUh9g";
        System.out.println(encrypt(s));
    }
}
