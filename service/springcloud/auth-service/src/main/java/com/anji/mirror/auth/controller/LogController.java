package com.anji.mirror.auth.controller;

import com.anji.mirror.auth.service.LogService;
import com.anji.mirror.common.annotation.Log;
import com.anji.mirror.common.model.LogVO;
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
 * 用户操作日志表 前端控制器
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @PostMapping("/create")
    @HasPermission("logManage:add")
    @Log(pageTitle = "新增")
    public ResponseModel create(@RequestBody RequestModel<LogVO> requestModel) {
        return logService.create(requestModel);
    }

    @PostMapping("/updateById")
    @HasPermission("logManage:edit")
    @Log(pageTitle = "根据id修改")
    public ResponseModel updateById(@RequestBody RequestModel<LogVO> requestModel) {
        return logService.updateById(requestModel);
    }

    @PostMapping("/deleteById")
    @HasPermission("logManage:delete")
    @Log(pageTitle = "根据id删除")
    public ResponseModel deleteById(@RequestBody RequestModel<LogVO> requestModel) {
        return logService.deleteById(requestModel);
    }

    @PostMapping("/queryById")
    @HasPermission("logManage:find")
    @Log(pageTitle = "根据id查询一条记录")
    public ResponseModel queryById(@RequestBody RequestModel<LogVO> requestModel) {
        return logService.queryById(requestModel);
    }

    @PostMapping("/queryByPage")
    @HasPermission("logManage:find")
    @Log(pageTitle = "根据参数分页查询列表")
    public ResponseModel queryByPage(@RequestBody RequestModel<LogVO> requestModel) {
        return logService.queryByPage(requestModel);
    }

    /************************** 以下为跨服务调用方法 ****************************/
    //微服务调用--所有接口进来后保存操作日志
    @PostMapping("/saveOperatorLog")
    @HasPermission("logManage:add")
    @Log(pageTitle = "微服务调用--所有接口进来后保存操作日志")
    public void saveOperatorLog(@RequestBody LogVO logVO) {
        logService.saveOperatorLog(logVO);
    }
    /************************** 以上为跨服务调用方法 ****************************/
}

