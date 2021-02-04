package com.github.anji.plus.modules.role.controller.param;

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
public class RoleOrgReqParam implements Serializable {
    private String roleCode;
    private List<String> orgCodes;
}
