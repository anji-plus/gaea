package com.anjiplus.gaea.security.code;

/**
 * 响应码
 * @author lirui
 * @since 2021-01-16
 */
public interface UserResponseCode {

    /**
     * 响应成功
     */
    String SUCCESS = "200";
    /**
     * 登录成功
     */
    String USER_LOGIN_SUCCESS = "Login.success";

    /**
     * 登出成功
     */
    String USER_LOGOUT_SUCCESS = "Logout.success";

    /**
     * 没有权限
     */
    String USER_NO_AUTH = "User.no.authority";

    /**
     * 用户token过期
     */
    String USER_TOKEN_EXPIRED = "User.credentials.expired";

    /**
     * 用户密码错误
     */
    String USER_PASSWORD_ERROR = "User.account.password.error";

    /**
     * 用户账号被锁定
     */
    String USER_LOCKED = "User.account.locked";

    /**
     * 验证码错误
     */
    String CAPTCHA_ERROR = "User.captcha.error";


    /**
     * 解析请求体失败
     */
    String REQUEST_BODY_ERROR = "User.login.body.error";


    /**
     * 用户注销，不可用
     */
    String USER_DISABLED_ERROR = "User.disabled";
}
