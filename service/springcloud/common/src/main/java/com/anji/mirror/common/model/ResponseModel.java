package com.anji.mirror.common.model;

import com.alibaba.fastjson.JSONObject;
import com.anji.mirror.common.enums.RepCodeEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ResponseModel implements Serializable {

    private static final long serialVersionUID = 8445617032523881407L;

    private String            repCode;

    private String            repMsg;

    private Object            repData;

    public ResponseModel() {
        this.repCode = RepCodeEnum.SUCCESS.getCode();
    }

    public ResponseModel(RepCodeEnum repCodeEnum) {
       this.setRepCodeEnum(repCodeEnum);
    }

    //成功
    public static ResponseModel success(){
        return ResponseModel.success("成功");
    }
    public static ResponseModel success(String message){
        ResponseModel responseModel = new ResponseModel();
        responseModel.setRepMsg(message);
        return responseModel;
    }
    public static ResponseModel success(Object data){
        ResponseModel responseModel = new ResponseModel();
        responseModel.setRepCode(RepCodeEnum.SUCCESS.getCode());
        if(data instanceof IPage){
            responseModel.setRepData((IPage<?>)data);
        }else{
            responseModel.setRepData(data);
        }
        return responseModel;
    }
    public static ResponseModel successData(String message){
        ResponseModel responseModel = new ResponseModel();
        responseModel.setRepData(message);
        return responseModel;
    }

    /** 操作失败信息
     * @param message
     * @return
     */
    public static ResponseModel fail(RepCodeEnum message){
        ResponseModel responseModel = new ResponseModel();
        responseModel.setRepCodeEnum(message);
        return responseModel;
    }
    public static ResponseModel fail(RepCodeEnum repCodeEnum, String message){
        ResponseModel responseModel = new ResponseModel();
        responseModel.setRepCode(repCodeEnum.getCode());
        responseModel.setRepMsg(message);
        return responseModel;
    }
    public static ResponseModel fail(String message){
        ResponseModel responseModel = new ResponseModel();
        responseModel.setRepCode(RepCodeEnum.FAIL.getCode());
        responseModel.setRepMsg(message);
        return responseModel;
    }
    public static ResponseModel fail(String repCode, String message){
        ResponseModel responseModel = new ResponseModel();
        responseModel.setRepCode(repCode);
        responseModel.setRepMsg(message);
        return responseModel;
    }
    public static ResponseModel fail(Object serviceRepEnum){
        ResponseModel responseModel = new ResponseModel();
        if(serviceRepEnum == null){
            responseModel.setRepCode(RepCodeEnum.FAIL.getCode());
            responseModel.setRepMsg("response is null");
            return responseModel;
        }
        Class clazz = serviceRepEnum.getClass();
        if(clazz.isEnum()){
            try{
                Method getCode = clazz.getMethod("getCode");
                Method getDesc = clazz.getMethod("getDesc");
                responseModel.setRepCode((String)getCode.invoke(serviceRepEnum));
                responseModel.setRepMsg((String)getDesc.invoke(serviceRepEnum));
                return responseModel;
            }catch (Exception e){
                responseModel.setRepCode(RepCodeEnum.FAIL.getCode());
                responseModel.setRepMsg("RepCodeEnum in service must has [String getCode()] and [String getDesc()]");
                return responseModel;
            }
        }
        responseModel.setRepCode(RepCodeEnum.FAIL.getCode());
        responseModel.setRepMsg("unknow response type");
        responseModel.setRepData(serviceRepEnum);
        return responseModel;
    }

    /** 程序异常
     * @param message
     * @return
     */
    public static ResponseModel error(String message){
        ResponseModel responseModel = new ResponseModel();
        responseModel.setRepCode(RepCodeEnum.ERROR.getCode());
        responseModel.setRepMsg(RepCodeEnum.ERROR.getDesc() + ": " + message);
        return responseModel;
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


    public void setRepData(IPage<?> pageList) {
        JSONObject repData=new JSONObject();
        if(pageList != null){
            repData.put("list", pageList.getRecords());
            repData.put("currentPage", pageList.getCurrent());
            repData.put("pageSize", pageList.getSize());
            repData.put("totalPage", pageList.getPages());
            repData.put("totalCount", pageList.getTotal());
        }else{
            repData.put("list", new ArrayList<>());
            repData.put("currentPage", 1);
            repData.put("pageSize", 10);
            repData.put("totalPage", 1);
            repData.put("totalCount", 0);
        }
        this.repData = repData;
    }


}
