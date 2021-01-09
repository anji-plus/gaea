package com.anji.mirror.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "customer.request")
public class RequestConfig {

    //网关透明传输的url，不做任何处理
    private List<String> transparentUrls;

    //不需要验证sign的接口
    private List<String> skipValidateSign;

    //不需要验证time的接口
    private List<String> skipValidateTimestamp;

    //不需要验证token的接口
    private List<String> skipValidateToken;

    //不需要验证auth的接口
    private List<String> skipValidateAuth;

    //不需要验证log的接口
    private List<String> skipValidateLog;

    //服务器拒绝访问ip名单
    private String rejectIPList;

    //服务器允许的客户端时间误差，秒
    private Integer timestampGap;

    public List<String> getTransparentUrls() {
        return transparentUrls;
    }

    public void setTransparentUrls(List<String> transparentUrls) {
        this.transparentUrls = transparentUrls;
    }

    public List<String> mergeListWithTransparentUrls(List<String> list){
        List<String> result = new ArrayList<String>();
        if(this.transparentUrls != null){
            for(int i=0; i< this.transparentUrls.size(); i++){
                String item = this.transparentUrls.get(i);
                if(result.contains(item) == false){
                    result.add(item);
                }
            }
        }
        if(list != null){
            for(int i=0; i< list.size(); i++){
                String item = list.get(i);
                if(result.contains(item) == false){
                    result.add(item);
                }
            }
        }
        return result;
    }

    public List<String> getSkipValidateSign() {
        return mergeListWithTransparentUrls(skipValidateSign);
    }

    public void setSkipValidateSign(List<String> skipValidateSign) {
        this.skipValidateSign = skipValidateSign;
    }

    public List<String> getSkipValidateTimestamp() {
        return mergeListWithTransparentUrls(skipValidateTimestamp);
    }

    public void setSkipValidateTimestamp(List<String> skipValidateTimestamp) {
        this.skipValidateTimestamp = skipValidateTimestamp;
    }

    public List<String> getSkipValidateToken() {
        return mergeListWithTransparentUrls(skipValidateToken);
    }

    public void setSkipValidateToken(List<String> skipValidateToken) {
        this.skipValidateToken = skipValidateToken;
    }

    public List<String> getSkipValidateAuth() {
        return mergeListWithTransparentUrls(skipValidateAuth);
    }

    public void setSkipValidateAuth(List<String> skipValidateAuth) {
        this.skipValidateAuth = skipValidateAuth;
    }

    public List<String> getSkipValidateLog() {
        return mergeListWithTransparentUrls(skipValidateLog);
    }

    public void setSkipValidateLog(List<String> skipValidateLog) {
        this.skipValidateLog = skipValidateLog;
    }

    public String getRejectIPList() {
        return rejectIPList;
    }

    public void setRejectIPList(String rejectIPList) {
        this.rejectIPList = rejectIPList;
    }

    public Integer getTimestampGap() {
        return timestampGap;
    }

    public void setTimestampGap(Integer timestampGap) {
        this.timestampGap = timestampGap;
    }
}
