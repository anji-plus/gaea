package com.github.anji.plus.modules.export.service;

import com.anjiplus.gaea.export.vo.ExportOperation;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.anji.plus.aop.GaeaQuery;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import com.github.anji.plus.modules.export.controller.dto.GaeaExportDTO;
import com.github.anji.plus.modules.export.controller.param.GaeaExportParam;
import com.github.anji.plus.modules.export.controller.param.GaeaExportQueryParam;
import com.github.anji.plus.modules.export.dao.entity.GaeaExport;

import java.util.List;

/**
 * 导出中心(GaeaExport)Service
 *
 * @author peiyanni
 * @since 2021-02-07 17:12:22
 */
public interface GaeaExportService extends GaeaBaseService<GaeaExportParam, GaeaExport> {
    /**
     * 导出中心-高级查询
     * @param queryParam
     * @return
     */
    Page<GaeaExport> getExportListPage(GaeaExportQueryParam queryParam);

    /**
     * 导出操作，保存导出日志信息到表Gaea_export
     * @param exportOperation
     * @return
     */
    Boolean saveExportLog(ExportOperation exportOperation);
}