package com.github.anji.plus.modules.menuextension.service;

import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import com.github.anji.plus.modules.menuextension.controller.dto.GaeaCommonConditionDTO;
import com.github.anji.plus.modules.menuextension.controller.param.ComConditionQueryParam;
import com.github.anji.plus.modules.menuextension.controller.param.GaeaCommonConditionParam;
import com.github.anji.plus.modules.menuextension.entity.GaeaCommonCondition;

import java.util.List;

/**
 * (GaeaCommonCondition)Service
 *
 * @author makejava
 * @since 2021-02-02 14:42:40
 */
public interface GaeaCommonConditionService extends GaeaBaseService<GaeaCommonConditionParam, GaeaCommonCondition> {
    /**
     * 根据用户，菜单查询用户设置的常用查询
     * @param queryParam
     * @return
     */
    List<GaeaCommonConditionDTO> queryByCondition(ComConditionQueryParam queryParam);
}