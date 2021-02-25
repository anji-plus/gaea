package com.anjiplus.template.business.modules.export.service;

import com.anjiplus.template.business.modules.export.dao.entity.GaeaExport;
import com.anjiplus.template.business.modules.export.controller.param.GaeaExportParam;
import com.anjiplus.template.business.modules.export.controller.param.GaeaExportQueryParam;
import com.anjiplus.gaea.export.vo.ExportOperation;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.anji.plus.gaea.curd.service.GaeaBaseService;

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
