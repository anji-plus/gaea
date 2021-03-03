package com.anji.plus.modules.menuextension.service.impl;

import com.alibaba.fastjson.JSON;
import com.anji.plus.modules.menuextension.controller.dto.GaeaQueryConditionDTO;
import com.anji.plus.modules.menuextension.entity.GaeaQueryCondition;
import com.anji.plus.modules.menuextension.service.GaeaQueryConditionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.anji.plus.common.CacheConstants;
import com.anji.plus.common.CacheTimeConstants;
import com.anji.plus.gaea.constant.Enabled;
import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.anji.plus.modules.menuextension.dao.GaeaQueryConditionMapper;
import com.anji.plus.service.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * (GaeaQueryCondition)ServiceImpl
 * 高级查询相关
 *
 * @author peiyanni
 * @since 2021-02-04 17:16:01
 */
@Service
public class GaeaQueryConditionServiceImpl implements GaeaQueryConditionService {
    @Autowired
    private GaeaQueryConditionMapper gaeaQueryConditionMapper;
    @Autowired
    private CacheService cacheService;

    @Override
    public GaeaBaseMapper<GaeaQueryCondition> getMapper() {
        return gaeaQueryConditionMapper;
    }

    @Override
    public List<GaeaQueryCondition> queryCondition(GaeaQueryConditionDTO requestParam) {
        String menuCode = requestParam.getMenuCode();
        String tableCode = requestParam.getTableCode();
        LambdaQueryWrapper<GaeaQueryCondition> queryWrapper= Wrappers.lambdaQuery();
        queryWrapper.eq(GaeaQueryCondition::getMenuCode,menuCode)
                .and(StringUtils.isNotEmpty(tableCode),e->e.eq(GaeaQueryCondition::getTableCode,tableCode))
                .and(e->e.eq(GaeaQueryCondition::getIsDisabled, Enabled.NO.getValue()));
        return gaeaQueryConditionMapper.selectList(queryWrapper);
    }

}
