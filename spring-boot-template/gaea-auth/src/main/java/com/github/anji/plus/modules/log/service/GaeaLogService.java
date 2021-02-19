package com.github.anji.plus.modules.log.service;

import com.anjiplus.gaea.log.aspect.LogOperation;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import com.github.anji.plus.modules.log.controller.param.GaeaLogParam;
import com.github.anji.plus.modules.log.entity.GaeaLog;

/**
 * (GaeaLog)Service
 *
 * @author peiyanni
 * @since 2021-02-18 16:30:22
 */
public interface GaeaLogService extends GaeaBaseService<GaeaLogParam, GaeaLog> {
    /**
     * 保存日志
     * @param logOperation
     * @return
     */
    void saveCallbackInfo(LogOperation logOperation);
}