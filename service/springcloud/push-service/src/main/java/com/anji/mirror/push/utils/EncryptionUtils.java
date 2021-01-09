package com.anji.mirror.push.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtils {

    /**
     * MD5加密
     *
     * @param source
     * @return
     */
    public static String md5(String source) {
        if (source == null)
            return null;
        String s = null;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source.getBytes());
            byte tmp[] = md.digest();
            char str[] = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            s = new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * AES 加密
     *
     * @param content  待加密内容
     * @param password 密钥必须16位
     * @return
     */
    public static byte[] aesEncrypt(String content, String password) {
        if (content == null || password == null)
            return null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(password.getBytes(), "AES"));
            return cipher.doFinal(content.getBytes("UTF-8"));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * AES 解密
     *
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static String aesDecrypt(byte[] content, String password) {
        if (content == null || password == null)
            return null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(password.getBytes(), "AES"));
            byte[] bytes = cipher.doFinal(content);
            return new String(bytes, "UTF-8");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将二进制数组转换为16进制字符串
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    private static char base64Code[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/',};

    private static byte base64Decode[] = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, 62, -1, 63, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, 0, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22,
            23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1,};

    /**
     * base64加密
     *
     * @param b
     * @return
     */
    public static String base64Encode(byte[] b) {
        int code = 0;
        StringBuffer sb = new StringBuffer(((b.length - 1) / 3) << 2 + 4);
        for (int i = 0; i < b.length; i++) {
            code |= (b[i] << (16 - i % 3 * 8)) & (0xff << (16 - i % 3 * 8));
            if (i % 3 == 2 || i == b.length - 1) {
                sb.append(base64Code[(code & 0xfc0000) >>> 18]);
                sb.append(base64Code[(code & 0x3f000) >>> 12]);
                sb.append(base64Code[(code & 0xfc0) >>> 6]);
                sb.append(base64Code[code & 0x3f]);
                code = 0;
            }
        }
        if (b.length % 3 > 0) {
            sb.setCharAt(sb.length() - 1, '=');
        }
        if (b.length % 3 == 1) {
            sb.setCharAt(sb.length() - 2, '=');
        }
        return sb.toString();
    }

    /**
     * base64解密
     *
     * @param code
     * @return
     */
    public static byte[] base64Dncode(String code) {
        if (code == null) {
            return new byte[0];
        }
        int len = code.length();
        if (len % 4 != 0) {
            throw new IllegalArgumentException("Base64 string length must be 4*n");
        }
        if (code.length() == 0) {
            return new byte[0];
        }
        int pad = 0;
        if (code.charAt(len - 1) == '=') {
            pad++;
        }
        if (code.charAt(len - 2) == '=') {
            pad++;
        }
        int retLen = len / 4 * 3 - pad;
        byte[] ret = new byte[retLen];
        char ch1, ch2, ch3, ch4;
        int i;
        for (i = 0; i < len; i += 4) {
            int j = i / 4 * 3;
            ch1 = code.charAt(i);
            ch2 = code.charAt(i + 1);
            ch3 = code.charAt(i + 2);
            ch4 = code.charAt(i + 3);
            int tmp = (base64Decode[ch1] << 18) | (base64Decode[ch2] << 12) | (base64Decode[ch3] << 6) | (base64Decode[ch4]);
            ret[j] = (byte) ((tmp & 0xff0000) >> 16);
            if (i < len - 4) {
                ret[j + 1] = (byte) ((tmp & 0x00ff00) >> 8);
                ret[j + 2] = (byte) ((tmp & 0x0000ff));
            } else {
                if (j + 1 < retLen) {
                    ret[j + 1] = (byte) ((tmp & 0x00ff00) >> 8);
                }
                if (j + 2 < retLen) {
                    ret[j + 2] = (byte) ((tmp & 0x0000ff));
                }
            }
        }
        return ret;
    }

}
