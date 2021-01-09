package com.anji.mirror.gateway.feign.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AccessUserVO implements Serializable {

    /**主键*/
    private Long userId;

    /**用户名*/
    private String userName;

    /**密码*/
    private String password;

    /**用户类型，system--超级管理员，manager--项目管理员，viewer--项目查看员*/
    private String userType;

    /**用户姓名*/
    private String nikeName;

    /**手机号码*/
    private String phone;

    /**用户邮箱*/
    private String email;

    /**0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG*/
    private Integer enableFlag;

    /** 0--未删除 1--已删除 DIC_NAME=DEL_FLAG*/
    private Integer deleteFlag;

    private String remark;

    /**推荐人*/
    private String recommender;

    /**最后一次登陆时间*/
    private Date lastLoginTime;

    /**最后一次登录IP*/
    private String lastLoginIp;

    /**当前登录token*/
    private String currentToken;

    /**当前登录token过期时间，时间戳毫秒*/
    private Long currentTokenExpire;

    private String createdBy;           //创建人
    private Date createdTime;           //创建时间
    private String updatedBy;           //更新人
    private Date updatedTime;           //更新时间

    /************************** 以下为非原表字段 ****************************/
    /**该用户的权限码*/
    private Map<String,List<Long>> authorities;

    private List<Long> projectList;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(Integer enableFlag) {
        this.enableFlag = enableFlag;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRecommender() {
        return recommender;
    }

    public void setRecommender(String recommender) {
        this.recommender = recommender;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(String currentToken) {
        this.currentToken = currentToken;
    }

    public Long getCurrentTokenExpire() {
        return currentTokenExpire;
    }

    public void setCurrentTokenExpire(Long currentTokenExpire) {
        this.currentTokenExpire = currentTokenExpire;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Map<String, List<Long>> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Map<String, List<Long>> authorities) {
        this.authorities = authorities;
    }

    public List<Long> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Long> projectList) {
        this.projectList = projectList;
    }
}
