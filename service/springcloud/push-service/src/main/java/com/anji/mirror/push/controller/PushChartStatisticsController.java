package com.anji.mirror.push.controller;

import com.anji.mirror.common.annotation.Log;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.security.HasPermission;
import com.anji.mirror.push.service.PushChartStatisticsService;
import com.anjiplus.gaea.push.domain.vo.PushChartStatisticsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anji gaea teams
 * @Date: 2020/10/20
 * @Description: 推送（邮件、短信、订单）图表统计
 */
@RestController
@RequestMapping("/statistics")
public class PushChartStatisticsController {
    @Autowired
    private PushChartStatisticsService pushChartStatisticsService;

    @PostMapping("/getPushStatistics")
    @HasPermission("statisticsManage:find")
    @Log(pageTitle = "推送消息图表统计 （邮件、短信、钉钉）")
    public ResponseModel getPushStatistics(@RequestBody RequestModel<PushChartStatisticsVO> requestModel) {//完成
        return pushChartStatisticsService.getPushStatistics(requestModel);
    }
}
