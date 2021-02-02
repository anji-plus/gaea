package com.github.anji.plus.modules.role.service;

import com.github.anji.plus.modules.role.dao.entity.GaeaRole;
import com.github.anji.plus.modules.role.controller.param.GaeaRoleParam;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;

import java.util.List;

/**
 * 角色(GaeaRole)Service
 *
 * @author lirui
 * @since 2021-02-02 13:37:54
 */
public interface GaeaRoleService extends GaeaBaseService<GaeaRoleParam, GaeaRole> {


    /**
     * 通过用户名获取对应的角色编码
     * @param username
     * @return
     */
    List<String> getUserRoleCodes(String username);

}
