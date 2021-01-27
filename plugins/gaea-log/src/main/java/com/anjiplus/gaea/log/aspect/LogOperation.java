package com.anjiplus.gaea.log.aspect;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日志相关数据
 * @author lirui
 */
public class LogOperation implements Serializable {

    /**
     * 请求路径
     */
    private String requestUrl;

    /**
     * 页面或按钮标题
     */
    private String pageTitle;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 响应参数
     */
    private String responseParam;

    /**
     * 来源IP
     */
    private String sourceIp;

    /**
     * 请求方式get/post/put/delete
     */
    private String requestMethod;

    /**
     * 访问时间
     */
    private LocalDateTime requestTime;

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
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

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }
}
