package com.github.anji.plus.module.demo.service.impl;

import com.github.anji.plus.module.demo.entity.TAction;
import com.github.anji.plus.module.demo.dao.TActionMapper;
import com.github.anji.plus.module.demo.service.TActionService;
import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 运营权限表(TAction)ServiceImpl
 *
 * @author lirui
 * @since 2021-01-29 18:45:49
 */
@Service
public class TActionServiceImpl implements TActionService {
    @Autowired
    private TActionMapper  tActionMapper;

    @Override
    public GaeaBaseMapper<TAction> getMapper() {
        return  tActionMapper;
    }

}