package com.anji.plus.modules.export.service.impl;

import com.anji.plus.modules.export.dao.GaeaExportMapper;
import com.anji.plus.modules.export.dao.entity.GaeaExport;
import com.anji.plus.modules.export.service.GaeaExportService;
import com.anji.plus.modules.file.dao.GaeaFileMapper;
import com.anji.plus.modules.file.entity.GaeaFile;
import com.anjiplus.gaea.export.vo.ExportOperation;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.anji.plus.aop.GaeaQuery;
import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.anji.plus.modules.export.controller.param.GaeaExportQueryParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 导出中心(GaeaExport)ServiceImpl
 *
 * @author makejava
 * @since 2021-02-07 17:12:24
 */
@Service
public class GaeaExportServiceImpl implements GaeaExportService {
    @Autowired
    private GaeaExportMapper gaeaExportMapper;
    @Autowired
    private GaeaFileMapper gaeaFileMapper;

    @Override
    public GaeaBaseMapper<GaeaExport> getMapper() {
        return gaeaExportMapper;
    }


    @Override
    @GaeaQuery
    public Page<GaeaExport> getExportListPage(GaeaExportQueryParam queryParam,QueryWrapper...qe) {
        Page<GaeaExport> page=new Page<>(queryParam.getPageNumber(),queryParam.getPageSize());
        List<GaeaExport> gaeaExports=gaeaExportMapper.queryExportInfo(page,queryParam,qe[0]);
        page.setRecords(gaeaExports);
        return page;
    }

    @Override
    @Transactional
    public Boolean saveExportLog(ExportOperation exportOperation) {
        //需要保存两张表数据 gaea_file ,gaea_export数据
        Date nowDate=new Date();
        GaeaFile gaeaFile=new GaeaFile();
        gaeaFile.setFileId(exportOperation.getFileId());
        gaeaFile.setFilePath(exportOperation.getFilePath());
        gaeaFile.setCreateBy(exportOperation.getCreaterUsername());
        gaeaFile.setCreateTime(nowDate);
        gaeaFile.setUpdateBy(exportOperation.getCreaterUsername());
        gaeaFile.setUpdateTime(nowDate);
        gaeaFileMapper.insert(gaeaFile);
        GaeaExport export=new GaeaExport();
        BeanUtils.copyProperties(exportOperation,export);
        export.setCreateBy(exportOperation.getCreaterUsername());
        export.setCreateTime(nowDate);
        export.setUpdateBy(exportOperation.getCreaterUsername());
        export.setUpdateTime(nowDate);
        gaeaExportMapper.insert(export);
        return true;
    }
}
