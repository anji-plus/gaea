package com.github.anji.plus.modules.push.history.service;

import com.github.anji.plus.gaea.bean.ResponseBean;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import com.github.anji.plus.modules.push.history.controller.param.GaeaPushHistoryParam;
import com.github.anji.plus.modules.push.history.dao.entity.GaeaPushHistory;
import com.github.anji.plus.modules.push.history.service.impl.PushChartStatisticsVO;

/**
 * (GaeaPushHistory)Service
 *
 * @author lr
 * @since 2021-02-08 09:36:10
 */
public interface GaeaPushHistoryService extends GaeaBaseService<GaeaPushHistoryParam, GaeaPushHistory> {

    /**
     * 获取报表
     * @param requestModel
     * @return
     */
    ResponseBean getPushStatistics(PushChartStatisticsVO requestModel);

}
