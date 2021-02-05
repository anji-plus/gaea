package com.github.anji.plus.modules.menu.controller;

import java.util.*;

import com.github.anji.plus.gaea.bean.ResponseBean;
import com.github.anji.plus.gaea.bean.TreeNode;
import com.github.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.github.anji.plus.gaea.holder.UserContentHolder;
import com.github.anji.plus.modules.menu.controller.dto.GaeaLeftMenuDTO;
import com.github.anji.plus.modules.menu.controller.dto.TreeDTO;
import com.github.anji.plus.modules.menu.controller.param.MenuActionReqParam;
import com.github.anji.plus.modules.menu.dao.entity.GaeaMenu;
import com.github.anji.plus.modules.menu.controller.dto.GaeaMenuDTO;
import com.github.anji.plus.modules.menu.controller.param.GaeaMenuParam;
import com.github.anji.plus.modules.menu.service.GaeaMenuService;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import com.github.anji.plus.modules.role.service.GaeaRoleService;
import com.github.anji.plus.modules.user.dao.entity.GaeaUser;
import com.github.anji.plus.modules.user.service.GaeaUserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 菜单表(GaeaMenu)实体类
 *
 * @author lirui
 * @since 2021-02-02 13:36:43
 */
@RestController
@RequestMapping("/menu")
@Api(value = "/menu", tags = "菜单表")
public class GaeaMenuController extends GaeaBaseController<GaeaMenuParam, GaeaMenu, GaeaMenuDTO> {
    @Autowired
    private GaeaMenuService gaeaMenuService;

    @Autowired
    private GaeaUserService gaeaUserService;

    @Autowired
    private GaeaRoleService gaeaRoleService;

    @Override
    public GaeaBaseService<GaeaMenuParam, GaeaMenu> getService() {
        return gaeaMenuService;
    }

    @Override
    public GaeaMenu getEntity() {
        return new GaeaMenu();
    }

    @Override
    public GaeaMenuDTO getDTO() {
        return new GaeaMenuDTO();
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/userInfo")
    public ResponseBean userInfo() {
        String username = UserContentHolder.getContext().getUsername();

        //获取当前用户所拥有的角色
        List<String> userRoles = gaeaRoleService.getUserRoleCodes(username);

        //获取当前用户所拥有的菜单
        List<GaeaLeftMenuDTO> userMenus = gaeaMenuService.getMenus(userRoles);

        GaeaUser gaeaUser = gaeaUserService.getUserByUsername(username);
        Map<String,Object> userInfo = new HashMap<>(8);

        userInfo.put("menus",userMenus);
        userInfo.put("roles",userRoles);
        userInfo.put("username",username);
        userInfo.put("nickname",gaeaUser.getNickname());
        ResponseBean listResponseBean = ResponseBean.builder().data(userInfo).build();
        return listResponseBean;
    }

    /**
     * 获取菜单树
     * @return
     */
    @GetMapping("/tree")
    public ResponseBean getTree() {
        List<TreeNode> tree = gaeaMenuService.getTree();
        return responseSuccessWithData(tree);
    }

    /**
     * 获取当前用户拥有的权限按钮
     * @return
     */
    @GetMapping("/tree/actions")
    public ResponseBean userActions() {
        String username = UserContentHolder.getContext().getUsername();
        return responseSuccessWithData(gaeaMenuService.getSelectActions(username));
    }

    /**
     * 获取菜单按钮树
     * @param menuCode
     * @return
     */
    @GetMapping("/queryActionTreeForMenu/{code}")
    public ResponseBean queryActionTreeForMenu(@PathVariable("code")String menuCode){
        TreeDTO data=gaeaMenuService.queryActionTreeForMenu(menuCode);
        return responseSuccessWithData(data);
    }

    /**
     * 保存菜单按钮树
     * @param reqParam
     * @return
     */
    @PostMapping("/saveActionTreeForMenu")
    public ResponseBean saveActionTreeForMenu(@RequestBody MenuActionReqParam reqParam){
        Boolean data=gaeaMenuService.saveActionTreeForMenu(reqParam);
        return responseSuccessWithData(data);
    }




}
