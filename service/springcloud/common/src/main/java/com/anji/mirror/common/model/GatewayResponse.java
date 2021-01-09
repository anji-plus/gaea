package com.anji.mirror.common.model;

import com.alibaba.fastjson.JSONObject;
import com.anji.mirror.common.enums.RepCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class GatewayResponse implements Serializable {

    private static final long serialVersionUID = 8445617032523881407L;

    private String            repCode;

    private String            repMsg;

    private Object            repData;

    public GatewayResponse() {
        this.repCode = RepCodeEnum.SUCCESS.getCode();
    }

    public GatewayResponse(RepCodeEnum repCodeEnum) {
        this.setRepCodeEnum(repCodeEnum);
    }

    //成功
    public static GatewayResponse success(){
        return GatewayResponse.success("成功");
    }
    public static GatewayResponse success(String message){
        GatewayResponse gatewayResponse = new GatewayResponse();
        gatewayResponse.setRepMsg(message);
        return gatewayResponse;
    }
    public static GatewayResponse success(Object data){
        GatewayResponse gatewayResponse = new GatewayResponse();
        gatewayResponse.setRepCode(RepCodeEnum.SUCCESS.getCode());
        gatewayResponse.setRepData(data);
        return gatewayResponse;
    }

    /** 操作失败信息
     * @param message
     * @return
     */
    public static GatewayResponse fail(RepCodeEnum message){
        GatewayResponse gatewayResponse = new GatewayResponse();
        gatewayResponse.setRepCodeEnum(message);
        return gatewayResponse;
    }
    public static GatewayResponse fail(String message){
        GatewayResponse gatewayResponse = new GatewayResponse();
        gatewayResponse.setRepCode(RepCodeEnum.FAIL.getCode());
        gatewayResponse.setRepMsg(message);
        return gatewayResponse;
    }
    public static GatewayResponse fail(RepCodeEnum repCodeEnum, String message){
        GatewayResponse gatewayResponse = new GatewayResponse();
        gatewayResponse.setRepCode(repCodeEnum.getCode());
        gatewayResponse.setRepMsg(message);
        return gatewayResponse;
    }

    /** 程序异常
     * @param message
     * @return
     */
    public static GatewayResponse error(String message){
        GatewayResponse gatewayResponse = new GatewayResponse();
        gatewayResponse.setRepCode(RepCodeEnum.ERROR.getCode());
        gatewayResponse.setRepMsg(RepCodeEnum.ERROR.getDesc() + ": " + message);
        return gatewayResponse;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String toJsonString() {
        return JSONObject.toJSONString(this);
    }

    public boolean isError(){
        return ! isSuccess();
    }

    public boolean isSuccess(){
        if(this == null){
            return false;
        }
        return StringUtils.equals(this.repCode, RepCodeEnum.SUCCESS.getCode());
    }

    public String getRepCode() {
        return repCode;
    }

    public void setRepCode(String repCode) {
        this.repCode = repCode;
    }
    public void setRepCodeEnum(RepCodeEnum repCodeEnum) {
        this.repCode=repCodeEnum.getCode();
        this.repMsg=repCodeEnum.getDesc();
    }

    public String getRepMsg() {
        return repMsg;
    }

    public void setRepMsg(String repMsg) {
        this.repMsg = repMsg;
    }

    public Object getRepData() {
        return repData;
    }

    public void setRepData(Object repData) {
        this.repData = repData;
    }

}
