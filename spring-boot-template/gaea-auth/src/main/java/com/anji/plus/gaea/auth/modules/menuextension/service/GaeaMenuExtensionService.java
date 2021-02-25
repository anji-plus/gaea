package com.anji.plus.gaea.auth.modules.menuextension.service;

import com.anji.plus.gaea.curd.service.GaeaBaseService;
import com.anji.plus.gaea.auth.modules.menuextension.controller.param.GaeaMenuExtensionParam;
import com.anji.plus.gaea.auth.modules.menuextension.entity.GaeaMenuExtension;

import java.util.List;

/**
 * (GaeaMenuExtension)Service
 *
 * @author makejava
 * @since 2021-02-04 17:14:15
 */
public interface GaeaMenuExtensionService extends GaeaBaseService<GaeaMenuExtensionParam, GaeaMenuExtension> {

    /**
     * 根据菜单code查询自定义列
     * @param menuCode
     * @return
     */
    List<GaeaMenuExtension> queryMenuExtensionList(String menuCode);
}
