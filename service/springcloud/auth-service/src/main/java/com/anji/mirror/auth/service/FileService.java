package com.anji.mirror.auth.service;

import com.anji.mirror.auth.domain.po.FilePO;
import com.anji.mirror.common.model.ResponseModel;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 文件操作
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
public interface FileService extends IService<FilePO> {

    /**文件上传
     * @param file
     * @return
     */
    ResponseModel upload(@RequestParam("file") MultipartFile file);

    /** 根据fileId显示图片或者下载文件
     * @param request
     * @param response
     * @param fileId
     * @return
     */
    ResponseEntity<byte[]> download(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileId") String fileId);
}
