package com.github.anji.plus.modules.org.service.impl;

import com.github.anji.plus.modules.org.dao.entity.GaeaOrg;
import com.github.anji.plus.modules.org.dao.GaeaOrgMapper;
import com.github.anji.plus.modules.org.service.GaeaOrgService;
import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 组织(GaeaOrg)ServiceImpl
 *
 * @author lirui
 * @since 2021-02-02 13:37:33
 */
@Service
public class GaeaOrgServiceImpl implements GaeaOrgService {
    @Autowired
    private GaeaOrgMapper  gaeaOrgMapper;

    @Override
    public GaeaBaseMapper<GaeaOrg> getMapper() {
        return  gaeaOrgMapper;
    }

}
