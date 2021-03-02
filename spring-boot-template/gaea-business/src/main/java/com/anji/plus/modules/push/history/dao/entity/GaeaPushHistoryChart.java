package com.anji.plus.modules.push.history.dao.entity;

import java.io.Serializable;

/**
 * 报表实体
 *
 * @author lr
 * @since 2021-02-08 09:36:10
 */
public class GaeaPushHistoryChart implements Serializable {

    /**
     * 模板类型，sms：短信；mail：邮件; dingtalk:
     */
    private String templateType;

    /**
     * 请求日期
     */
    private String sendDate;

    /**
     * 成功数
     */
    private Long success;

    /**
     * 失败数
     */
    private Long failure;

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public Long getSuccess() {
        return success;
    }

    public void setSuccess(Long success) {
        this.success = success;
    }

    public Long getFailure() {
        return failure;
    }

    public void setFailure(Long failure) {
        this.failure = failure;
    }
}
