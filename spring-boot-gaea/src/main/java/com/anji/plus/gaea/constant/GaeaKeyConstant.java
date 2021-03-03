package com.anji.plus.gaea.constant;

/**
 * 缓存key
 * @author lr
 * @since 2021-03-03
 */
public interface GaeaKeyConstant {

    /**
     * 路由与角色的缓存
     */
    String HASH_URL_ROLE_KEY = "gaea:security:url:roles";


    /**
     * 用户名与角色对应关系的前缀
     */
    String USER_ROLE_SET_PREFIX = "gaea:security:user:roles:";
}
