package com.anji.mirror.auth.service.impl;

import com.anji.mirror.auth.domain.po.ExportPO;
import com.anji.mirror.auth.domain.po.FilePO;
import com.anji.mirror.auth.mapper.ExportMapper;
import com.anji.mirror.auth.mapper.FileMapper;
import com.anji.mirror.auth.service.ExportService;
import com.anji.mirror.common.enums.FileStatusEnum;
import com.anji.mirror.common.enums.RepCodeEnum;
import com.anji.mirror.common.model.ExportVO;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.utils.BeanUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 导出中心 服务实现类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-11-23
 */
@Service
public class ExportServiceImpl extends ServiceImpl<ExportMapper, ExportPO> implements ExportService {

    private static Logger logger = LoggerFactory.getLogger(ExportServiceImpl.class);

    @Value("${customer.file.dist-path}")
    private String dictPath;

    @Autowired
    private ExportMapper exportMapper;

    @Autowired
    private FileMapper fileMapper;

    /**
     * 根据数据库必填项，校验是否为空，不校验主键
     *
     * @param exportVO
     * @return
     */
    private ResponseModel validateCreateFieldNotNull(ExportVO exportVO) {
        if (exportVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        /* 该片段由生成器产生，请根据实际情况修改
        if(exportVO.getExportId() == null){
            return RepCodeEnum.NULL_ERROR.parseError("exportId");
        }
        if(StringUtils.isBlank(exportVO.getFileId())){
            return RepCodeEnum.NULL_ERROR.parseError("fileId");
        }
        if(StringUtils.isBlank(exportVO.getFileName())){
            return RepCodeEnum.NULL_ERROR.parseError("fileName");
        }
        if(exportVO.getResultStartTime() == null){
            return RepCodeEnum.NULL_ERROR.parseError("resultStartTime");
        }
        if(exportVO.getResultEndTime() == null){
            return RepCodeEnum.NULL_ERROR.parseError("resultEndTime");
        }
        if(exportVO.getResultSize() == null){
            return RepCodeEnum.NULL_ERROR.parseError("resultSize");
        }
        if(exportVO.getFileCreateTime() == null){
            return RepCodeEnum.NULL_ERROR.parseError("fileCreateTime");
        }
        if(exportVO.getFileFinishTime() == null){
            return RepCodeEnum.NULL_ERROR.parseError("fileFinishTime");
        }
        if(StringUtils.isBlank(exportVO.getFileStatus())){
            return RepCodeEnum.NULL_ERROR.parseError("fileStatus");
        }
        if(exportVO.getCreaterUserid() == null){
            return RepCodeEnum.NULL_ERROR.parseError("createrUserid");
        }
        if(StringUtils.isBlank(exportVO.getCreaterUsername())){
            return RepCodeEnum.NULL_ERROR.parseError("createrUsername");
        }
        if(StringUtils.isBlank(exportVO.getRemark())){
            return RepCodeEnum.NULL_ERROR.parseError("remark");
        }
        */
        return ResponseModel.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel create(RequestModel<ExportVO> requestModel) {
        //参数校验
        ExportVO exportVO = requestModel.getData();
        ResponseModel valid = validateCreateFieldNotNull(exportVO);
        if (valid.isError()) {
            return valid;
        }
        //业务校验
        //...todo

        //业务处理
        String operator = requestModel.getOpUserName();

        exportVO.setCreaterUserid(requestModel.getOpUserId());
        exportVO.setCreaterUsername(requestModel.getOpUserName());

        ExportPO exportPO = new ExportPO();
        BeanUtils.copyProperties(exportVO, exportPO);
        boolean flag = save(exportPO);

        //返回结果
        if (flag) {
            return ResponseModel.success(exportPO);
        } else {
            return ResponseModel.fail(RepCodeEnum.FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel updateById(RequestModel<ExportVO> requestModel) {
        //参数校验
        ExportVO exportVO = requestModel.getData();
        if (exportVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long exportId = exportVO.getExportId();
        if (exportId == null) {
            return RepCodeEnum.NULL_ERROR.parseError("exportId");
        }
        //业务校验
        //...todo

        //业务处理
        ExportPO exportPO = getById(exportId);
        if (exportPO == null) {
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("exportId=" + exportId.longValue());
        }
        String operator = requestModel.getOpUserName();
        BeanUtils.copyProperties(exportVO, exportPO, true);
        boolean flag = updateById(exportPO);

        //返回结果
        if (flag) {
            return ResponseModel.success(exportPO);
        } else {
            return ResponseModel.fail("修改失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel deleteById(RequestModel<ExportVO> requestModel) {
        //参数校验
        ExportVO exportVO = requestModel.getData();
        if (exportVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long exportId = exportVO.getExportId();
        if (exportId == null) {
            return RepCodeEnum.NULL_ERROR.parseError("exportId");
        }

        //业务处理
        ExportPO exportPO = getById(exportId);
        if (exportPO == null) {
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("exportId=" + exportId.longValue());
        }
        boolean flag = removeById(exportId);

        //返回结果
        if (flag) {
            return ResponseModel.success("删除成功");
        } else {
            return ResponseModel.fail("删除失败");
        }
    }

    @Override
    public ResponseModel queryById(RequestModel<ExportVO> requestModel) {
        //参数校验
        ExportVO exportVO = requestModel.getData();
        if (exportVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long exportId = exportVO.getExportId();
        if (exportId == null) {
            return RepCodeEnum.NULL_ERROR.parseError("exportId");
        }

        //业务处理
        ExportPO exportPO = getById(exportId);
        if (exportPO == null) {
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("exportId=" + exportId.longValue());
        }

        //返回结果
        BeanUtils.copyProperties(exportPO, exportVO);
        return ResponseModel.success(exportVO);
    }

    @Override
    public ResponseModel queryByPage(RequestModel<ExportVO> requestModel) {
        //参数校验
        ExportVO exportVO = requestModel.getData();
        if (exportVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        //...todo

        //分页参数
        Page<ExportVO> page = new Page<ExportVO>(requestModel.getCurrentPage(), requestModel.getPageSize());

        //业务处理
        IPage<ExportVO> pageList = exportMapper.queryByPage(page, exportVO);

        //返回结果
        return ResponseModel.success(pageList);
    }

    /**
     * 导出(PDF或者excel)
     * 简单的导出和模板导出
     *
     * @param exportVO
     * @return
     */
    @Override
    public boolean export(ExportVO exportVO) {
        boolean result = false;
        ExportPO exportPO = new ExportPO();
        logger.info("------>导出成功<-----:路径：{}", exportVO.getFilePath());
//        // 生成文件唯一性标识
//        String fileId = UUID.randomUUID().toString();
        //开始生成excel前，在下载中心表t_export中增加一条记录,状态是生成中
        BeanUtils.copyProperties(exportVO, exportPO);
        exportMapper.insert(exportPO);
        if (exportVO.getFileStatus().equalsIgnoreCase(FileStatusEnum.SUCCESS.getCodeValue())) {
            //在t_file插入一条记录
            FilePO filePO = new FilePO();
            filePO.setFileId(exportVO.getFileId());
            filePO.setFileInstruction(exportVO.getFileTitle());
            filePO.setFilePath(exportVO.getFilePath());
            filePO.setFileCreateTime(LocalDateTime.now());
            fileMapper.insert(filePO);
        }

        return result;
    }

}
