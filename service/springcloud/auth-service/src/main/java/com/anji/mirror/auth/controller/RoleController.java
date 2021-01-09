package com.anji.mirror.auth.controller;

import com.anji.mirror.auth.domain.vo.RoleVO;
import com.anji.mirror.auth.service.RoleService;
import com.anji.mirror.common.annotation.Log;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.security.HasPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/create")
    @HasPermission("roleManage:add")
    @Log(pageTitle = "创建角色")
    public ResponseModel create(@RequestBody RequestModel<RoleVO> requestModel) {
        return roleService.create(requestModel);
    }

    @PostMapping("/updateById")
    @HasPermission("roleManage:edit")
    @Log(pageTitle = "根据id修改")
    public ResponseModel updateById(@RequestBody RequestModel<RoleVO> requestModel) {
        return roleService.updateById(requestModel);
    }

    @PostMapping("/deleteById")
    @HasPermission("roleManage:delete")
    @Log(pageTitle = "根据id删除")
    public ResponseModel deleteById(@RequestBody RequestModel<RoleVO> requestModel) {
        return roleService.deleteById(requestModel);
    }

    @PostMapping("/queryById")
    @HasPermission("roleManage:find")
    @Log(pageTitle = "根据id查询一条记录")
    public ResponseModel queryById(@RequestBody RequestModel<RoleVO> requestModel) {
        return roleService.queryById(requestModel);
    }

    @PostMapping("/queryByPage")
    @HasPermission("roleManage:find")
    @Log(pageTitle = "根据参数分页查询列表")
    public ResponseModel queryByPage(@RequestBody RequestModel<RoleVO> requestModel) {
        return roleService.queryByPage(requestModel);
    }

    @PostMapping("/queryMenuActionTreeForRole")
    @HasPermission("roleManage:find")
    @Log(pageTitle = "查询角色的菜单按钮树")
    public ResponseModel queryMenuActionTreeForRole(@RequestBody RequestModel<RoleVO> requestModel){
        return roleService.queryMenuActionTreeForRole(requestModel);
    }

    @PostMapping("/saveMenuActionTreeForRole")
    @HasPermission("roleManage:edit")
    @Log(pageTitle = "保存角色的菜单按钮树")
    public ResponseModel saveMenuActionTreeForRole(@RequestBody RequestModel<RoleVO> requestModel){
        return roleService.saveMenuActionTreeForRole(requestModel);
    }


    @PostMapping("/queryOrgTreeForRole")
    @HasPermission("roleManage:find")
    @Log(pageTitle = "查询角色关联的组织机构")
    public ResponseModel queryOrgTreeForRole(@RequestBody RequestModel<RoleVO> requestModel){
        return roleService.queryOrgTreeForRole(requestModel);

    }

    @PostMapping("/queryUserOrgTreeForRole")
    @HasPermission("roleManage:find")
    @Log(pageTitle = "查询用户角色关联的组织机构")
    public ResponseModel queryUserOrgTreeForRole(@RequestBody RequestModel<RoleVO> requestModel){
        return roleService.queryUserOrgTreeForRole(requestModel);
    }

    @PostMapping("/saveOrgTreeForRole")
    @HasPermission("roleManage:edit")
    @Log(pageTitle = "保存角色关联的组织机构")
    public ResponseModel saveOrgTreeForRole(@RequestBody RequestModel<RoleVO> requestModel){
        return roleService.saveOrgTreeForRole(requestModel);
    }
    @PostMapping("/saveOrgTreeForUserRole")
    @HasPermission("roleManage:edit")
    @Log(pageTitle = "保存用户角色关联的组织机构")
    public ResponseModel saveOrgTreeForUserRole(@RequestBody RequestModel<RoleVO> requestModel){
        return roleService.saveOrgTreeForUserRole(requestModel);
    }
}

