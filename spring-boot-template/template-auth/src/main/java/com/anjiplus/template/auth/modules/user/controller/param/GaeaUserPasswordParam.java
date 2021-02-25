package com.anjiplus.template.auth.modules.user.controller.param;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户表(GaeaUser)param
 *
 * @author peiyanni
 * @since 2021-02-07 13:38:12
 */
@Getter
@Setter
public class GaeaUserPasswordParam implements Serializable {
    private String username;
    /**
     * 新密码
     */
    private String password;
    /**修改密码时旧密码*/
    private String oldPassword;

    /**修改密码时确认密码*/
    private String confirmPassword;
}
