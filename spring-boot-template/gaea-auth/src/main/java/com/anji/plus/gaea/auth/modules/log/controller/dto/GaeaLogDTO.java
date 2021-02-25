package com.anji.plus.gaea.auth.modules.log.controller.dto;

import com.anji.plus.gaea.curd.dto.GaeaBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * (GaeaLog)实体类
 *
 * @author peiyanni
 * @since 2021-02-18 16:30:24
 */
@ApiModel(value = "")
public class GaeaLogDTO extends GaeaBaseDTO {
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String userName;
    /**
     * 请求路径
     */
    @ApiModelProperty(value = "请求路径")
    private String requestUrl;
    /**
     * 页面或按钮标题
     */
    @ApiModelProperty(value = "页面或按钮标题")
    private String pageTitle;
    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private Object requestParam;
    /**
     * 响应参数
     */
    @ApiModelProperty(value = "响应参数")
    private Object responseParam;
    /**
     * 来源IP
     */
    @ApiModelProperty(value = "来源IP")
    private String sourceIp;
    /**
     * 访问时间
     */
    @ApiModelProperty(value = "访问时间")
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

    public void setRequestParam(Object requestParam) {
        this.requestParam = requestParam;
    }

    public Object getResponseParam() {
        return responseParam;
    }

    public void setResponseParam(Object responseParam) {
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
