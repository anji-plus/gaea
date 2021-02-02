package com.github.anji.plus.menuextension.service.impl;

import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.github.anji.plus.menuextension.dao.GaeaMenuUserExtensionMapper;
import com.github.anji.plus.menuextension.entity.GaeaMenuUserExtension;
import com.github.anji.plus.menuextension.service.GaeaMenuUserExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (GaeaMenuUserExtension)ServiceImpl
 *
 * @author makejava
 * @since 2021-02-02 14:40:52
 */
@Service
public class GaeaMenuUserExtensionServiceImpl implements GaeaMenuUserExtensionService {
    @Autowired
    private GaeaMenuUserExtensionMapper gaeaMenuUserExtensionMapper;

    @Override
    public GaeaBaseMapper<GaeaMenuUserExtension> getMapper() {
        return gaeaMenuUserExtensionMapper;
    }

}