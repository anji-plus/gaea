package com.anji.plus.modules.menu.service;

import com.anji.plus.gaea.bean.TreeNode;
import com.anji.plus.gaea.curd.service.GaeaBaseService;
import com.anji.plus.modules.menu.controller.dto.GaeaLeftMenuDTO;
import com.anji.plus.modules.menu.controller.param.GaeaMenuParam;
import com.anji.plus.modules.menu.dao.entity.GaeaMenu;

import java.util.List;

/**
 * 菜单表(GaeaMenu)Service
 *
 * @author lr
 * @since 2021-02-02 13:36:43
 */
public interface GaeaMenuService extends GaeaBaseService<GaeaMenuParam, GaeaMenu> {

    /**
     * 根据角色查询对应的菜单
     * @param roles
     * @return
     */
    List<GaeaLeftMenuDTO> getMenus(List<String> roles);


    /**
     * 获取所有菜单按钮树,角色分配权限时用
     * @return
     */
    List<TreeNode> getTree();


    /**
     * 获取用户拥有的权限
     * @param roleCode
     * @return
     */
    List<String> getSelectActions(String roleCode);


    /**
     * 设置菜单跟权限对应关系
     * @param menuCode
     * @param authorities
     * @return
     */
    boolean saveMenuAuthorities(String menuCode, List<String> authorities);

}
