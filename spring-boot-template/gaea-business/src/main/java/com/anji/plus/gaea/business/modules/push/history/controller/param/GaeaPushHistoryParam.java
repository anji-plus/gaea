package com.anji.plus.gaea.business.modules.push.history.controller.param;


import com.anji.plus.gaea.curd.dto.Query;
import com.anji.plus.gaea.curd.dto.QueryEnum;
import com.anji.plus.gaea.curd.params.PageParam;

import java.io.Serializable;

/**
 * (GaeaPushHistory)param
 *
 * @author lr
 * @since 2021-02-08 09:36:10
 */
public class GaeaPushHistoryParam extends PageParam implements Serializable {

    /**
     * 推送类型
     */
    private String templateType;

    /**
     * 推送标题
     */
    @Query(QueryEnum.LIKE)
    private String pushTitle;

    /**
     * 发送时间,最小值与最大值用逗号隔开
     */
    @Query(value = QueryEnum.BWT)
    private String sendTime;

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getPushTitle() {
        return pushTitle;
    }

    public void setPushTitle(String pushTitle) {
        this.pushTitle = pushTitle;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
