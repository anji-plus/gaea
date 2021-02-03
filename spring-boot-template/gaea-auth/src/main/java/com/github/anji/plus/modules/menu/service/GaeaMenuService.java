package com.github.anji.plus.modules.menu.service;

import com.github.anji.plus.gaea.bean.TreeNode;
import com.github.anji.plus.modules.menu.controller.dto.GaeaLeftMenuDTO;
import com.github.anji.plus.modules.menu.dao.entity.GaeaMenu;
import com.github.anji.plus.modules.menu.controller.param.GaeaMenuParam;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;

import java.util.List;

/**
 * 菜单表(GaeaMenu)Service
 *
 * @author lirui
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
     * @param username
     * @return
     */
    List<String> getSelectActions(String username);

}
