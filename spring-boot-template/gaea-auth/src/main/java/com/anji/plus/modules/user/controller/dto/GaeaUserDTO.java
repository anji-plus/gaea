package com.anji.plus.modules.user.controller.dto;

import com.anji.plus.gaea.annotation.Unique;
import com.anji.plus.gaea.curd.dto.GaeaBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户表(GaeaUser)实体类
 *
 * @author lr
 * @since 2021-02-02 13:38:12
 */
@ApiModel(value = "用户表")
public class GaeaUserDTO extends GaeaBaseDTO {

    @ApiModelProperty(value = "用户登录名")
    @Unique
    private String username;

    @ApiModelProperty(value = "用户密码")
    private String password;
    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String nickname;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String phone;
    /**
     * 1：可用 0：禁用
     */
    @ApiModelProperty(value = "1：可用 0：禁用")
    private Integer enabled;
    /**
     * 0：否，锁定，1：是，未锁定
     */
    @ApiModelProperty(value = "0：否，锁定，1：是，未锁定")
    private Integer accountLocked;
    /**
     * 0：否，过期，1：是，未过期
     */
    @ApiModelProperty(value = "0：否，过期，1：是，未过期")
    private Integer accountNonExpired;
    /**
     * 0：否，过期，1：是，未过期
     */
    @ApiModelProperty(value = "0：否，过期，1：是，未过期")
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
