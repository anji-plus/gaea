package com.anji.mirror.gateway.feign.model;

import java.io.Serializable;
import java.util.Date;

public class AccessLogVO implements Serializable {

    private Long logId;

    /**用户ID*/
    private Long userId;

    /**用户名称*/
    private String userName;

    /**请求路径*/
    private String requestUrl;

    /**请求参数*/
    private String requestParam;

    /**响应参数*/
    private String responseParam;

    /**来源IP*/
    private String sourceIp;

    /**访问时间*/
    private Date requestTime;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getResponseParam() {
        return responseParam;
    }

    public void setResponseParam(String responseParam) {
        this.responseParam = responseParam;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }
}
