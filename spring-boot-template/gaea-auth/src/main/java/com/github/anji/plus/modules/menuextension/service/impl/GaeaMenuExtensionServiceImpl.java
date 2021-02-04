package com.github.anji.plus.modules.menuextension.service.impl;

import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.github.anji.plus.modules.menuextension.dao.GaeaMenuExtensionMapper;
import com.github.anji.plus.modules.menuextension.entity.GaeaMenuExtension;
import com.github.anji.plus.modules.menuextension.service.GaeaMenuExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}