package com.anji.plus.modules.org.service.impl;

import com.anji.plus.modules.org.service.GaeaOrgService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.anji.plus.common.MagicValueConstants;
import com.anji.plus.gaea.constant.Enabled;
import com.anji.plus.modules.org.controller.dto.GaeaOrgDTO;
import com.anji.plus.modules.org.dao.entity.GaeaOrg;
import com.anji.plus.modules.org.dao.GaeaOrgMapper;
import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组织(GaeaOrg)ServiceImpl
 *
 * @author lr
 * @since 2021-02-02 13:37:33
 */
@Service
public class GaeaOrgServiceImpl implements GaeaOrgService {
    @Autowired
    private GaeaOrgMapper  gaeaOrgMapper;

    @Override
    public GaeaBaseMapper<GaeaOrg> getMapper() {
        return  gaeaOrgMapper;
    }

    @Override
    public List<GaeaOrg> queryAllOrg() {
        LambdaQueryWrapper<GaeaOrg> wrapper= Wrappers.lambdaQuery();
        wrapper.select(GaeaOrg::getOrgCode,GaeaOrg::getOrgName)
                .eq(GaeaOrg::getEnabled, Enabled.YES.getValue())
                .and(e->e.eq(GaeaOrg::getDeleteFlag,Enabled.NO.getValue()));
        return gaeaOrgMapper.selectList(wrapper);
    }

    @Override
    public Boolean saveOrUpdateOrg(GaeaOrgDTO gaeaOrgDTO) {
        //设置父类机构名称
        if(StringUtils.isNotEmpty(gaeaOrgDTO.getOrgParentCode())){
            LambdaQueryWrapper<GaeaOrg> wrapper= Wrappers.lambdaQuery();
            wrapper.select(GaeaOrg::getOrgCode,GaeaOrg::getOrgName)
                    .eq(GaeaOrg::getOrgCode,gaeaOrgDTO.getOrgParentCode());
            List<GaeaOrg> parentOrgList= gaeaOrgMapper.selectList(wrapper);
            gaeaOrgDTO.setOrgParentName(parentOrgList.get(0).getOrgName());
        }else{
            gaeaOrgDTO.setOrgParentCode(MagicValueConstants.STRING_ZERO);
        }
        GaeaOrg gaeaOrg=new GaeaOrg();
        BeanUtils.copyProperties(gaeaOrgDTO,gaeaOrg);
        if(null!=gaeaOrgDTO.getId()){
            gaeaOrgMapper.updateById(gaeaOrg);
        }else{
            gaeaOrgMapper.insert(gaeaOrg);
        }
        return true;
    }


}
