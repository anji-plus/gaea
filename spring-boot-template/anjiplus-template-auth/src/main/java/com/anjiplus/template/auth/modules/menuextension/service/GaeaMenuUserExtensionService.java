package com.anjiplus.template.auth.modules.menuextension.service;

import com.anji.plus.gaea.curd.service.GaeaBaseService;
import com.anjiplus.template.auth.modules.menuextension.controller.dto.GaeaMenuUserExtensionListDTO;
import com.anjiplus.template.auth.modules.menuextension.controller.param.GaeaMenuUserExtensionParam;
import com.anjiplus.template.auth.modules.menuextension.entity.GaeaMenuUserExtension;

import java.util.List;

/**
 * (GaeaMenuUserExtension)Service
 *
 * @author makejava
 * @since 2021-02-04 17:15:36
 */
public interface GaeaMenuUserExtensionService extends GaeaBaseService<GaeaMenuUserExtensionParam, GaeaMenuUserExtension> {
    /**
     * 查询用户自定义列-列头
     * @param currentUser
     * @param menuCode
     * @param tableCode
     * @return
     */
    List<GaeaMenuUserExtensionListDTO> getUserMenuList(String currentUser, String menuCode, String tableCode);

    /**
     * 批量修改用户自定义列配置信息
     * @param reqData
     * @return
     */
    Boolean batchUpdate(List<GaeaMenuUserExtensionListDTO> reqData);
}
