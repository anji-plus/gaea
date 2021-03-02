package com.anji.plus.modules.user.service;

import com.anji.plus.modules.menu.controller.dto.TreeDTO;
import com.anji.plus.modules.org.dao.entity.GaeaOrg;
import com.anji.plus.modules.user.controller.dto.GaeaUserDTO;
import com.anji.plus.modules.user.controller.param.GaeaUserParam;
import com.anji.plus.modules.user.controller.param.GaeaUserPasswordParam;
import com.anji.plus.modules.user.controller.param.UserRoleOrgReqParam;
import com.anji.plus.modules.user.dao.entity.GaeaUser;
import com.anji.plus.gaea.curd.service.GaeaBaseService;

import java.util.List;

/**
 * 用户表(GaeaUser)Service
 *
 * @author lr
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
