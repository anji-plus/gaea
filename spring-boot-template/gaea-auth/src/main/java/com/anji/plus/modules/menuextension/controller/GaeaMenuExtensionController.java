package com.anji.plus.modules.menuextension.controller;

import com.anji.plus.gaea.bean.ResponseBean;
import com.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.anji.plus.gaea.curd.service.GaeaBaseService;
import com.anji.plus.modules.menuextension.controller.dto.GaeaMenuExtensionDTO;
import com.anji.plus.modules.menuextension.controller.param.GaeaMenuExtensionParam;
import com.anji.plus.modules.menuextension.entity.GaeaMenuExtension;
import com.anji.plus.modules.menuextension.service.GaeaMenuExtensionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (GaeaMenuExtension)实体类
 * 自定义列相关
 * @author peiyanni
 * @since 2021-02-04 17:14:15
 */
@RestController
@RequestMapping("/menuextension")
@Api(value = "/menuextension", tags = "")
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

    @GetMapping("/queryMenuExtension/{menuCode}")
    public ResponseBean queryMenuExtension(@PathVariable("menuCode")String menuCode){
        return responseSuccessWithData(gaeaMenuExtensionService.queryMenuExtensionList(menuCode));
    }
}
