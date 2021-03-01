package com.anji.plus.gaea.controller;

import com.anji.plus.gaea.bean.ResponseBean;
import com.anji.plus.gaea.init.InitRequestUrlMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 通用请求
 * @author lr
 * @since 2021-03-01
 */
@RestController
public class GaeaBootController {

    @Autowired
    private InitRequestUrlMappings initRequestUrlMappings;

    /**
     * 获取请求信息，用于微服务中获取其他模块的请求信息
     * @return
     */
    @GetMapping("/gaea/boot/requestInfos")
    public ResponseBean getRequestInfos() {
        List<InitRequestUrlMappings.RequestInfo> requestInfos = initRequestUrlMappings.getRequestInfos();
        return ResponseBean.builder().data(requestInfos).build();
    }
}
