package com.github.anji.plus.modules.menuextension.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.anji.plus.common.CacheConstants;
import com.github.anji.plus.common.CacheTimeConstants;
import com.github.anji.plus.gaea.constant.Enabled;
import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.github.anji.plus.modules.menuextension.controller.dto.GaeaQueryConditionDTO;
import com.github.anji.plus.modules.menuextension.dao.GaeaQueryConditionMapper;
import com.github.anji.plus.modules.menuextension.entity.GaeaQueryCondition;
import com.github.anji.plus.modules.menuextension.service.GaeaQueryConditionService;
import com.github.anji.plus.service.cache.CacheService;
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
        StringBuffer cacheKeyStr = new StringBuffer(CacheConstants.QUERY_CONDITION);
        cacheKeyStr.append(menuCode).append(":").append(tableCode);
        Object obj = cacheService.get(cacheKeyStr.toString());
        if (null == obj) {
            LambdaQueryWrapper<GaeaQueryCondition> queryWrapper= Wrappers.lambdaQuery();
            queryWrapper.eq(GaeaQueryCondition::getMenuCode,menuCode)
                    .and(e->e.eq(GaeaQueryCondition::getTableCode,tableCode))
                    .and(e->e.eq(GaeaQueryCondition::getIsDisabled, Enabled.NO.getValue()));
            List<GaeaQueryCondition> list=gaeaQueryConditionMapper.selectList(queryWrapper);
            if(CollectionUtils.isNotEmpty(list)){
                cacheService.add(cacheKeyStr.toString(), JSON.toJSONString(list), CacheTimeConstants.CHCHE_VALID_TIME_ONE_DAYS, TimeUnit.DAYS);
                return list;
            }else{
                //如果没有缓存一个空字符串，时间为3分钟
                cacheService.add(cacheKeyStr.toString(),"",CacheTimeConstants.CHCHE_VALID_TIME_THREE_MINUTE,TimeUnit.MINUTES);
            }
        }else{
            if(StringUtils.isNotEmpty(obj.toString())){
                return JSON.parseArray(obj.toString(), GaeaQueryCondition.class);
            }
        }
        return null;
    }

}