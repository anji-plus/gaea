package com.github.anji.plus.modules.menuextension.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.anji.plus.dto.DynamicQueryBo;
import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.github.anji.plus.modules.menuextension.controller.dto.GaeaCommonConditionDTO;
import com.github.anji.plus.modules.menuextension.controller.param.ComConditionQueryParam;
import com.github.anji.plus.modules.menuextension.dao.GaeaCommonConditionMapper;
import com.github.anji.plus.modules.menuextension.entity.GaeaCommonCondition;
import com.github.anji.plus.modules.menuextension.service.GaeaCommonConditionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * (GaeaCommonCondition)ServiceImpl
 *
 * @author makejava
 * @since 2021-02-02 14:42:40
 */
@Service
@Slf4j
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

    @Override
    public List<DynamicQueryBo> getDynamicQueryBoListById(Long commonId) {
        List<DynamicQueryBo> result = new ArrayList<>(1);
        LambdaQueryWrapper<GaeaCommonCondition> queryWrapper= Wrappers.lambdaQuery();
        queryWrapper.eq(GaeaCommonCondition::getId,commonId);
        try {
            GaeaCommonCondition commonCondition=gaeaCommonConditionMapper.selectOne(queryWrapper);
            if(null!=commonCondition&& StringUtils.isNotEmpty(commonCondition.getSql())){
                result=JSON.parseArray(commonCondition.getSql(),DynamicQueryBo.class);
            }
        }catch (Exception e){
           log.error("search error {}",e.getMessage());
        }
        return result;
    }
}