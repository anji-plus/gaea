package com.github.anji.plus.modules.menuextension.service;

import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import com.github.anji.plus.modules.menuextension.controller.dto.GaeaQueryConditionDTO;
import com.github.anji.plus.modules.menuextension.controller.param.GaeaQueryConditionParam;
import com.github.anji.plus.modules.menuextension.entity.GaeaQueryCondition;

import java.util.List;

/**
 * (GaeaQueryCondition)Service
 * 高级查询相关
 * @author peiyanni
 * @since 2021-02-04 17:16:01
 */
public interface GaeaQueryConditionService extends GaeaBaseService<GaeaQueryConditionParam, GaeaQueryCondition> {

    /**
     * 根据菜单和表格code查询设置的高级查询
     * @param requestParam
     * @return
     */
    List<GaeaQueryCondition> queryCondition(GaeaQueryConditionDTO requestParam);
}