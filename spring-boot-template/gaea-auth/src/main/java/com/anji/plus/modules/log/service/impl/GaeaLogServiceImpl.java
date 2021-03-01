package com.anji.plus.modules.log.service.impl;

import com.anji.plus.modules.log.dao.GaeaLogMapper;
import com.anji.plus.modules.log.entity.GaeaLog;
import com.anji.plus.modules.log.service.GaeaLogService;
import com.anjiplus.gaea.export.enums.ExportTypeEnum;
import com.anjiplus.gaea.export.utils.ExportUtil;
import com.anjiplus.gaea.export.vo.ExportOperation;
import com.anjiplus.gaea.log.aspect.LogOperation;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.anji.plus.common.RespCommonCode;
import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.anji.plus.gaea.exception.BusinessExceptionBuilder;
import com.anji.plus.gaea.holder.UserContentHolder;
import com.anji.plus.modules.log.controller.param.GaeaLogParam;
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
        GaeaLog gaeaLog = new GaeaLog();
        BeanUtils.copyProperties(logOperation, gaeaLog);
        gaeaLog.setUserName(UserContentHolder.getContext().getUsername());
        gaeaLogMapper.insert(gaeaLog);
    }

    @Override
    public Boolean exportLogToFile(GaeaLogParam gaeaLogParam) {
        Page<GaeaLog> page = new Page<>(gaeaLogParam.getPageNumber(), gaeaLogParam.getPageSize());
        ExportOperation exportOperation = new ExportOperation();
        //指明导出数据查询到结果开始时间
        exportOperation.setResultStartTime(LocalDateTime.now());
        List<GaeaLog> list = gaeaLogMapper.queryLogInfo(page, gaeaLogParam);
        if (CollectionUtils.isEmpty(list)) {
            throw BusinessExceptionBuilder.build(RespCommonCode.LIST_IS_EMPTY);
        }
        page.setRecords(list);
        //指明导出数据查询到结果结束时间
        exportOperation.setResultEndTime(LocalDateTime.now());
        //指明导出数据查询到结果条数
        exportOperation.setResultSize(Long.parseLong(list.size() + ""));
        //指明采用什么模式导出
        exportOperation.setExportType(ExportTypeEnum.SIMPLE_EXCEL.getCodeValue());
        //设置导出的文件名
        exportOperation.setFileTitle("Export Log Info");
        //设置导出的文件存放目录
        exportOperation.setFilePath(dictPath);
        //设置导出的数据集合
        exportOperation.setList(list);
        //保存当前操作人
        exportOperation.setCreaterUsername(UserContentHolder.getContext().getUsername());
        //调用盖亚组件实现导出文件
        ExportUtil.getInstance().exportByFilePathSimple(exportOperation, GaeaLog.class);
        return true;
    }
}
