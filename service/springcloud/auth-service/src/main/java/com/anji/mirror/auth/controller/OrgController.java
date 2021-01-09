package com.anji.mirror.auth.controller;

import com.anji.mirror.auth.service.OrgService;
import com.anji.mirror.auth.domain.vo.OrgVO;
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
 * 组织机构表 前端控制器
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
@RestController
@RequestMapping("/org")
public class OrgController {

    @Autowired
    private OrgService orgService;

    @PostMapping("/create")
    @HasPermission("orgManage:add")
    @Log(pageTitle = "创建组织")
    public ResponseModel create(@RequestBody RequestModel<OrgVO> requestModel) {
        return orgService.create(requestModel);
    }

    @PostMapping("/updateById")
    @HasPermission("orgManage:edit")
    @Log(pageTitle = "根据id修改组织")
    public ResponseModel updateById(@RequestBody RequestModel<OrgVO> requestModel) {
        return orgService.updateById(requestModel);
    }

    @PostMapping("/deleteById")
    @HasPermission("orgManage:delete")
    @Log(pageTitle = "根据id删除组织")
    public ResponseModel deleteById(@RequestBody RequestModel<OrgVO> requestModel) {
        return orgService.deleteById(requestModel);
    }

    @PostMapping("/queryById")
    @HasPermission("orgManage:find")
    @Log(pageTitle = "根据id查询一条组织记录")
    public ResponseModel queryById(@RequestBody RequestModel<OrgVO> requestModel) {
        return orgService.queryById(requestModel);
    }

    @PostMapping("/queryByPage")
    @HasPermission("orgManage:find")
    @Log(pageTitle = "根据参数分页查询组织列表")
    public ResponseModel queryByPage(@RequestBody RequestModel<OrgVO> requestModel) {
        return orgService.queryByPage(requestModel);
    }

    @PostMapping("/selectOption")
    @HasPermission("orgManage:find|deviceInfoManage:add|deviceInfoManage:edit")
    @Log(pageTitle = "前端select选择器下拉数据")
    public ResponseModel selectOption(@RequestBody RequestModel<OrgVO> requestModel) {
        return orgService.selectOption(requestModel);
    }
}

