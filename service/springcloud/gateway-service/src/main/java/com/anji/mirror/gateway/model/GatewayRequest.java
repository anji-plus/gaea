package com.anji.mirror.gateway.model;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class GatewayRequest {
    /************************** 前端发送请求参数 ****************************/
    private Integer currentPage=1;      //当前是每几页
    private Integer pageSize=10;        //每页显示多少行
    private String orderBy;             //排序字段及升序降序

    /** 参与签名，按key升序，排除null值，用&拼接 */
    private JSONObject data;            //真实请求体
    /** 参与签名，在签名串开始 */
    private String time;                //前端请求时间的timestamp
    /** 参与签名，在签名串末尾 */
    private String token;               //当前操作者token
    private String sign;                //前端的签名

    private String isFrom;              //当前操作者客户端类型 pc/app

    private List<String> ignoreKeyList;     //data中哪些key不参与签名

    /************************** filter缓存参数 ****************************/

    private Long userId;                //当前操作者userId
    private String userName;            //当前操作者用户名
    private List<Long> orgIdList;       //当前操作者所属的组织列表
    private List<String> orgCodeList;       //当前操作者所属的组织列表
    private String servletPath;         // /auth-service/user/login
    private String sourceIP;            //当前操作者来源ip

    /************************** getter setter ****************************/
    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIsFrom() {
        return isFrom;
    }

    public void setIsFrom(String isFrom) {
        this.isFrom = isFrom;
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

    public String getServletPath() {
        return servletPath;
    }

    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    public List<Long> getOrgIdList() {
        return orgIdList;
    }

    public void setOrgIdList(List<Long> orgIdList) {
        this.orgIdList = orgIdList;
    }

    public List<String> getOrgCodeList() {
        return orgCodeList;
    }

    public void setOrgCodeList(List<String> orgCodeList) {
        this.orgCodeList = orgCodeList;
    }

    public String getSourceIP() {
        return sourceIP;
    }

    public void setSourceIP(String sourceIP) {
        this.sourceIP = sourceIP;
    }

    public List<String> getIgnoreKeyList() {
        return ignoreKeyList;
    }

    public void setIgnoreKeyList(List<String> ignoreKeyList) {
        this.ignoreKeyList = ignoreKeyList;
    }
}
