package com.anji.mirror.push.service.impl;

import com.anji.mirror.common.enums.RepCodeEnum;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.utils.DateUtil;
import com.anji.mirror.push.mapper.PushHistoryMapper;
import com.anji.mirror.push.domain.vo.PushChartStatisticsVO;
import com.anji.mirror.push.service.PushChartStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author anji gaea teams
 * @Date: 2020/10/20
 * @Description:
 */
@Slf4j
@Service
public class PushChartStatisticsServiceImpl implements PushChartStatisticsService {
    @Autowired
    private PushHistoryMapper pushHistoryMapper;


    @Override
    public ResponseModel getPushStatistics(RequestModel<PushChartStatisticsVO> requestModel) {
        PushChartStatisticsVO data = requestModel.getData();
        if (null == data) {
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        PushChartStatisticsVO mirrorChartVO = new PushChartStatisticsVO();
        mirrorChartVO.setLegendData(getLegendList());
        mirrorChartVO.setXAxisData(getXAxisData(data));
        mirrorChartVO.setSeriesData(getSeriesData(mirrorChartVO));
        return ResponseModel.success(mirrorChartVO);
    }


    /**
     * 处理 Series 数据
     *
     * @param mirrorChartVO
     * @return
     */
    private List getSeriesData(PushChartStatisticsVO mirrorChartVO) {
        List<Map<String, List<String>>> yAxis = new ArrayList<>();

        Map yAxisMailMap = new HashMap();
        Map yAxisSMSMap = new HashMap();
        Map yAxisDingTalkMap = new HashMap();

        List<String> yAxisFailMail = new ArrayList<>();
        List<String> yAxisSuccessMail = new ArrayList<>();
        ArrayList<String> yAxisFailSMS = new ArrayList<>();
        ArrayList<String> yAxisSuccessSMS = new ArrayList<>();
        ArrayList<String> yAxisFailDingTalk = new ArrayList<>();
        ArrayList<String> yAxisSuccessDingTalk = new ArrayList<>();

        mirrorChartVO.getXAxisData().stream().forEach(sendTime -> {
            int mailFailCount = pushHistoryMapper.queryCountByDay("mail", sendTime, 0);
            int mailSuccessCount = pushHistoryMapper.queryCountByDay("mail", sendTime, 1);
            String c = String.valueOf(mailSuccessCount);
            log.info("String.valueOf(mailFailCount) : {}", c);
            yAxisFailMail.add(String.valueOf(mailFailCount));
            yAxisSuccessMail.add(String.valueOf(mailSuccessCount));


            int smsFailCount = pushHistoryMapper.queryCountByDay("sms", sendTime, 0);
            int smsSuccessCount = pushHistoryMapper.queryCountByDay("sms", sendTime, 1);
            yAxisFailSMS.add(String.valueOf(smsFailCount));
            yAxisSuccessSMS.add(String.valueOf(smsSuccessCount));


            int dingTalkFailCount = pushHistoryMapper.queryCountByDay("dingtalk", sendTime, 0);
            int dingTalkSuccessCount = pushHistoryMapper.queryCountByDay("dingtalk", sendTime, 1);
            yAxisFailDingTalk.add(String.valueOf(dingTalkFailCount));
            yAxisSuccessDingTalk.add(String.valueOf(dingTalkSuccessCount));

        });
        yAxisMailMap.put("fail", yAxisFailMail);
        yAxisMailMap.put("success", yAxisSuccessMail);
        yAxisSMSMap.put("fail", yAxisFailSMS);
        yAxisSMSMap.put("success", yAxisSuccessSMS);
        yAxisDingTalkMap.put("fail", yAxisFailDingTalk);
        yAxisDingTalkMap.put("success", yAxisSuccessDingTalk);
        yAxis.add(yAxisMailMap);
        yAxis.add(yAxisSMSMap);
        yAxis.add(yAxisDingTalkMap);
        return yAxis;
    }

    /**
     * 获取数据头部列表
     *
     * @return
     */
    private List<String> getLegendList() {
        List<String> legendList = new ArrayList<>();
        legendList.add("邮件");
        legendList.add("短信");
        legendList.add("钉钉");
        return legendList;
    }

    /**
     * 获取数据日期时间段
     *
     * @param data
     * @return
     */
    private List<String> getXAxisData(PushChartStatisticsVO data) {
        List<String> xAxisData = null;
        try {
            if (StringUtils.isBlank(data.getStartTime()) && StringUtils.isBlank(data.getEndTime())) {
                //开始时间和结束时间都为空
                xAxisData = DateUtil.getPastDateByDay(7);
            } else if (StringUtils.isBlank(data.getStartTime()) && !StringUtils.isBlank(data.getEndTime())) {
                //开始时间为空和结束时间不为空 取结束时间往前7天
                xAxisData = DateUtil.findDates(DateUtil.getPastDate(6, data.getEndTime()), data.getEndTime());
            } else if (!StringUtils.isBlank(data.getStartTime()) && StringUtils.isBlank(data.getEndTime())) {
                //开始时间不为空和结束时间为空 取开始时间到今天
                //判断开始时间距离今天是否大于7
                int begin = DateUtil.calcHowManyDays(data.getStartTime());
                int end = DateUtil.calcHowManyDays(DateUtil.getPastDate(0));
                if (end - begin > 7) {
                    xAxisData = DateUtil.findDates(data.getStartTime(), DateUtil.getAfterDate(6, data.getStartTime()));
                } else {
                    xAxisData = DateUtil.findDates(data.getStartTime(), DateUtil.getPastDate(0));
                }
            } else {
                //都不为空
                xAxisData = DateUtil.findDates(data.getStartTime(), data.getEndTime());
            }
        } catch (Exception e) {
            log.error("获取时间报错", e);
        }
        return xAxisData;
    }
}
