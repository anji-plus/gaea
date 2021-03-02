package com.anji.plus.modules.helper.service.impl;

import com.anji.plus.modules.helper.dao.entity.GaeaHelp;
import com.anji.plus.modules.helper.service.GaeaHelpService;
import com.anji.plus.modules.helper.dao.GaeaHelpMapper;
import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 帮助中心表(GaeaHelp)ServiceImpl
 *
 * @author lr
 * @since 2021-02-22 10:36:38
 */
@Service
public class GaeaHelpServiceImpl implements GaeaHelpService {
    @Autowired
    private GaeaHelpMapper  gaeaHelpMapper;

    @Override
    public GaeaBaseMapper<GaeaHelp> getMapper() {
        return  gaeaHelpMapper;
    }

}
