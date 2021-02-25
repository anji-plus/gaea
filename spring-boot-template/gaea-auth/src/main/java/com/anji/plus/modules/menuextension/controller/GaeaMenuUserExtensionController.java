package com.anji.plus.modules.menuextension.controller;

import com.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.anji.plus.gaea.curd.service.GaeaBaseService;
import com.anji.plus.modules.menuextension.controller.dto.GaeaMenuUserExtensionDTO;
import com.anji.plus.modules.menuextension.controller.param.GaeaMenuUserExtensionParam;
import com.anji.plus.modules.menuextension.entity.GaeaMenuUserExtension;
import com.anji.plus.modules.menuextension.service.GaeaMenuUserExtensionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (GaeaMenuUserExtension)实体类
 * 自定义列相关
 * @author peiyanni
 * @since 2021-02-04 17:15:37
 */
@RestController
@RequestMapping("/gaeaMenuUserExtension")
@Api(value = "/gaeaMenuUserExtension", tags = "")
public class GaeaMenuUserExtensionController extends GaeaBaseController<GaeaMenuUserExtensionParam, GaeaMenuUserExtension, GaeaMenuUserExtensionDTO> {
    @Autowired
    private GaeaMenuUserExtensionService gaeaMenuUserExtensionService;

    @Override
    public GaeaBaseService<GaeaMenuUserExtensionParam, GaeaMenuUserExtension> getService() {
        return gaeaMenuUserExtensionService;
    }

    @Override
    public GaeaMenuUserExtension getEntity() {
        return new GaeaMenuUserExtension();
    }

    @Override
    public GaeaMenuUserExtensionDTO getDTO() {
        return new GaeaMenuUserExtensionDTO();
    }
}
