package com.anji.mirror.auth.controller;

import com.anji.mirror.common.annotation.Log;
import com.anji.mirror.common.model.LogVO;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.service.AsyncLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by raodeming on 2020/9/11.
 */
@RestController
@RequestMapping("/test")
public class DemoController {

    @PostMapping("/log")
    @Log(pageTitle = "测试一下")
    public ResponseModel create(@RequestBody RequestModel<LogVO> requestModel) {

        return ResponseModel.success();
    }
}
