package com.github.anji.plus.menuextension.service.impl;

import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.github.anji.plus.menuextension.dao.GaeaMenuExtensionMapper;
import com.github.anji.plus.menuextension.entity.GaeaMenuExtension;
import com.github.anji.plus.menuextension.service.GaeaMenuExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (GaeaMenuExtension)ServiceImpl
 *
 * @author makejava
 * @since 2021-02-02 14:39:06
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