package com.github.anji.plus.menuextension.service.impl;

import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.github.anji.plus.menuextension.dao.GaeaCommonConditionMapper;
import com.github.anji.plus.menuextension.entity.GaeaCommonCondition;
import com.github.anji.plus.menuextension.service.GaeaCommonConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}