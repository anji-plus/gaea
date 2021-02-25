package com.anjiplus.template.business.modules.push.history.service;

import com.anji.plus.gaea.bean.ResponseBean;
import com.anji.plus.gaea.curd.service.GaeaBaseService;
import com.anjiplus.template.business.modules.push.history.controller.param.GaeaPushHistoryParam;
import com.anjiplus.template.business.modules.push.history.dao.entity.GaeaPushHistory;
import com.anjiplus.template.business.modules.push.history.service.impl.PushChartStatisticsVO;

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
