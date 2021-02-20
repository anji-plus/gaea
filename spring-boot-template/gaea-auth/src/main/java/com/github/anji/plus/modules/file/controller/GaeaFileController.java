package com.github.anji.plus.modules.file.controller;

import com.anji.captcha.model.common.ResponseModel;
import com.github.anji.plus.gaea.bean.ResponseBean;
import com.github.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import com.github.anji.plus.modules.file.controller.dto.GaeaFileDTO;
import com.github.anji.plus.modules.file.controller.param.GaeaFileParam;
import com.github.anji.plus.modules.file.entity.GaeaFile;
import com.github.anji.plus.modules.file.service.GaeaFileService;
import io.swagger.annotations.Api;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * (GaeaFile)实体类
 *
 * @author peiyanni
 * @since 2021-02-18 14:48:33
 */
@Controller
@RequestMapping("/file")
@Api(value = "/file", tags = "")
public class GaeaFileController {
    @Autowired
    private GaeaFileService gaeaFileService;

    @PostMapping("/upload")
    public ResponseBean upload(@RequestParam("file") MultipartFile file) {
        return ResponseBean.builder().message("success").data((gaeaFileService.upload(file))).build();
    }

    @GetMapping(value="/download/{fileId}")
    public ResponseEntity<byte[]> download(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileId") String fileId){
        return gaeaFileService.download(request, response, fileId);
    }
}