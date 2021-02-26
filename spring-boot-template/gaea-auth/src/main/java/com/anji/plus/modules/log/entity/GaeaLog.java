package com.anji.plus.modules.log.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.anji.plus.gaea.curd.entity.GaeaBaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * (GaeaLog)实体类
 *
 * @author peiyanni
 * @since 2021-02-18 16:30:18
 */
@TableName("gaea_log")
public class GaeaLog extends GaeaBaseEntity implements Serializable {
    /**
     * 用户名称
     */
    private String userName;
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
     * 访问时间
     */
    private Date requestTime;

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

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public Object getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public Object getResponseParam() {
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
