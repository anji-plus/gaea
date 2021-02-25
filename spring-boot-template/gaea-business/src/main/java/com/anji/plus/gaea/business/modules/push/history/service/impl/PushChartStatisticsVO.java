package com.anji.plus.gaea.business.modules.push.history.service.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PushChartStatisticsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 示例列表 ["邮件","短信","钉钉"]
     */
    private List<String> legendData;

    /**
     * x轴数据
     */
    private List<String> xAxisData;

    /**
     * y轴数据
     */
    private List<String> yAxisData;

    /**
     * 示例序列数据
     */
    private List<Map<String, List<Long>>> seriesData;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 发送时间
     */
    private String sendTime;

    /**
     * 发送状态,1,成功;0,失败
     */
    private Integer sendStatus;
}
