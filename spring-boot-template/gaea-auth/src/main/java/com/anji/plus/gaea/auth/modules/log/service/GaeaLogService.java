package com.anji.plus.gaea.auth.modules.log.service;

import com.anji.plus.gaea.auth.modules.log.entity.GaeaLog;
import com.anjiplus.gaea.log.aspect.LogOperation;
import com.anji.plus.gaea.curd.service.GaeaBaseService;
import com.anji.plus.gaea.auth.modules.log.controller.param.GaeaLogParam;

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

    /**
     * 导出日志信息到文件，便于后续下载
     * @param gaeaLogParam
     * @return
     */
    Boolean exportLogToFile(GaeaLogParam gaeaLogParam);


}
