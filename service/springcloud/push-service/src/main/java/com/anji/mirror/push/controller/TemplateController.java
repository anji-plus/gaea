package com.anji.mirror.push.controller;

import com.anji.mirror.common.annotation.Log;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.security.HasPermission;
import com.anji.mirror.common.model.PushParamVO;
import com.anji.mirror.push.service.TemplateService;
import com.anji.mirror.push.domain.vo.TemplateVO;
import com.anji.mirror.push.domain.vo.TreeParamVO;
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
 * @since 2020-10-16
 */
@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @PostMapping("/create")
    @Log(pageTitle = "推送模板创建")
    @HasPermission("templateManage:add")
    public ResponseModel create(@RequestBody RequestModel<TemplateVO> requestModel) {
        return templateService.create(requestModel);
    }

    @PostMapping("/updateById")
    @Log(pageTitle = "推送模板更新")
    @HasPermission("templateManage:edit")
    public ResponseModel updateById(@RequestBody RequestModel<TemplateVO> requestModel) {
        return templateService.updateById(requestModel);
    }

    @PostMapping("/deleteById")
    @Log(pageTitle = "推送模板删除")
    @HasPermission("templateManage:delete")
    public ResponseModel deleteById(@RequestBody RequestModel<TemplateVO> requestModel) {
        return templateService.deleteById(requestModel);
    }

    @PostMapping("/queryById")
    @HasPermission("templateManage:find")
    @Log(pageTitle = "根据id查询一条推送模板")
    public ResponseModel queryById(@RequestBody RequestModel<TemplateVO> requestModel) {
        return templateService.queryById(requestModel);
    }

    @PostMapping("/queryByPage")
    @HasPermission("templateManage:find")
    @Log(pageTitle = "根据参数分页查询推送模板列表")
    public ResponseModel queryByPage(@RequestBody RequestModel<TemplateVO> requestModel) {
        return templateService.queryByPage(requestModel);
    }

    @PostMapping("/preview")
    @HasPermission("templateManage:find")
    @Log(pageTitle = "预览邮件模板")
    public ResponseModel preViewTemplate(@RequestBody RequestModel<TemplateVO> requestModel) {
        return templateService.preViewTemplate(requestModel);
    }

    @PostMapping("/testSendPush")
    @HasPermission("templateManage:test")
    @Log(pageTitle = "发送推送信息")
    public ResponseModel testSendPush(@RequestBody RequestModel<PushParamVO> requestModel) {//完成
        return templateService.sendPush(requestModel);
    }


    @PostMapping("/queryTree")
    @HasPermission("templateManage:find")
    @Log(pageTitle = "获取模板树")
    public ResponseModel queryTree(@RequestBody RequestModel<TreeParamVO> requestModel) {//完成
        return templateService.queryTree(requestModel);
    }



}

