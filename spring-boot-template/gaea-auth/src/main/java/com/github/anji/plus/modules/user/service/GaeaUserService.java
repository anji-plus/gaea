package com.github.anji.plus.modules.user.service;

import com.github.anji.plus.modules.menu.controller.dto.TreeDTO;
import com.github.anji.plus.modules.user.controller.param.UserRoleOrgReqParam;
import com.github.anji.plus.modules.user.dao.entity.GaeaUser;
import com.github.anji.plus.modules.user.controller.param.GaeaUserParam;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;

/**
 * 用户表(GaeaUser)Service
 *
 * @author lirui
 * @since 2021-02-02 13:38:12
 */
public interface GaeaUserService extends GaeaBaseService<GaeaUserParam, GaeaUser> {


    /**
     * 通过用户名获取用户信息
     * @param username
     * @return
     */
    GaeaUser getUserByUsername(String username);

    /**
     * 根据用户获取角色信息
     * @param username
     * @return
     */
    TreeDTO queryRoleTree(String username);

    /**
     * 保存用户角色机构信息
     * @param reqParam
     * @return
     */
    Boolean saveRoleTree(UserRoleOrgReqParam reqParam);

}
