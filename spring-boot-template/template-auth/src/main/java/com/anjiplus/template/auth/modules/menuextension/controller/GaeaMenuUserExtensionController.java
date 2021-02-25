package com.anjiplus.template.auth.modules.menuextension.controller;

import com.anji.plus.gaea.bean.ResponseBean;
import com.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.anji.plus.gaea.curd.service.GaeaBaseService;
import com.anji.plus.gaea.holder.UserContentHolder;
import com.anjiplus.template.auth.modules.menuextension.controller.dto.GaeaMenuUserExtensionDTO;
import com.anjiplus.template.auth.modules.menuextension.controller.param.GaeaMenuUserExtensionParam;
import com.anjiplus.template.auth.modules.menuextension.entity.GaeaMenuUserExtension;
import com.anjiplus.template.auth.modules.menuextension.service.GaeaMenuUserExtensionService;
import com.anjiplus.template.auth.modules.menuextension.controller.dto.GaeaMenuUserExtensionListDTO;
import com.anjiplus.gaea.log.annotation.GaeaAuditLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * (GaeaMenuUserExtension)实体类
 * 用户自定义列相关
 * @author peiyanni
 * @since 2021-02-04 17:15:37
 */
@RestController
@RequestMapping("/userextension")
@Api(value = "/userextension", tags = "")
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

    @ApiOperation(value = "用户菜单扩展列表")
    @GetMapping(value = "/userMenuExtensions")
    public ResponseBean getUserMenuList(@RequestParam(value = "menuCode") String menuCode,
                                        @RequestParam(value = "tableCode") String tableCode) {
        List<GaeaMenuUserExtensionListDTO> list = gaeaMenuUserExtensionService.getUserMenuList(UserContentHolder.getContext().getUsername(), menuCode, tableCode);
        return responseSuccessWithData(list);
    }

    @ApiOperation(value = "用户菜单扩展保存")
    @PostMapping(value = "/userMenuExtensionsBatchSave")
    @GaeaAuditLog(pageTitle = "保存用户菜单自定义列")
    public ResponseBean batchSave(@RequestBody List<GaeaMenuUserExtensionListDTO> request) {
        return responseSuccessWithData(gaeaMenuUserExtensionService.batchUpdate(request));
    }
}