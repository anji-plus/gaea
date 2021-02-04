package com.github.anji.plus.modules.role.controller;

import com.github.anji.plus.gaea.bean.ResponseBean;
import com.github.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.github.anji.plus.modules.menu.controller.dto.TreeDTO;
import com.github.anji.plus.modules.role.controller.param.RoleOrgReqParam;
import com.github.anji.plus.modules.role.dao.entity.GaeaRole;
import com.github.anji.plus.modules.role.controller.dto.GaeaRoleDTO;
import com.github.anji.plus.modules.role.controller.param.GaeaRoleParam;
import com.github.anji.plus.modules.role.service.GaeaRoleService;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import io.swagger.annotations.Api;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 角色(GaeaRole)实体类
 *
 * @author lirui
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
    public ResponseBean queryOrgTreeForRole(@PathVariable("code") String roleCode) {
        TreeDTO data = gaeaRoleService.queryRoleOrgTree(roleCode);
        return responseSuccessWithData(data);
    }

    @PostMapping("/saveOrgTreeForRole")
    public ResponseBean saveOrgTreeForRole(@RequestBody RoleOrgReqParam reqParam){
        Boolean data=gaeaRoleService.saveOrgTreeForRole(reqParam);
        return responseSuccessWithData(data);
    }
}
