package com.github.anji.plus.modules.menuextension.controller;

import com.github.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import com.github.anji.plus.modules.menuextension.controller.dto.GaeaMenuExtensionDTO;
import com.github.anji.plus.modules.menuextension.controller.param.GaeaMenuExtensionParam;
import com.github.anji.plus.modules.menuextension.entity.GaeaMenuExtension;
import com.github.anji.plus.modules.menuextension.service.GaeaMenuExtensionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (GaeaMenuExtension)实体类
 *
 * @author makejava
 * @since 2021-02-04 17:14:15
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