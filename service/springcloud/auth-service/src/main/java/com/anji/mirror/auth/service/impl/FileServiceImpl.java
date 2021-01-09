package com.anji.mirror.auth.service.impl;

import com.anji.mirror.auth.domain.po.ExportPO;
import com.anji.mirror.auth.domain.po.FilePO;
import com.anji.mirror.auth.mapper.ExportMapper;
import com.anji.mirror.auth.mapper.FileMapper;
import com.anji.mirror.auth.service.FileService;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.utils.StringPatternUtil;
import com.anji.mirror.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FilePO> implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Value("${customer.file.dist-path}")
    private String dictPath;

    @Value("${customer.file.white-list}")
    private String whiteList;

    @Value("${customer.file.excelSuffix}")
    private String excelSuffix;

    @Value("${customer.environment.path}")
    private String environmentFlag;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private ExportMapper exportMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel upload(MultipartFile file){
        try{
            // 获取原文件名及后缀
            String fileName = file.getOriginalFilename();
            if (StringUtils.isBlank(fileName)) {
                return ResponseModel.error("fileName is empty");
            }
            String suffixName = fileName.substring(fileName.lastIndexOf("."));

            //白名单校验(不区分大小写)
            List<String> list = new ArrayList<>( Arrays.asList(whiteList.split("\\|")));
            list.addAll(list.stream().map(String::toUpperCase).collect(Collectors.toList()));
            if (!list.contains(suffixName)) {
                return ResponseModel.error("不支持的后缀类型");
            }

            // 生成文件唯一性标识
            String fileId = UUID.randomUUID().toString();
            String newFileName = fileId + suffixName;

            // 本地文件保存路径
            String filePath = dictPath + newFileName;
            String urlPath = environmentFlag + "/auth-service/file/download/" + fileId;
            // 在t_file表中插入一条记录
            FilePO filePO = new FilePO();
            filePO.setFileId(fileId);
            filePO.setFilePath(filePath);
            filePO.setUrlPath(urlPath);
            filePO.setFileCreateTime(LocalDateTime.now());
            fileMapper.insert(filePO);

            //写文件 将文件保存/app/dictPath/upload下
            File dest = new File(dictPath + newFileName);
            file.transferTo(dest);

            // 将完整的http访问路径返回
            return ResponseModel.successData(urlPath);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("error: {}", e);
            return ResponseModel.error("failed to save");
        }
    }

    @Override
    public ResponseEntity<byte[]> download(HttpServletRequest request, HttpServletResponse response, String fileId) {
        try{
            String userAgent = request.getHeader("User-Agent");
            boolean isIEBrowser = userAgent.indexOf("MSIE") > 0;

            //根据fileId，从t_file中读出filePath
            QueryWrapper<FilePO> queryWrapper=new QueryWrapper<FilePO>();
            queryWrapper.eq("file_id",fileId);
            FilePO filePO = fileMapper.selectOne(queryWrapper);
            if (null == filePO) {
                ResponseModel.error("file is not exists");
            }
            //解析文件路径、文件名和后缀
            String filePath = filePO.getFilePath();
            if (StringUtils.isBlank(filePath)) {
                ResponseModel.error("file path not exists");
            }
            String filename = filePath.substring(filePath.lastIndexOf(File.separator));
            if (StringUtils.isBlank(filePath)) {
                ResponseModel.error("fileName is empty");
            }
            String fileSuffix = filename.substring(filename.lastIndexOf("."));

            //特殊处理：如果是excel文件，则从t_export表中查询文件名
            List list = Arrays.asList(excelSuffix.split("\\|")) ;
            if (list.contains(fileSuffix)) {
                QueryWrapper<ExportPO> wrapper=new QueryWrapper<ExportPO>();
                wrapper.eq("file_id",fileId);
                ExportPO exportPO = exportMapper.selectOne(wrapper);
                if (null != exportPO) {
                    filename = exportPO.getFileTitle() + fileSuffix;
                }
            }

            //根据文件后缀来判断，是显示图片\视频\音频，还是下载文件
            File file = new File(filePath);
            ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
            builder.contentLength(file.length());
            if(StringPatternUtil.StringMatchIgnoreCase(fileSuffix, "(.png|.jpg|.jpeg|.bmp|.gif|.icon)")){
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
        }catch (Exception e){
            logger.error("error: {}", e);
            return null;
        }
    }
}
