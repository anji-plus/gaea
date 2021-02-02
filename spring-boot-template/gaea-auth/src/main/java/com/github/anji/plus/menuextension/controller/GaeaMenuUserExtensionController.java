package com.github.anji.plus.menuextension.controller;

import com.github.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import com.github.anji.plus.menuextension.controller.dto.GaeaMenuUserExtensionDTO;
import com.github.anji.plus.menuextension.controller.param.GaeaMenuUserExtensionParam;
import com.github.anji.plus.menuextension.entity.GaeaMenuUserExtension;
import com.github.anji.plus.menuextension.service.GaeaMenuUserExtensionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (GaeaMenuUserExtension)实体类
 * 用户自定义列相关
 * @author makejava
 * @since 2021-02-02 14:40:53
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