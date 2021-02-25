package com.github.anji.plus.modules.export.controller;

import com.anjiplus.gaea.export.vo.ExportOperation;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.anji.plus.gaea.bean.ResponseBean;
import com.github.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import com.github.anji.plus.gaea.utils.GaeaBeanUtils;
import com.github.anji.plus.modules.export.controller.dto.GaeaExportDTO;
import com.github.anji.plus.modules.export.controller.param.GaeaExportParam;
import com.github.anji.plus.modules.export.controller.param.GaeaExportQueryParam;
import com.github.anji.plus.modules.export.dao.entity.GaeaExport;
import com.github.anji.plus.modules.export.service.GaeaExportService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 导出中心(GaeaExport)实体类
 *
 * @author makejava
 * @since 2021-02-07 17:12:31
 */
@RestController
@RequestMapping("/export")
@Api(value = "/export", tags = "导出中心")
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

    @PostMapping("/queryAdvanceExport")
    public ResponseBean queryExportInfo(@RequestBody GaeaExportQueryParam param) {
        Page<GaeaExport> exportList=gaeaExportService.getExportListPage(param);
        List<GaeaExportDTO> list = exportList.getRecords().stream()
                .map(entity -> GaeaBeanUtils.copyAndFormatter(entity, getDTO()))
                .collect(Collectors.toList());
        Page<GaeaExportDTO> pageDto = new Page<>();
        pageDto.setCurrent(exportList.getCurrent());
        pageDto.setRecords(list);
        pageDto.setPages(exportList.getPages());
        pageDto.setTotal(exportList.getTotal());
        pageDto.setSize(exportList.getSize());
        return responseSuccessWithData(pageDto);
    }

    @PostMapping("/saveExportLog")
    public Boolean export(@RequestBody ExportOperation exportOperation) {
        return gaeaExportService.saveExportLog(exportOperation);
    }

}