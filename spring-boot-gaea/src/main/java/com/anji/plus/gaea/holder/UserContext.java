package com.anji.plus.gaea.holder;

import java.util.List;
import java.util.Set;

/**
 * 用户上下文信息，用户名和角色等
 * @author lr
 * @since 2021-01-12
 */
public class UserContext {

    /**
     * 用户名
     */
    private String username;

    /**
     * 角色
     */
    private List<String> roles;

    /**
     * 权限
     */
    private Set<String> Authorities;

    /**
     * 菜单
     */
    private List<String> menus;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Set<String> getAuthorities() {
        return Authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        Authorities = authorities;
    }

    public List<String> getMenus() {
        return menus;
    }

    public void setMenus(List<String> menus) {
        this.menus = menus;
    }
}
