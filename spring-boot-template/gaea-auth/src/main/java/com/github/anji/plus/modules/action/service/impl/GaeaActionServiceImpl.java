package com.github.anji.plus.modules.action.service.impl;

import com.github.anji.plus.modules.action.dao.entity.GaeaAction;
import com.github.anji.plus.modules.action.dao.GaeaActionMapper;
import com.github.anji.plus.modules.action.service.GaeaActionService;
import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 按钮权限(GaeaAction)ServiceImpl
 *
 * @author lirui
 * @since 2021-02-02 13:37:04
 */
@Service
public class GaeaActionServiceImpl implements GaeaActionService {
    @Autowired
    private GaeaActionMapper  gaeaActionMapper;

    @Override
    public GaeaBaseMapper<GaeaAction> getMapper() {
        return  gaeaActionMapper;
    }

}
