package com.anji.mirror.common.model;

import java.util.List;

public class RequestModel<T> {
    private Integer currentPage=1;      //当前是每几页
    private Integer pageSize=10;        //每页显示多少行
    private String orderBy;             //排序字段及升序降序

    private String opToken;             //当前操作者token
    private Long opUserId;              //当前操作者userId
    private String opUserName;          //当前操作者用户名
    private String opIsFrom;            //当前操作者客户端类型 pc/app
    private String opSourceIP;          //当前操作者来源ip
    private List<Long> opOrgIdList;       //当前操作者所属的orgId列表
    private List<String> opOrgCodeList;   //当前操作者所属的orgCode列表

    private T data;                     //真实请求体

    /************************** getter setter ****************************/
    public Integer getCurrentPage() {
        if(this.currentPage==null || this.currentPage<=0){
            return 1;
        }else{
            return currentPage;
        }
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;

    }

    public Integer getPageSize() {
        if(this.pageSize==null || this.pageSize<=0){
            return 10;
        }else{
            return pageSize;
        }
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

    public String getOpToken() {
        return opToken;
    }

    public void setOpToken(String opToken) {
        this.opToken = opToken;
    }

    public Long getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(Long opUserId) {
        this.opUserId = opUserId;
    }

    public String getOpUserName() {
        return opUserName;
    }

    public void setOpUserName(String opUserName) {
        this.opUserName = opUserName;
    }

    public String getOpIsFrom() {
        return opIsFrom;
    }

    public void setOpIsFrom(String opIsFrom) {
        this.opIsFrom = opIsFrom;
    }

    public String getOpSourceIP() {
        return opSourceIP;
    }

    public void setOpSourceIP(String opSourceIP) {
        this.opSourceIP = opSourceIP;
    }

    public List<Long> getOpOrgIdList() {
        return opOrgIdList;
    }

    public void setOpOrgIdList(List<Long> opOrgIdList) {
        this.opOrgIdList = opOrgIdList;
    }

    public List<String> getOpOrgCodeList() {
        return opOrgCodeList;
    }

    public void setOpOrgCodeList(List<String> opOrgCodeList) {
        this.opOrgCodeList = opOrgCodeList;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
