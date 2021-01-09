package com.anji.mirror.push.controller;

import com.anji.mirror.common.annotation.Log;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.model.PushParamVO;
import com.anji.mirror.push.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anji gaea teams
 * @Date: 2020/10/21
 * @Description: 内部调用接口
 */
@RestController
@RequestMapping("/businessCall")
public class BusinessCallApiController {
    @Autowired
    private TemplateService templateService;


    @PostMapping("/sendPushByInternal")
    @Log(pageTitle = "推送通知发送")
    @ResponseBody
    public ResponseModel sendPushByInternal(@RequestBody PushParamVO pushParamVO) {
        return templateService.sendPush(pushParamVO);
    }
}
