package com.anji.mirror.auth.controller;

import com.anji.mirror.common.annotation.Log;
import com.anji.mirror.common.model.ExportVO;
import com.anji.mirror.auth.service.ExportService;
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
 * 导出中心 前端控制器
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-11-23
 */
@RestController
@RequestMapping("/export")
public class ExportController {

    @Autowired
    private ExportService exportService;

    @PostMapping("/create")
    @HasPermission("exportManage:add")
    @Log(pageTitle = "创建导出记录")
    public ResponseModel create(@RequestBody RequestModel<ExportVO> requestModel) {
        return exportService.create(requestModel);
    }

    @PostMapping("/deleteById")
    @HasPermission("exportManage:delete")
    @Log(pageTitle = "根据id删除导出记录")
    public ResponseModel deleteById(@RequestBody RequestModel<ExportVO> requestModel) {
        return exportService.deleteById(requestModel);
    }

    @PostMapping("/queryById")
    @HasPermission("exportManage:find")
    @Log(pageTitle = "根据id查询一条导出记录")
    public ResponseModel queryById(@RequestBody RequestModel<ExportVO> requestModel) {
        return exportService.queryById(requestModel);
    }

    @PostMapping("/queryByPage")
    @HasPermission("exportManage:find")
    @Log(pageTitle = "根据参数分页查询导出记录列表")
    public ResponseModel queryByPage(@RequestBody RequestModel<ExportVO> requestModel) {
        return exportService.queryByPage(requestModel);
    }



    @PostMapping("/exportFile")
    @Log(pageTitle = "导出(PDF或者excel)")
    public ResponseModel export(@RequestBody ExportVO exportVO) {
        return ResponseModel.success(exportService.export(exportVO));
    }
}

