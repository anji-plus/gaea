package com.github.anji.plus.modules.menuextension.service.impl;

import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.github.anji.plus.modules.menuextension.controller.dto.GaeaCommonConditionDTO;
import com.github.anji.plus.modules.menuextension.controller.param.ComConditionQueryParam;
import com.github.anji.plus.modules.menuextension.dao.GaeaCommonConditionMapper;
import com.github.anji.plus.modules.menuextension.entity.GaeaCommonCondition;
import com.github.anji.plus.modules.menuextension.service.GaeaCommonConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (GaeaCommonCondition)ServiceImpl
 *
 * @author makejava
 * @since 2021-02-02 14:42:40
 */
@Service
public class GaeaCommonConditionServiceImpl implements GaeaCommonConditionService {
    @Autowired
    private GaeaCommonConditionMapper gaeaCommonConditionMapper;

    @Override
    public GaeaBaseMapper<GaeaCommonCondition> getMapper() {
        return gaeaCommonConditionMapper;
    }

    @Override
    public List<GaeaCommonConditionDTO> queryByCondition(ComConditionQueryParam queryParam) {
        return gaeaCommonConditionMapper.queryByCondition(queryParam);
    }
}