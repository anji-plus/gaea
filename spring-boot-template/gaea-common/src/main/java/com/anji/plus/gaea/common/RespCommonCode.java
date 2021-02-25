package com.anji.plus.gaea.common;

/**
 * 功能描述：
 * 异常code ,具体的提示信息见 i18n/messages.properties
 * @Author: peiyanni
 * @Date: 2021/2/7 10:52
 */
public class RespCommonCode {

    /**
     * 用户相关
     */
    public static final String AUTH_PASSWORD_NOTSAME="1001";
    public static final String USER_PASSWORD_CONFIG_PASSWORD_CANOT_EQUAL="1002";
    public static final String OLD_PASSWORD_ERROR="1003";
    public static final String USER_ONTEXIST_ORGINFO="1004";
    public static final String USER_ONTEXIST_ROLEINFO="1005";
    public static final String MENU_TABLE_CODE_EXIST="1006";

    /**
     * 文件相关
     */
    public  static final String FILE_EMPTY_FILENAME="2001";
    public static final String FILE_SUFFIX_UNSUPPORTED="2002";
    public static final String FILE_UPLOAD_ERROR="2003";
    public static final String FILE_ONT_EXSIT="2004";
    public static final String LIST_IS_EMPTY="2005";

}
