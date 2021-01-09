package com.anji.mirror.push.service;

import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.push.domain.vo.PushChartStatisticsVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-10-16
 */
public interface PushChartStatisticsService {

    /**
     * 推送消息图表统计 （邮件、短信、钉钉）
     * startTime
     * endTime
     *
     * @param requestModel
     * @return
     */
    ResponseModel getPushStatistics(RequestModel<PushChartStatisticsVO> requestModel);
}
