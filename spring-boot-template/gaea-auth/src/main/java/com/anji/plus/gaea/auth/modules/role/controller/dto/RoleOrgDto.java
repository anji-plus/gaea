package com.anji.plus.gaea.auth.modules.role.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 功能描述：
 *
 * @Author: peiyanni
 * @Date: 2021/2/4 15:39
 */
@Getter
@Setter
public class RoleOrgDto implements Serializable {
    private Long id;
    private String orgCode;
    private String orgName;
    private String roleCode;
    private String roleName;
}
