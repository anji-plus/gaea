package com.anji.plus.gaea.auth.modules.role.controller.param;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：
 * 角色，菜单权限
 * @Author: peiyanni
 * @Date: 2021/2/3 17:46
 */
@Getter
@Setter
public class RoleMenuActionReqParam implements Serializable {
    private String roleCode;
    private List<String> codes;
}
