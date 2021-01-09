package com.anji.mirror.common.security;

import java.io.Serializable;
import java.util.List;

public class Authentication implements Serializable {

    private static final long serialVersionUID = 1L;

    // token有效
    private boolean tokenIsValid;

    //是否拥有指定的权限
    private boolean hasPermission;

    //当前操作者userId
    private Long userId;

    //当前操作者登录名
    private String loginName;

    //当前操作者对哪些组织有操作权限 orgId
    private List<Long> orgIdList;

    //当前操作者对哪些组织有操作权限 orgCode
    private List<String> orgCodeList;

    public boolean isTokenIsValid() {
        return tokenIsValid;
    }

    public void setTokenIsValid(boolean tokenIsValid) {
        this.tokenIsValid = tokenIsValid;
    }

    public boolean isHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(boolean hasPermission) {
        this.hasPermission = hasPermission;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public List<Long> getOrgIdList() {
        return orgIdList;
    }

    public void setOrgIdList(List<Long> orgIdList) {
        this.orgIdList = orgIdList;
    }

    public void setOrgCodeList(List<String> orgCodeList) {
        this.orgCodeList = orgCodeList;
    }

    public List<String> getOrgCodeList() {
        return orgCodeList;
    }


    public static Authentication fail(){
        Authentication authentication = new Authentication();
        authentication.setTokenIsValid(false);
        authentication.setHasPermission(false);
        return authentication;
    }


    public static Authentication pass(){
        Authentication authentication = new Authentication();
        authentication.setTokenIsValid(true);
        authentication.setHasPermission(true);
        return authentication;
    }
}
