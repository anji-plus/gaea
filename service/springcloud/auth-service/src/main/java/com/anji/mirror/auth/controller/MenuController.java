package com.anji.mirror.auth.controller;

import com.anji.mirror.auth.domain.vo.MenuVO;
import com.anji.mirror.auth.service.MenuService;
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
 * 菜单表 前端控制器
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping("/create")
    @HasPermission("menuManage:add")
    @Log(pageTitle = "创建菜单")
    public ResponseModel create(@RequestBody RequestModel<MenuVO> requestModel) {
        return menuService.create(requestModel);
    }

    @PostMapping("/updateById")
    @HasPermission("menuManage:edit")
    @Log(pageTitle = "根据id修改菜单")
    public ResponseModel updateById(@RequestBody RequestModel<MenuVO> requestModel) {
        return menuService.updateById(requestModel);
    }

    @PostMapping("/deleteById")
    @HasPermission("menuManage:delete")
    @Log(pageTitle = "根据id删除菜单")
    public ResponseModel deleteById(@RequestBody RequestModel<MenuVO> requestModel) {
        return menuService.deleteById(requestModel);
    }

    @PostMapping("/queryById")
    @HasPermission("menuManage:find")
    @Log(pageTitle = "根据id查询一条菜单记录")
    public ResponseModel queryById(@RequestBody RequestModel<MenuVO> requestModel) {
        return menuService.queryById(requestModel);
    }

    @PostMapping("/queryByPage")
    @HasPermission("menuManage:find")
    @Log(pageTitle = "根据参数分页查询菜单列表")
    public ResponseModel queryByPage(@RequestBody RequestModel<MenuVO> requestModel) {
        return menuService.queryByPage(requestModel);
    }

    @PostMapping("/queryActionTreeForMenu")
    @HasPermission("menuManage:find")
    @Log(pageTitle = "查询菜单的关联按钮树")
    public ResponseModel queryActionTreeForMenu(@RequestBody RequestModel<MenuVO> requestModel){
        return menuService.queryActionTreeForMenu(requestModel);
    }

    @PostMapping("/saveActionTreeForMenu")
    @HasPermission("menuManage:edit")
    @Log(pageTitle = "保存菜单的关联按钮树")
    public ResponseModel saveActionTreeForMenu(@RequestBody RequestModel<MenuVO> requestModel){
        return menuService.saveActionTreeForMenu(requestModel);
    }
}

