package com.anji.plus.modules.authority.service;

import com.anji.plus.gaea.bean.TreeNode;
import com.anji.plus.gaea.curd.service.GaeaBaseService;
import com.anji.plus.modules.authority.controller.param.GaeaAuthorityParam;
import com.anji.plus.modules.authority.dao.entity.GaeaAuthority;
import com.anji.plus.modules.authority.dao.entity.GaeaRoleAuthority;

import java.util.List;

/**
 * 菜单表(GaeaAuthority)Service
 *
 * @author lirui
 * @since 2021-03-01 10:03:51
 */
public interface GaeaAuthorityService extends GaeaBaseService<GaeaAuthorityParam, GaeaAuthority> {

    /**
     * 权限树
     *
     * @return
     */
    List<TreeNode> authorityTree();


    /**
     * 保存角色和权限
     *
     * @param role
     * @param authorities
     */
    void insertRoleAuthority(String role, List<String> authorities);


    /**
     * 获取指定角色的权限
     * @param org
     * @return
     */
    List<GaeaRoleAuthority> userAuthorities(String org);

}
