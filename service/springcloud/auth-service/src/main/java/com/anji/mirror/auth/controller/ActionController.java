package com.anji.mirror.auth.controller;

import com.anji.mirror.auth.domain.vo.ActionVO;
import com.anji.mirror.auth.service.ActionService;
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
 * 按钮表 前端控制器
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
@RestController
@RequestMapping("/action")
public class ActionController {

    @Autowired
    private ActionService actionService;

    @PostMapping("/create")
    @HasPermission("actionManage:add")
    @Log(pageTitle = "创建按钮")
    public ResponseModel create(@RequestBody RequestModel<ActionVO> requestModel) {
        return actionService.create(requestModel);
    }

    @PostMapping("/updateById")
    @HasPermission("actionManage:edit")
    @Log(pageTitle = "根据id修改按钮")
    public ResponseModel updateById(@RequestBody RequestModel<ActionVO> requestModel) {
        return actionService.updateById(requestModel);
    }

    @PostMapping("/deleteById")
    @HasPermission("actionManage:delete")
    @Log(pageTitle = "根据id删除按钮")
    public ResponseModel deleteById(@RequestBody RequestModel<ActionVO> requestModel) {
        return actionService.deleteById(requestModel);
    }

    @PostMapping("/queryById")
    @HasPermission("actionManage:find")
    @Log(pageTitle = "根据id查询一条按钮记录")
    public ResponseModel queryById(@RequestBody RequestModel<ActionVO> requestModel) {
        return actionService.queryById(requestModel);
    }

    @PostMapping("/queryByPage")
    @HasPermission("actionManage:find")
    @Log(pageTitle = "根据参数分页查询按钮列表")
    public ResponseModel queryByPage(@RequestBody RequestModel<ActionVO> requestModel) {
        return actionService.queryByPage(requestModel);
    }
}

