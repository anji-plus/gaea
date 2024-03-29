package com.anji.plus.modules.menu.controller;

import com.anji.plus.gaea.annotation.log.GaeaAuditLog;
import com.anji.plus.gaea.bean.ResponseBean;
import com.anji.plus.gaea.bean.TreeNode;
import com.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.anji.plus.gaea.curd.service.GaeaBaseService;
import com.anji.plus.gaea.holder.UserContentHolder;
import com.anji.plus.modules.authority.service.GaeaAuthorityService;
import com.anji.plus.modules.menu.controller.dto.GaeaLeftMenuDTO;
import com.anji.plus.modules.menu.controller.dto.GaeaMenuAuthorityDTO;
import com.anji.plus.modules.menu.controller.dto.GaeaMenuDTO;
import com.anji.plus.modules.menu.controller.param.GaeaMenuParam;
import com.anji.plus.modules.menu.controller.param.LeftMenuReqParam;
import com.anji.plus.modules.menu.dao.entity.GaeaMenu;
import com.anji.plus.modules.menu.service.GaeaMenuService;
import com.anji.plus.modules.org.dao.entity.GaeaOrg;
import com.anji.plus.modules.role.dao.entity.GaeaRoleMenuAuthority;
import com.anji.plus.modules.role.service.GaeaRoleService;
import com.anji.plus.modules.user.dao.entity.GaeaUser;
import com.anji.plus.modules.user.service.GaeaUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单表(GaeaMenu)实体类
 *
 * @author lr
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

    @Autowired
    private GaeaAuthorityService gaeaAuthorityService;

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

    @PostMapping("/menuUserInfoByOrg")
    @GaeaAuditLog(pageTitle="获取菜单")
    public ResponseBean getMenuInfoByOrg(@RequestBody LeftMenuReqParam reqParam){
        String username = UserContentHolder.getContext().getUsername();
        //获取当前用户所在机构
        Map<String,Object> userInfo = new HashMap<>(8);
        List<GaeaOrg> orgList = gaeaUserService.getOrgByUsername(username);
        if(!CollectionUtils.isEmpty(orgList)){
            String orgCode=reqParam.getOrgCode();
            if(StringUtils.isEmpty(orgCode)){
                orgCode=orgList.get(0).getOrgCode();
            }
            List<String> userRoles =gaeaUserService.getRoleByUserOrg(username,orgCode);
            //获取当前用户所拥有的菜单
            List<GaeaLeftMenuDTO> userMenus = gaeaMenuService.getMenus(userRoles);

            List<String> roles = gaeaUserService.getRoleByUserOrg(username, orgCode);
            if(!CollectionUtils.isEmpty(roles)){
                //按钮权限
                List<GaeaRoleMenuAuthority> gaeaRoleAuthorities = gaeaAuthorityService.userAuthorities(orgCode, roles);
                //路径
                List authorities  = gaeaRoleAuthorities.stream().map(GaeaRoleMenuAuthority::getAuthCode).collect(Collectors.toList());
                userInfo.put("menus",userMenus);
                userInfo.put("roles",userRoles);
                userInfo.put("orgs",orgList);
                userInfo.put("currentOrgCode",orgCode);
                userInfo.put("authorities", authorities);
            }else{
                userInfo.put("menus",userMenus);
                userInfo.put("roles",null);
                userInfo.put("orgs",orgList);
                userInfo.put("currentOrgCode",orgCode);
                userInfo.put("authorities", null);
            }
        }else{
            userInfo.put("menus",null);
            userInfo.put("roles",null);
            userInfo.put("orgs",null);
        }
        GaeaUser gaeaUser = gaeaUserService.getUserByUsername(username);
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
     * 获取当前角色拥有的权限按钮
     * @return
     */
    @GetMapping("/tree/actions/{roleCode}")
    public ResponseBean userActions(@PathVariable("roleCode")String roleCode) {
        return responseSuccessWithData(gaeaMenuService.getSelectActions(roleCode));
    }


    @PostMapping("/mapper/authorities")
    public ResponseBean menuAuthority(@RequestBody GaeaMenuAuthorityDTO authorityDTO) {
        gaeaMenuService.saveMenuAuthorities(authorityDTO.getMenuCode(), authorityDTO.getAuthCodes());
        return responseSuccess();
    }

}
