package com.anji.mirror.push.controller;

import com.anji.mirror.common.annotation.Log;
import com.anji.mirror.push.service.PushHistoryService;
import com.anji.mirror.push.domain.vo.PushHistoryVO;
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
 * 前端控制器
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-10-26
 */
@RestController
@RequestMapping("/pushHistory")
public class PushHistoryController {

    @Autowired
    private PushHistoryService pushHistoryService;

    @PostMapping("/queryById")
    @HasPermission("pushHistoryManage:find")
    @Log(pageTitle = "根据id查询一条记录")
    public ResponseModel queryById(@RequestBody RequestModel<PushHistoryVO> requestModel) {
        return pushHistoryService.queryById(requestModel);
    }

    @PostMapping("/queryByPage")
    @HasPermission("pushHistoryManage:find")
    @Log(pageTitle = "根据参数分页查询列表")
    public ResponseModel queryByPage(@RequestBody RequestModel<PushHistoryVO> requestModel) {
        return pushHistoryService.queryByPage(requestModel);
    }
}

