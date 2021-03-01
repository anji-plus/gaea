package com.anjiplus.template.auth.modules.menuextension.dao;

import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.anjiplus.template.auth.modules.menuextension.controller.dto.GaeaCommonConditionDTO;
import com.anjiplus.template.auth.modules.menuextension.controller.param.ComConditionQueryParam;
import com.anjiplus.template.auth.modules.menuextension.entity.GaeaCommonCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (GaeaCommonCondition)Mapper
 *
 * @author makejava
 * @since 2021-02-02 14:42:40
 */
@Mapper
public interface GaeaCommonConditionMapper extends GaeaBaseMapper<GaeaCommonCondition> {

    List<GaeaCommonConditionDTO> queryByCondition(@Param("queryParam") ComConditionQueryParam queryParam);

}
