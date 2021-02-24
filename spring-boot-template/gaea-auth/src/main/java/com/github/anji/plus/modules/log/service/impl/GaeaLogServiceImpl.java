package com.github.anji.plus.modules.log.service.impl;

import com.anjiplus.gaea.export.enums.ExportTypeEnum;
import com.anjiplus.gaea.export.utils.ExportUtil;
import com.anjiplus.gaea.export.vo.ExportOperation;
import com.anjiplus.gaea.log.aspect.LogOperation;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.anji.plus.common.RespCommonCode;
import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.github.anji.plus.gaea.exception.BusinessException;
import com.github.anji.plus.gaea.exception.BusinessExceptionBuilder;
import com.github.anji.plus.gaea.holder.UserContentHolder;
import com.github.anji.plus.modules.log.controller.param.GaeaLogParam;
import com.github.anji.plus.modules.log.dao.GaeaLogMapper;
import com.github.anji.plus.modules.log.entity.GaeaLog;
import com.github.anji.plus.modules.log.service.GaeaLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * (GaeaLog)ServiceImpl
 *
 * @author peiyanni
 * @since 2021-02-18 16:30:23
 */
@Service
public class GaeaLogServiceImpl implements GaeaLogService {
    @Autowired
    private GaeaLogMapper gaeaLogMapper;

    @Value("${file.dist-path}")
    private String dictPath;

    @Override
    public GaeaBaseMapper<GaeaLog> getMapper() {
        return gaeaLogMapper;
    }

    @Override
    public void saveCallbackInfo(LogOperation logOperation) {
        GaeaLog gaeaLog=new GaeaLog();
        BeanUtils.copyProperties(logOperation,gaeaLog);
        gaeaLog.setUserName(UserContentHolder.getContext().getUsername());
        gaeaLogMapper.insert(gaeaLog);
    }

    @Override
    public Boolean exportLogToFile(GaeaLogParam gaeaLogParam) {
        Page<GaeaLog> page=new Page<>(gaeaLogParam.getPageNumber(),gaeaLogParam.getPageSize());
        ExportOperation exportOperation = new ExportOperation();
        exportOperation.setResultStartTime(LocalDateTime.now());
        List<GaeaLog> list=gaeaLogMapper.queryLogInfo(page,gaeaLogParam);
        if(CollectionUtils.isEmpty(list)){
            throw BusinessExceptionBuilder.build(RespCommonCode.LIST_IS_EMPTY);
        }
        page.setRecords(list);
        exportOperation.setResultEndTime(LocalDateTime.now());
        exportOperation.setResultSize(Long.parseLong(list.size()+""));
        exportOperation.setExportType(ExportTypeEnum.SIMPLE_EXCEL.getCodeValue());
        exportOperation.setFileTitle("Export Log Info");
        exportOperation.setFilePath(dictPath);
        exportOperation.setList(list);
        exportOperation.setCreaterUsername(UserContentHolder.getContext().getUsername());
        //调用盖亚组件实现导出文件
        ExportUtil.getInstance().exportByFilePathSimple(exportOperation,GaeaLog.class);
        return true;
    }
}