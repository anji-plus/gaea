package com.github.anji.plus.modules.menuextension.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.anji.plus.gaea.constant.Enabled;
import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.github.anji.plus.modules.menuextension.dao.GaeaMenuExtensionMapper;
import com.github.anji.plus.modules.menuextension.entity.GaeaMenuExtension;
import com.github.anji.plus.modules.menuextension.service.GaeaMenuExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (GaeaMenuExtension)ServiceImpl
 *
 * @author makejava
 * @since 2021-02-04 17:14:15
 */
@Service
public class GaeaMenuExtensionServiceImpl implements GaeaMenuExtensionService {
    @Autowired
    private GaeaMenuExtensionMapper gaeaMenuExtensionMapper;

    @Override
    public GaeaBaseMapper<GaeaMenuExtension> getMapper() {
        return gaeaMenuExtensionMapper;
    }

    @Override
    public List<GaeaMenuExtension> queryMenuExtensionList(String menuCode) {
        LambdaQueryWrapper<GaeaMenuExtension> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(GaeaMenuExtension::getMenuCode, menuCode)
               .and(e->e.eq(GaeaMenuExtension::getIsDisabled,Enabled.NO.getValue()));
        return gaeaMenuExtensionMapper.selectList(queryWrapper);
    }
}