package com.github.anji.plus.modules.user.service;

import com.github.anji.plus.modules.menu.controller.dto.TreeDTO;
import com.github.anji.plus.modules.org.dao.entity.GaeaOrg;
import com.github.anji.plus.modules.user.controller.dto.GaeaUserDTO;
import com.github.anji.plus.modules.user.controller.param.GaeaUserPasswordParam;
import com.github.anji.plus.modules.user.controller.param.UserRoleOrgReqParam;
import com.github.anji.plus.modules.user.dao.entity.GaeaUser;
import com.github.anji.plus.modules.user.controller.param.GaeaUserParam;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;

import java.util.List;

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

    /**
     * 修改密码操作
     * @param requestParam
     * @return
     */
    Boolean updatePassword(GaeaUserPasswordParam requestParam);

    /**
     * 管理员-新增用户
     * @param dto
     * @return
     */
    Boolean saveGaeaUser(GaeaUserDTO dto);

    /**
     * 根据用户查询所属的全部机构信息
     * @param username
     * @return
     */
    List<GaeaOrg> getOrgByUsername(String username);

    /**
     * 根据用户名和机构信息查询角色
     * @param username
     * @param orgCode
     * @return
     */
    List<String> getRoleByUserOrg(String username,String orgCode);

    /**
     * 管理员重置密码操作
     * @param username
     * @return
     */
    Boolean setDefaultPwd(String username);

}
