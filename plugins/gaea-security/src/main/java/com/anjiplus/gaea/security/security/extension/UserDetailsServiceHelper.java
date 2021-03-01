package com.anjiplus.gaea.security.security.extension;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

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


    /**
     * 获取请求方式+url与角色的对应关系
     * 需要子类实现，才能实现对url进行角色鉴权
     * @return
     */
    public Map<String,Set<String>> getUrlRoleMappings() {

        HashMap<String, Set<String>> map = new HashMap<>(2);

        Set<String> set1 = new HashSet<>();
        set1.add("admin");
        set1.add("demo");
        map.put("/user/demo", set1);

        Set<String> set2 = new HashSet<>();
        set2.add("admin1");
        set2.add("demo1");
        map.put("/user/select/**", set2);

        return map;
    }

    /**
     * 白名单
     * @return
     */
    public String whiteList() {
        return "/login,/logout";
    }

}
