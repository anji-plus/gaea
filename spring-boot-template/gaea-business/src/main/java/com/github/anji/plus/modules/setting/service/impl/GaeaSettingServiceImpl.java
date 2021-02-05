package com.github.anji.plus.modules.setting.service.impl;

import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.github.anji.plus.modules.setting.dao.GaeaSettingMapper;
import com.github.anji.plus.modules.setting.entity.GaeaSetting;
import com.github.anji.plus.modules.setting.service.GaeaSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (GaeaSetting)ServiceImpl
 *
 * @author makejava
 * @since 2021-02-05 16:58:55
 */
@Service
public class GaeaSettingServiceImpl implements GaeaSettingService {
    @Autowired
    private GaeaSettingMapper gaeaSettingMapper;

    @Override
    public GaeaBaseMapper<GaeaSetting> getMapper() {
        return gaeaSettingMapper;
    }

}