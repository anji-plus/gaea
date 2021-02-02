package com.github.anji.plus.menuextension.controller;

import com.github.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import com.github.anji.plus.menuextension.controller.dto.GaeaMenuExtensionDTO;
import com.github.anji.plus.menuextension.controller.param.GaeaMenuExtensionParam;
import com.github.anji.plus.menuextension.entity.GaeaMenuExtension;
import com.github.anji.plus.menuextension.service.GaeaMenuExtensionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (GaeaMenuExtension)实体类
 * 菜单自定义列相关
 * @author makejava
 * @since 2021-02-02 14:39:09
 */
@RestController
@RequestMapping("/gaeaMenuExtension")
@Api(value = "/gaeaMenuExtension", tags = "")
public class GaeaMenuExtensionController extends GaeaBaseController<GaeaMenuExtensionParam, GaeaMenuExtension, GaeaMenuExtensionDTO> {
    @Autowired
    private GaeaMenuExtensionService gaeaMenuExtensionService;

    @Override
    public GaeaBaseService<GaeaMenuExtensionParam, GaeaMenuExtension> getService() {
        return gaeaMenuExtensionService;
    }

    @Override
    public GaeaMenuExtension getEntity() {
        return new GaeaMenuExtension();
    }

    @Override
    public GaeaMenuExtensionDTO getDTO() {
        return new GaeaMenuExtensionDTO();
    }
}