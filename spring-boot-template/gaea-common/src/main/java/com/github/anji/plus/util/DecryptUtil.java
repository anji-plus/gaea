package com.github.anji.plus.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 功能描述：
 * 加密工具
 * @Author: peiyanni
 * @Date: 2021/2/7 11:44
 */
public class DecryptUtil {
    /**
     * 加密
     * @param str
     * @return
     */
    public static String decrypt(String str) {
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(str);
    }

    public static boolean matches(String str,String oldStr){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(str,oldStr);
    }
}
