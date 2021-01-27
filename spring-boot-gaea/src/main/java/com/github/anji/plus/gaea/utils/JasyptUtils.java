package com.github.anji.plus.gaea.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * 加解密工具类
 * @author lr
 * @since 2020-12-21
 */
public class JasyptUtils {

    /**
     * jasypt加密私钥
     */
    private static String jasyptPassword = "anji-plus";

    /**
     * 解密
     * @param str
     * @return
     */
    public static String encrypt(String str) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setPassword(jasyptPassword);
        return standardPBEStringEncryptor.encrypt(str);
    }

    /**
     * 加密
     * @param str
     * @return
     */
    public static String decrypt(String str) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setPassword(jasyptPassword);
        return standardPBEStringEncryptor.decrypt(str);
    }
}
