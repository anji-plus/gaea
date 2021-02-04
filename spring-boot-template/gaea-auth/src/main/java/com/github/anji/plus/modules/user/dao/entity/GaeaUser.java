package com.github.anji.plus.modules.user.dao.entity;

import com.github.anji.plus.gaea.curd.entity.GaeaBaseEntity;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户表(GaeaUser)实体类
 *
 * @author lirui
 * @since 2021-02-02 13:38:12
 */
@TableName("gaea_user")
public class GaeaUser extends GaeaBaseEntity implements Serializable {

    private String username;

    private String password;
        /**
    * 真实姓名
    */
    private String nickname;

    private String email;

    private String phone;
        /**
    * 1：可用 0：禁用
    */
    private Integer enabled;
        /**
    * 0：否，锁定，1：是，未锁定
    */
    private Integer accountLocked;
        /**
    * 0：否，过期，1：是，未过期
    */
    private Integer accountNonExpired;
        /**
    * 0：否，过期，1：是，未过期
    */
    private Integer credentialsNonExpired;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(Integer accountLocked) {
        this.accountLocked = accountLocked;
    }

    public Integer getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Integer accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Integer getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Integer credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }


}
