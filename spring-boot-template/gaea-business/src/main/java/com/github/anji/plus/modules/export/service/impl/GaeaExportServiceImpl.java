package com.github.anji.plus.modules.export.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.anji.plus.aop.GaeaQuery;
import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.github.anji.plus.modules.export.controller.param.GaeaExportQueryParam;
import com.github.anji.plus.modules.export.dao.GaeaExportMapper;
import com.github.anji.plus.modules.export.dao.entity.GaeaExport;
import com.github.anji.plus.modules.export.service.GaeaExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

    @Override
    public GaeaBaseMapper<GaeaExport> getMapper() {
        return gaeaExportMapper;
    }


    @Override
    @GaeaQuery
    public Page<GaeaExport> getExportListPage(GaeaExportQueryParam queryParam) {
        Page<GaeaExport> page=new Page<>(queryParam.getPageNo(),queryParam.getPageSize());
        List<GaeaExport> gaeaExports=gaeaExportMapper.queryExportInfo(page,queryParam,queryParam.getQueryWrapper());
        page.setRecords(gaeaExports);
        return page;
    }
}