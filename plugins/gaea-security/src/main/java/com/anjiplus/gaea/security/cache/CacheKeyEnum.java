package com.anjiplus.gaea.security.cache;

/**
 * 缓存对象key.
 *
 * @author lirui
 * @since 2019-07-30 09:28
 */
public enum CacheKeyEnum {

    /**
     * 数据字典
     */
    INIT_DICT_PREFIX("open:dict:prefix:","数据字典"),

    /**
     * 保存5分钟签名，防止恶意重复请求
     */
    REPEAT_REQUEST_SIGN("open:repeat:sign:","请求签名"),


    /**
     * 保存用户可权限路径
     */
    USER_AUTH("open:user:auth:","用户权限"),

    /**
     * 用户权限url
     */
    USER_URL("open:user:url:","用户权限url"),

    /**
     * 保存用户角色
     */
    USER_ROLE("open:user:role:","用户角色"),

    /**
     * 登录token
     */
    USER_LOGIN_TOKEN("open:login:token:","登录token"),

    /**
     * 作用：JWT本身的token失效（私钥共享）有缺陷，登出后token仍然有效
     * 故设置每个用户独享一个加密私钥，当用户登出后，改变私钥
     */
    TOKEN_JWT_EXPIRE("open:token:expire:", "用户存储失效的token"),

    /**
     * 作用：JWT本身的token失效（私钥共享）有缺陷，登出后token仍然有效
     * 故设置每个用户独享一个加密私钥，当用户登出后，改变私钥
     */
    TOKEN_JWT_USER("open:user:token", "存放用户名及对应的token"),

    /**
     * 密码错误次数
     */
    USER_PASSWORD_ERROR_NUMBER("open:user:password:errorNumber:", "密码错误次数"),

    /**
     * 保存用户对应的权限
     */
    USER_AUTHORITIES("open:user:authorities:", "保存用户对应的权限");


    private String key;

    private String value;

    CacheKeyEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }}
