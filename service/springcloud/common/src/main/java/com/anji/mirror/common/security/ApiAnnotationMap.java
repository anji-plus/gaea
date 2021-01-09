package com.anji.mirror.common.security;

import com.alibaba.fastjson.JSONObject;
import com.anji.mirror.common.annotation.Log;

import java.util.HashMap;
import java.util.Map;

public class ApiAnnotationMap {

    /**
     * 每个url对应的权限码
     * key = /analysis-service/event/queryById
     * value = eventManage:edit|bigScreenManage:edit
     */
    private Map<String, String> permissionMap = new HashMap<String, String>();

    /**
     * 每个url对应的操作日志
     * key = /analysis-service/event/queryById
     * value = {pageTitle:"告警事件详细页", isSaveRequestData:true}
     */
    private Map<String, String> operationLogMap = new HashMap<String, String>();

    /**
     * 每个url对应的限流
     * key = /analysis-service/event/queryById
     * value = {unitSecond:60, maximum:200, frozenSecond:1}
     */
    private Map<String, String> rateLimitMap = new HashMap<String, String>();

    public Map<String, String> getPermissionMap() {
        return permissionMap;
    }

    public void setPermissionMap(Map<String, String> permissionMap) {
        this.permissionMap = permissionMap;
    }

    public void addToPermissionMap(String apiPath, String permission) {
        this.permissionMap.put(apiPath, permission);
    }

    public Map<String, String> getOperationLogMap() {
        return operationLogMap;
    }

    public void setOperationLogMap(Map<String, String> operationLogMap) {
        this.operationLogMap = operationLogMap;
    }

    public void addToOperationLogMap(String apiPath, Log log) {
        if(log != null){
            this.operationLogMap.put(apiPath, JSONObject.toJSONString(log));
        }
    }

    public Map<String, String> getRateLimitMap() {
        return rateLimitMap;
    }

    public void setRateLimitMap(Map<String, String> rateLimitMap) {
        this.rateLimitMap = rateLimitMap;
    }

    public void addToRateLimiterMap(String apiPath, RateLimit rateLimit) {
        if(rateLimit != null){
            this.rateLimitMap.put(apiPath, JSONObject.toJSONString(rateLimit));
        }
    }
}
