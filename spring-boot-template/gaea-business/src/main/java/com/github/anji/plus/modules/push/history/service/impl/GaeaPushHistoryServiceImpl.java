package com.github.anji.plus.modules.push.history.service.impl;

import com.github.anji.plus.gaea.bean.ResponseBean;
import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.github.anji.plus.modules.push.history.dao.GaeaPushHistoryMapper;
import com.github.anji.plus.modules.push.history.dao.entity.GaeaPushHistory;
import com.github.anji.plus.modules.push.history.dao.entity.GaeaPushHistoryChart;
import com.github.anji.plus.modules.push.history.service.GaeaPushHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (GaeaPushHistory)ServiceImpl
 *
 * @author lr
 * @since 2021-02-08 09:36:10
 */
@Service
public class GaeaPushHistoryServiceImpl implements GaeaPushHistoryService {
    @Autowired
    private GaeaPushHistoryMapper gaeaPushHistoryMapper;

    @Override
    public GaeaBaseMapper<GaeaPushHistory> getMapper() {
        return gaeaPushHistoryMapper;
    }


    @Override
    public ResponseBean getPushStatistics(PushChartStatisticsVO data) {

        List<GaeaPushHistoryChart> chartData = gaeaPushHistoryMapper.queryChartData(data.getStartTime(), data.getEndTime());


        PushChartStatisticsVO mirrorChartVO = new PushChartStatisticsVO();

        //类
        mirrorChartVO.setLegendData(getLegendList());

        //横轴日期
        List<String> sendDateList = chartData.stream().map(chart -> chart.getSendDate()).distinct().collect(Collectors.toList());
        mirrorChartVO.setXAxisData(sendDateList);

        //纵轴数据
        mirrorChartVO.setSeriesData(getSeriesData(chartData,sendDateList));


        return ResponseBean.builder().data(mirrorChartVO).build();
    }


    /**
     * 处理 Series 数据
     *
     * @param charts
     * @param sendDateList
     * @return
     */
    private List getSeriesData(List<GaeaPushHistoryChart> charts, List<String> sendDateList) {


        Map<String, List<GaeaPushHistoryChart>> chartGroup = charts.stream()
                .collect(Collectors.groupingBy(GaeaPushHistoryChart::getTemplateType));

        //邮件
        List<GaeaPushHistoryChart> mail = chartGroup.get("mail");
        Map<String, GaeaPushHistoryChart> mailDateMap = mail.stream().collect(Collectors.toMap(GaeaPushHistoryChart::getSendDate, value -> value));

        //短信
        List<GaeaPushHistoryChart> sms = chartGroup.get("sms");
        Map<String, GaeaPushHistoryChart> smsDateMap = sms.stream().collect(Collectors.toMap(GaeaPushHistoryChart::getSendDate, value -> value));


        //钉钉
        List<GaeaPushHistoryChart> dingtalk = chartGroup.get("dingtalk");
        Map<String, GaeaPushHistoryChart> dingtalkDateMap = dingtalk.stream().collect(Collectors.toMap(GaeaPushHistoryChart::getSendDate, value -> value));

        List<Map<String, List<String>>> yAxis = new ArrayList<>();

        Map yAxisMailMap = new HashMap();
        Map yAxisSMSMap = new HashMap();
        Map yAxisDingTalkMap = new HashMap();

        List<Long> yAxisFailMail = new ArrayList<>();
        List<Long> yAxisSuccessMail = new ArrayList<>();
        ArrayList<Long> yAxisFailSMS = new ArrayList<>();
        ArrayList<Long> yAxisSuccessSMS = new ArrayList<>();
        ArrayList<Long> yAxisFailDingTalk = new ArrayList<>();
        ArrayList<Long> yAxisSuccessDingTalk = new ArrayList<>();

        sendDateList.stream().forEach(sendTime -> {
            buildAxis(mailDateMap, yAxisFailMail, yAxisSuccessMail, sendTime);

            buildAxis(smsDateMap, yAxisFailSMS, yAxisSuccessSMS, sendTime);

            buildAxis(dingtalkDateMap, yAxisFailDingTalk, yAxisSuccessDingTalk, sendTime);
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

    private void buildAxis(Map<String, GaeaPushHistoryChart> mailDateMap, List<Long> yAxisFailMail, List<Long> yAxisSuccessMail, String sendTime) {
        GaeaPushHistoryChart mailChart = mailDateMap.get(sendTime);
        if (mailChart == null) {
            yAxisFailMail.add(0L);
            yAxisSuccessMail.add(0L);
        } else {
            yAxisFailMail.add(mailChart.getFailure());
            yAxisSuccessMail.add(mailChart.getSuccess());
        }
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


//    private List<String> getDateList(String startTime, String endTime) {
//        GaeaDate
//    }

    /**
     * 获取数据日期时间段
     *
     * @param data
     * @return
     */
//    private List<String> getXAxisData(PushChartStatisticsVO data) {
//        List<String> xAxisData = null;
//        try {
//            if (StringUtils.isBlank(data.getStartTime()) && StringUtils.isBlank(data.getEndTime())) {
//                //开始时间和结束时间都为空
//                xAxisData = DateUtil.getPastDateByDay(7);
//            } else if (StringUtils.isBlank(data.getStartTime()) && !StringUtils.isBlank(data.getEndTime())) {
//                //开始时间为空和结束时间不为空 取结束时间往前7天
//                xAxisData = DateUtil.findDates(DateUtil.getPastDate(6, data.getEndTime()), data.getEndTime());
//            } else if (!StringUtils.isBlank(data.getStartTime()) && StringUtils.isBlank(data.getEndTime())) {
//                //开始时间不为空和结束时间为空 取开始时间到今天
//                //判断开始时间距离今天是否大于7
//                int begin = DateUtil.calcHowManyDays(data.getStartTime());
//                int end = DateUtil.calcHowManyDays(DateUtil.getPastDate(0));
//                if (end - begin > 7) {
//                    xAxisData = DateUtil.findDates(data.getStartTime(), DateUtil.getAfterDate(6, data.getStartTime()));
//                } else {
//                    xAxisData = DateUtil.findDates(data.getStartTime(), DateUtil.getPastDate(0));
//                }
//            } else {
//                //都不为空
//                xAxisData = DateUtil.findDates(data.getStartTime(), data.getEndTime());
//            }
//        } catch (Exception e) {
//            log.error("获取时间报错", e);
//        }
//        return xAxisData;
//    }

}
