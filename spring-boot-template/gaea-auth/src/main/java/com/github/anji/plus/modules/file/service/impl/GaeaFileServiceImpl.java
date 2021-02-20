package com.github.anji.plus.modules.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.anji.plus.common.RespCommonCode;
import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.github.anji.plus.gaea.exception.BusinessExceptionBuilder;
import com.github.anji.plus.modules.export.dao.GaeaExportMapper;
import com.github.anji.plus.modules.export.dao.entity.GaeaExport;
import com.github.anji.plus.modules.file.dao.GaeaFileMapper;
import com.github.anji.plus.modules.file.entity.GaeaFile;
import com.github.anji.plus.modules.file.service.GaeaFileService;
import com.github.anji.plus.util.StringPatternUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * (GaeaFile)ServiceImpl
 *
 * @author peiyanni
 * @since 2021-02-18 14:48:26
 */
@Service
@Slf4j
public class GaeaFileServiceImpl implements GaeaFileService {

    @Value("${file.dist-path}")
    private String dictPath;

    @Value("${file.white-list}")
    private String whiteList;

    @Value("${file.excelSuffix}")
    private String excelSuffix;

    @Value("${file.downloadPath}")
    private String fileDownloadPath;

    @Autowired
    private GaeaFileMapper gaeaFileMapper;
    @Autowired
    private GaeaExportMapper gaeaExportMapper;

    @Override
    public GaeaBaseMapper<GaeaFile> getMapper() {
        return gaeaFileMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String upload(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            if (StringUtils.isBlank(fileName)) {
                throw BusinessExceptionBuilder.build(RespCommonCode.FILE_EMPTY_FILENAME);
            }
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            //白名单校验(不区分大小写)
            List<String> list = new ArrayList<String>(Arrays.asList(whiteList.split("\\|")));
            list.addAll(list.stream().map(String::toUpperCase).collect(Collectors.toList()));
            if (!list.contains(suffixName)) {
                throw BusinessExceptionBuilder.build(RespCommonCode.FILE_SUFFIX_UNSUPPORTED);
            }
            // 生成文件唯一性标识
            String fileId = UUID.randomUUID().toString();
            String newFileName = fileId + suffixName;
            // 本地文件保存路径
            String filePath = dictPath + File.separator + newFileName;
            String urlPath = fileDownloadPath + File.separator + fileId;

            GaeaFile gaeaFile = new GaeaFile();
            gaeaFile.setFilePath(filePath);
            gaeaFile.setFileId(fileId);
            gaeaFile.setUrlPath(urlPath);
            gaeaFileMapper.insert(gaeaFile);

            //写文件 将文件保存/app/dictPath/upload下
            File dest = new File(dictPath + File.separator + newFileName);
            file.transferTo(dest);
            // 将完整的http访问路径返回
            return urlPath;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("file upload error: {}", e);
            throw BusinessExceptionBuilder.build(RespCommonCode.FILE_UPLOAD_ERROR);
        }
    }

    @Override
    public ResponseEntity<byte[]> download(HttpServletRequest request, HttpServletResponse response, String fileId) {
        try {
            String userAgent = request.getHeader("User-Agent");
            boolean isIEBrowser = userAgent.indexOf("MSIE") > 0;
            //根据fileId，从gaea_file中读出filePath
            LambdaQueryWrapper<GaeaFile> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(GaeaFile::getFileId, fileId);
            GaeaFile gaeaFile = gaeaFileMapper.selectOne(queryWrapper);
            if (null == gaeaFile) {
                throw BusinessExceptionBuilder.build(RespCommonCode.FILE_ONT_EXSIT);
            }
            //解析文件路径、文件名和后缀
            String filePath = gaeaFile.getFilePath();
            if (StringUtils.isBlank(filePath)) {
                throw BusinessExceptionBuilder.build(RespCommonCode.FILE_ONT_EXSIT);
            }
            String filename = filePath.substring(filePath.lastIndexOf(File.separator));
            String fileSuffix = filename.substring(filename.lastIndexOf("."));
            //特殊处理：如果是excel文件，则从t_export表中查询文件名
            List list = Arrays.asList(excelSuffix.split("\\|"));
            if (list.contains(fileSuffix)) {
                LambdaQueryWrapper<GaeaExport> exportWrapper = Wrappers.lambdaQuery();
                exportWrapper.eq(GaeaExport::getFileId, fileId);
                GaeaExport exportPO = gaeaExportMapper.selectOne(exportWrapper);
                if (null != exportPO) {
                    filename = exportPO.getFileTitle() + fileSuffix;
                }
            }
            //根据文件后缀来判断，是显示图片\视频\音频，还是下载文件
            File file = new File(filePath);
            ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
            builder.contentLength(file.length());
            if (StringPatternUtil.StringMatchIgnoreCase(fileSuffix, "(.png|.jpg|.jpeg|.bmp|.gif|.icon)")) {
                builder.cacheControl(CacheControl.noCache()).contentType(MediaType.IMAGE_PNG);
            } else if (StringPatternUtil.StringMatchIgnoreCase(fileSuffix, "(.flv|.swf|.mkv|.avi|.rm|.rmvb|.mpeg|.mpg|.ogg|.ogv|.mov|.wmv|.mp4|.webm|.wav|.mid|.mp3|.aac)")) {
                builder.header("Content-Type", "video/mp4; charset=UTF-8");
            } else {
                //application/octet-stream 二进制数据流（最常见的文件下载）
                builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
                filename = URLEncoder.encode(filename, "UTF-8");
                if (isIEBrowser) {
                    builder.header("Content-Disposition", "attachment; filename=" + filename);
                } else {
                    builder.header("Content-Disposition", "attacher; filename*=UTF-8''" + filename);
                }
            }
            return builder.body(FileUtils.readFileToByteArray(file));
        } catch (Exception e) {
            log.error("file download error: {}", e);
            return null;
        }
    }


}