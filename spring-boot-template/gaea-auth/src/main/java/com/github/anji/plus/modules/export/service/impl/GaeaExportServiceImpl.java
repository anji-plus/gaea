package com.github.anji.plus.modules.export.service.impl;

import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.github.anji.plus.modules.export.dao.GaeaExportMapper;
import com.github.anji.plus.modules.export.dao.entity.GaeaExport;
import com.github.anji.plus.modules.export.service.GaeaExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}