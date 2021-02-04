package com.github.anji.plus.gaea.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * 加解密工具类
 * @author lr
 * @since 2021-01-06
 */
public class JasyptUtils {
    /**
     * 解密
     * @param str
     * @return
     */
    public static String encrypt(String str, String secret) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setPassword(secret);
        return standardPBEStringEncryptor.encrypt(str);
    }

    /**
     * 加密
     * @param str
     * @return
     */
    public static String decrypt(String str, String secret) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setPassword(secret);
        return standardPBEStringEncryptor.decrypt(str);
    }
}
