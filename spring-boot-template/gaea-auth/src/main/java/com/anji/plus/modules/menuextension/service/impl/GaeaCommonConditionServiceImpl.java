package com.anji.plus.modules.menuextension.service.impl;

import com.alibaba.fastjson.JSON;
import com.anji.plus.common.MagicValueConstants;
import com.anji.plus.dto.DynamicQueryBo;
import com.anji.plus.modules.menuextension.controller.dto.GaeaCommonConditionDTO;
import com.anji.plus.modules.menuextension.controller.param.ComConditionQueryParam;
import com.anji.plus.modules.menuextension.controller.param.CommonConditionInputBO;
import com.anji.plus.modules.menuextension.controller.param.CommonConditionReqBO;
import com.anji.plus.modules.menuextension.dao.GaeaCommonConditionMapper;
import com.anji.plus.modules.menuextension.entity.GaeaCommonCondition;
import com.anji.plus.modules.menuextension.service.GaeaCommonConditionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
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
            if(null!=commonCondition&& StringUtils.isNotEmpty(commonCondition.getCommSql())){
                result=JSON.parseArray(commonCondition.getCommSql(),DynamicQueryBo.class);
            }
        }catch (Exception e){
           log.error("search error {}",e.getMessage());
        }
        return result;
    }
    @Override
    public boolean saveCommonCondition(CommonConditionInputBO t) {
        List<String> values = new ArrayList<>();
        List<DynamicQueryBo> dynamicQueryBos = new ArrayList<>();
        List<CommonConditionReqBO> commonConditionReqBOList = t.getCommonConditionReqBOList();
        for (int i = 0; i < commonConditionReqBOList.size(); i++) {
            CommonConditionReqBO commonConditionReqBO = commonConditionReqBOList.get(i);
            String value = commonConditionReqBO.getValue();
            // 查询条件下拉框展示标签
            if (MagicValueConstants.TWO == commonConditionReqBO.getType()) {
                value = commonConditionReqBO.getValueName();
            }
            values.add(value);
            getDynamicList(dynamicQueryBos, commonConditionReqBO);
        }
        GaeaCommonCondition commonCondition = t.inputBO2Entity();
        commonCondition.setCommSql(JSON.toJSONString(dynamicQueryBos));
        commonCondition.setLabel(JSON.toJSONString(values));
        gaeaCommonConditionMapper.insert(commonCondition);
        // TODO 常用查询sql,List塞入缓存
        return true;
    }

    private void getDynamicList(List<DynamicQueryBo> dynamicQueryBos, CommonConditionReqBO commonConditionReqBO) {
        DynamicQueryBo dynamicQueryBo = new DynamicQueryBo();
        dynamicQueryBo.setDatePrecision(commonConditionReqBO.getDatePrecision());
        dynamicQueryBo.setName(commonConditionReqBO.getName());
        dynamicQueryBo.setOperator(commonConditionReqBO.getOperator());
        dynamicQueryBo.setValue(commonConditionReqBO.getValue());
        dynamicQueryBo.setValueType(commonConditionReqBO.getValueType());
        dynamicQueryBos.add(dynamicQueryBo);
    }

}
