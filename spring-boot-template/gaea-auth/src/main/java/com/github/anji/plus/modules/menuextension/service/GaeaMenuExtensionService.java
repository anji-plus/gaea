package com.github.anji.plus.modules.menuextension.service;

import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import com.github.anji.plus.modules.menuextension.controller.param.GaeaMenuExtensionParam;
import com.github.anji.plus.modules.menuextension.entity.GaeaMenuExtension;

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