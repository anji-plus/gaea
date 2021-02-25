package com.anji.plus.gaea.auth.modules.setting.service.impl;

import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.anji.plus.gaea.auth.modules.setting.dao.GaeaSettingMapper;
import com.anji.plus.gaea.auth.modules.setting.entity.GaeaSetting;
import com.anji.plus.gaea.auth.modules.setting.service.GaeaSettingService;
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
