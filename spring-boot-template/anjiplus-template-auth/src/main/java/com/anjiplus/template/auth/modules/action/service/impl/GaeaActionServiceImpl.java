package com.anjiplus.template.auth.modules.action.service.impl;

import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.anjiplus.template.auth.modules.action.dao.GaeaActionMapper;
import com.anjiplus.template.auth.modules.action.service.GaeaActionService;
import com.anjiplus.template.auth.modules.action.dao.entity.GaeaAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 按钮权限(GaeaAction)ServiceImpl
 *
 * @author makejava
 * @since 2021-02-04 14:59:47
 */
@Service
public class GaeaActionServiceImpl implements GaeaActionService {
    @Autowired
    private GaeaActionMapper gaeaActionMapper;

    @Override
    public GaeaBaseMapper<GaeaAction> getMapper() {
        return gaeaActionMapper;
    }

}