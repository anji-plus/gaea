package com.anjiplus.gaea.security.code;

/**
 * 错误响应码
 * @author lirui
 * @since 2021-01-16
 */
public interface UserResponseCode {




    /******************200:表示成功************************/

    /**
     * 响应成功
     */
    String SUCCESS = "200";
    /**
     * 登录成功
     */
    String USER_LOGIN_SUCCESS = "200-02-0001";

    /**
     * 登出成功
     */
    String USER_LOGOUT_SUCCESS = "200-02-0002";


    /******************500:表示失败************************/

    //*********************************数据字典500-02-0*

    /**
     * 未登录
     */
    String USER_NO_LOGIN = "500-02-0001";

    /**
     * 没有权限
     */
    String USER_NO_AUTH = "500-02-0002";

    /**
     * 用户不存在
     */
    String USER_NO_EXIST = "500-02-0003";


    /**
     * 用户token过期
     */
    String USER_TOKEN_EXPIRED = "500-02-0004";

    /**
     * 用户密码错误
     */
    String USER_PASSWORD_ERROR = "500-02-0005";

    /**
     * 用户原密码错误
     */
    String USER_OLD_PASSWORD_ERROR = "500-02-0006";

    /**
     * 用户密码格式错误
     */
    String USER_PASSWORD_PATTERN_ERROR = "500-02-0007";

    /**
     * 用户已存在
     */
    String USER_EXIST = "500-02-0008";

    /**
     * 用户账号被锁定
     */
    String USER_LOCKED = "500-02-0009";

    /**
     * 验证码错误
     */
    String CAPTCHA_ERROR = "500-02-0010";

    /**
     * 部门关联用户，不能删除
     */
    String DEPT_USER_DELETE = "500-02-0011";


    /**
     * 解密失败
     */
    String CRYPTO_ERROR = "500-02-0012";


    /**
     * 解析请求体失败
     */
    String REQUEST_BODY_ERROR = "500-02-0013";

    //*********************************数据字典500-02-1*

    /**
     * 字典编码重复
     */
    String DICT_EXIST = "500-02-1001";

    /**
     * 字典项名称重复
     */
    String DICT_ITEM_EXIST = "500-02-1002";

    //*********************************角色500-02-2*

    /**
     * 字典编码重复
     */
    String ROLE_EXIST = "500-02-2001";


    //*********************************角色500-02-3*

    /**
     * 资源编号重复
     */
    String RESOURCE_EXIST = "500-02-3001";
}
