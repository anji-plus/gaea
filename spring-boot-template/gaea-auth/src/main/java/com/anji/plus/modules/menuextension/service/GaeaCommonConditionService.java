package com.anji.plus.modules.menuextension.service;

import com.anji.plus.dto.DynamicQueryBo;
import com.anji.plus.modules.menuextension.controller.dto.GaeaCommonConditionDTO;
import com.anji.plus.modules.menuextension.controller.param.ComConditionQueryParam;
import com.anji.plus.modules.menuextension.controller.param.CommonConditionInputBO;
import com.anji.plus.modules.menuextension.controller.param.GaeaCommonConditionParam;
import com.anji.plus.modules.menuextension.entity.GaeaCommonCondition;
import com.anji.plus.gaea.curd.service.GaeaBaseService;

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

    /**
     * 根据commonID查询设置的常用查询
     * @param commonId
     * @return
     */
    List<DynamicQueryBo> getDynamicQueryBoListById(Long commonId);

    /**
     * 常用查询新增
     * @param t
     * @return
     */
    boolean saveCommonCondition(CommonConditionInputBO t);
}
