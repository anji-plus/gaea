package com.github.anji.plus.modules.export.controller;

import com.github.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import com.github.anji.plus.modules.export.controller.dto.GaeaExportDTO;
import com.github.anji.plus.modules.export.controller.param.GaeaExportParam;
import com.github.anji.plus.modules.export.dao.entity.GaeaExport;
import com.github.anji.plus.modules.export.service.GaeaExportService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 导出中心(GaeaExport)实体类
 *
 * @author makejava
 * @since 2021-02-07 17:12:31
 */
@RestController
@RequestMapping("/gaeaExport")
@Api(value = "/gaeaExport", tags = "导出中心")
public class GaeaExportController extends GaeaBaseController<GaeaExportParam, GaeaExport, GaeaExportDTO> {
    @Autowired
    private GaeaExportService gaeaExportService;

    @Override
    public GaeaBaseService<GaeaExportParam, GaeaExport> getService() {
        return gaeaExportService;
    }

    @Override
    public GaeaExport getEntity() {
        return new GaeaExport();
    }

    @Override
    public GaeaExportDTO getDTO() {
        return new GaeaExportDTO();
    }
}