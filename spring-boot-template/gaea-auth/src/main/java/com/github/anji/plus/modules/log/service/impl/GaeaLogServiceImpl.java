package com.github.anji.plus.modules.log.service.impl;

import com.anjiplus.gaea.log.aspect.LogOperation;
import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.github.anji.plus.gaea.holder.UserContentHolder;
import com.github.anji.plus.modules.log.dao.GaeaLogMapper;
import com.github.anji.plus.modules.log.entity.GaeaLog;
import com.github.anji.plus.modules.log.service.GaeaLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (GaeaLog)ServiceImpl
 *
 * @author peiyanni
 * @since 2021-02-18 16:30:23
 */
@Service
public class GaeaLogServiceImpl implements GaeaLogService {
    @Autowired
    private GaeaLogMapper gaeaLogMapper;

    @Override
    public GaeaBaseMapper<GaeaLog> getMapper() {
        return gaeaLogMapper;
    }

    @Override
    public void saveCallbackInfo(LogOperation logOperation) {
        GaeaLog gaeaLog=new GaeaLog();
        BeanUtils.copyProperties(logOperation,gaeaLog);
        gaeaLog.setUserName(UserContentHolder.getContext().getUsername());
        gaeaLogMapper.insert(gaeaLog);
    }
}