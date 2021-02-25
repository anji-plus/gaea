package com.anjiplus.template.auth.modules.role.controller;

import com.anjiplus.template.auth.modules.menu.controller.dto.TreeDTO;
import com.anjiplus.template.auth.modules.role.controller.param.RoleMenuActionReqParam;
import com.anjiplus.template.auth.modules.role.controller.param.RoleOrgReqParam;
import com.anjiplus.template.auth.modules.role.dao.entity.GaeaRole;
import com.anjiplus.template.auth.modules.role.service.GaeaRoleService;
import com.anjiplus.template.auth.modules.role.controller.dto.GaeaRoleDTO;
import com.anjiplus.template.auth.modules.role.controller.param.GaeaRoleParam;
import com.anjiplus.gaea.log.annotation.GaeaAuditLog;
import com.anji.plus.gaea.bean.ResponseBean;
import com.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.anji.plus.gaea.curd.service.GaeaBaseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 角色(GaeaRole)实体类
 *
 * @author lr
 * @since 2021-02-02 13:37:54
 */
@RestController
@RequestMapping("/role")
@Api(value = "/role", tags = "角色")
public class GaeaRoleController extends GaeaBaseController<GaeaRoleParam, GaeaRole, GaeaRoleDTO> {
    @Autowired
    private GaeaRoleService gaeaRoleService;

    @Override
    public GaeaBaseService<GaeaRoleParam, GaeaRole> getService() {
        return gaeaRoleService;
    }

    @Override
    public GaeaRole getEntity() {
        return new GaeaRole();
    }

    @Override
    public GaeaRoleDTO getDTO() {
        return new GaeaRoleDTO();
    }

    @GetMapping("/queryOrgTreeForRole/{code}")
    @GaeaAuditLog(pageTitle="查询角色组织")
    public ResponseBean queryOrgTreeForRole(@PathVariable("code") String roleCode) {
        TreeDTO data = gaeaRoleService.queryRoleOrgTree(roleCode);
        return responseSuccessWithData(data);
    }

    @PostMapping("/saveOrgTreeForRole")
    @GaeaAuditLog(pageTitle="保存角色组织")
    public ResponseBean saveOrgTreeForRole(@RequestBody RoleOrgReqParam reqParam){
        Boolean data=gaeaRoleService.saveOrgTreeForRole(reqParam);
        return responseSuccessWithData(data);
    }

    @PostMapping("/saveMenuActionTreeForRole")
    @GaeaAuditLog(pageTitle="保存角色菜单权限")
    public ResponseBean saveMenuActionTreeForRole(@RequestBody RoleMenuActionReqParam reqParam){
        Boolean data=gaeaRoleService.saveMenuActionTreeForRole(reqParam);
        return responseSuccessWithData(data);
    }
}
