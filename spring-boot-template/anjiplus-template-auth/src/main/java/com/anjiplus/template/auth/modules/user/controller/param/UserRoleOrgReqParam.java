package com.anjiplus.template.auth.modules.user.controller.param;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：
 *
 * @Author: peiyanni
 * @Date: 2021/2/3 17:46
 */
@Getter
@Setter
public class UserRoleOrgReqParam implements Serializable {
    private String username;
    private List<String> roleOrgCodes;
}
