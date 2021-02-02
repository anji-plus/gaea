package com.anjiplus.gaea.security.security.extension;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Set;

/**
 * 帮助类，留给子类实现扩展
 * @author lr
 * @since 2021-02-01
 */
public abstract class UserDetailsServiceHelper {

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    public abstract UserDetails findByUsername(String username);

    /**
     * 获取用户拥有的角色
     * @param username
     * @return
     */
    public abstract List<String> getUserRoles(String username);

    /**
     * 获取指定角色拥有的权限
     * @param roles
     * @return
     */
    public abstract Set<String> getUserAuthorities(List<String> roles);



}
