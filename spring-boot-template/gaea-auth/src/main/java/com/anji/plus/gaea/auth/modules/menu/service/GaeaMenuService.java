package com.anji.plus.gaea.auth.modules.menu.service;

import com.anji.plus.gaea.bean.TreeNode;
import com.anji.plus.gaea.auth.modules.menu.controller.param.GaeaMenuParam;
import com.anji.plus.gaea.auth.modules.menu.controller.dto.GaeaLeftMenuDTO;
import com.anji.plus.gaea.auth.modules.menu.controller.dto.TreeDTO;
import com.anji.plus.gaea.auth.modules.menu.controller.param.MenuActionReqParam;
import com.anji.plus.gaea.auth.modules.menu.dao.entity.GaeaMenu;
import com.anji.plus.gaea.curd.service.GaeaBaseService;

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
     * 获取菜单所分批的按钮树
     * @param menuCode
     * @return
     */
    TreeDTO queryActionTreeForMenu(String menuCode);

    /**
     * 保存菜单按钮信息
     * @param requestModel
     * @return
     */
    Boolean saveActionTreeForMenu(MenuActionReqParam requestModel);

}
