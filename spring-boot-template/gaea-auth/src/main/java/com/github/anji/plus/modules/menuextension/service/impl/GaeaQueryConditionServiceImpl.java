package com.github.anji.plus.modules.menuextension.service.impl;

import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.github.anji.plus.modules.menuextension.dao.GaeaQueryConditionMapper;
import com.github.anji.plus.modules.menuextension.entity.GaeaQueryCondition;
import com.github.anji.plus.modules.menuextension.service.GaeaQueryConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (GaeaQueryCondition)ServiceImpl
 *
 * @author makejava
 * @since 2021-02-04 17:16:01
 */
@Service
public class GaeaQueryConditionServiceImpl implements GaeaQueryConditionService {
    @Autowired
    private GaeaQueryConditionMapper gaeaQueryConditionMapper;

    @Override
    public GaeaBaseMapper<GaeaQueryCondition> getMapper() {
        return gaeaQueryConditionMapper;
    }

}