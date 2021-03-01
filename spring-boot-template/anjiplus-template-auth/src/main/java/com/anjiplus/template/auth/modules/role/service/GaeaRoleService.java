package com.anjiplus.template.auth.modules.role.service;

import com.anjiplus.template.auth.modules.menu.controller.dto.TreeDTO;
import com.anjiplus.template.auth.modules.role.controller.param.RoleMenuActionReqParam;
import com.anjiplus.template.auth.modules.role.controller.param.RoleOrgReqParam;
import com.anjiplus.template.auth.modules.role.dao.entity.GaeaRole;
import com.anjiplus.template.auth.modules.role.controller.param.GaeaRoleParam;
import com.anji.plus.gaea.curd.service.GaeaBaseService;

import java.util.List;

/**
 * 角色(GaeaRole)Service
 *
 * @author lr
 * @since 2021-02-02 13:37:54
 */
public interface GaeaRoleService extends GaeaBaseService<GaeaRoleParam, GaeaRole> {


    /**
     * 通过用户名获取对应的角色编码
     * @param username
     * @return
     */
    List<String> getUserRoleCodes(String username);

    /**
     * 通过角色查询角色组织信息
     * @param roleCode
     * @return
     */
    TreeDTO queryRoleOrgTree(String roleCode);

    /**
     * 保存角色组织信息
     * @param requestModel
     * @return
     */
    Boolean saveOrgTreeForRole(RoleOrgReqParam requestModel);

    /**
     * 保存角色，菜单权限信息
     * @param reqParam
     * @return
     */
    Boolean saveMenuActionTreeForRole(RoleMenuActionReqParam reqParam);
}
