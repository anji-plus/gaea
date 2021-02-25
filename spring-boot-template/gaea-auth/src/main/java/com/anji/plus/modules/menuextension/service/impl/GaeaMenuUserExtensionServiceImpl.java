package com.anji.plus.modules.menuextension.service.impl;

import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.anji.plus.modules.menuextension.dao.GaeaMenuUserExtensionMapper;
import com.anji.plus.modules.menuextension.entity.GaeaMenuUserExtension;
import com.anji.plus.modules.menuextension.service.GaeaMenuUserExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (GaeaMenuUserExtension)ServiceImpl
 *
 * @author makejava
 * @since 2021-02-04 17:15:36
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
