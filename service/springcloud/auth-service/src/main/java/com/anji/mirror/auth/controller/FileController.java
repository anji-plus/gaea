package com.anji.mirror.auth.controller;

import com.anji.mirror.auth.service.FileService;
import com.anji.mirror.common.annotation.Log;
import com.anji.mirror.common.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 组织机构表 前端控制器
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @ResponseBody
    @PostMapping("/upload")
    @Log(pageTitle = "文件上传")
    public ResponseModel upload(@RequestParam("file") MultipartFile file) {
        return fileService.upload(file);
    }

    @RequestMapping(value="/download/{fileId}")
    @Log(pageTitle = "文件下载")
    public ResponseEntity<byte[]> download(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileId") String fileId){
        return fileService.download(request, response, fileId);
    }
}
