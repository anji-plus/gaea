package com.github.anji.plus.menuextension.service.impl;

import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.github.anji.plus.menuextension.dao.GaeaQueryConditionMapper;
import com.github.anji.plus.menuextension.entity.GaeaQueryCondition;
import com.github.anji.plus.menuextension.service.GaeaQueryConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (GaeaQueryCondition)ServiceImpl
 *
 * @author makejava
 * @since 2021-02-02 14:42:14
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