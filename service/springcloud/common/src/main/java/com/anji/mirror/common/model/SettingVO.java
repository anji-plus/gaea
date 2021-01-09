package com.anji.mirror.common.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-10-10
 */

public class SettingVO implements Serializable {

    private static final long serialVersionUID=1L;

    /** PK */
      private Long settingId;

    /** 参数名称 */
    private String settingName;

    /** 参数描述 */
    private String settingLabel;

    /** 参数值类型，input input-number json-array */
    private String settingType;

    /** 参数表单 */
    private String settingForm;

    /** 表单保存的值，int\String\Json */
    private String settingValue;

    /** 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG */
    private Integer enableFlag;

    /** 备注 */
    private String remark;

    /** 创建人 */
    private String createdBy;

    /** 创建时间 */
    private LocalDateTime createdTime;

    /** 修改人 */
    private String updatedBy;

    /** 修改时间 */
    private LocalDateTime updatedTime;

    /************************** 以下为非原表字段 ****************************/
    private JSONObject settingValueJson;

    private JSONArray settingFormJson;

    private String itemDesc;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getSettingId() {
        return settingId;
    }

    public void setSettingId(Long settingId) {
        this.settingId = settingId;
    }

    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    public String getSettingLabel() {
        return settingLabel;
    }

    public void setSettingLabel(String settingLabel) {
        this.settingLabel = settingLabel;
    }

    public String getSettingType() {
        return settingType;
    }

    public void setSettingType(String settingType) {
        this.settingType = settingType;
    }

    public String getSettingForm() {
        return settingForm;
    }

    public void setSettingForm(String settingForm) {
        this.settingForm = settingForm;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }

    public Integer getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(Integer enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public void setSettingValueJson(JSONObject settingValueJson) {
        this.settingValueJson = settingValueJson;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }


    /** 如果settingValue是int类型，返回Integer
     * @return Integer
     */
    public Integer getIntSettingValue() {
        if(this.settingValue == null || this.settingValue.trim().length() == 0){
            return null;
        }
        try{
            return Integer.parseInt(this.settingValue);
        }catch (Exception e){
            return null;
        }
    }

    public JSONObject getSettingValueJson(){
        if(this.settingValueJson != null){
            return settingValueJson;
        }
        if(this.settingValue == null || this.settingValue.trim().length() == 0){
            return new JSONObject();
        }
        try{
            settingValueJson = JSONObject.parseObject(this.settingValue);
            return settingValueJson;
        }catch (Exception e){
            return new JSONObject();
        }
    }

    public JSONArray getSettingFormJson(){
        if(this.settingFormJson != null){
            return settingFormJson;
        }
        if(this.settingValue == null || this.settingValue.trim().length() == 0
                || this.settingForm == null || this.settingForm.trim().length() == 0){
            return new JSONArray();
        }
        try{
            settingValueJson = JSONObject.parseObject(this.settingValue);
            settingFormJson = JSONArray.parseArray(this.settingForm);
            return settingFormJson;
        }catch (Exception e){
            return new JSONArray();
        }
    }


    public Integer getIntInSettingValueJson(String key){
        JSONObject json = getSettingValueJson();
        if(json == null || json.containsKey(key) ==false){
            return null;
        }
        return json.getInteger(key);
    }

    public Long getLongInSettingValueJson(String key){
        JSONObject json = getSettingValueJson();
        if(json == null || json.containsKey(key) ==false){
            return null;
        }
        return json.getLong(key);
    }

    public String getStringInSettingValueJson(String key){
        JSONObject json = getSettingValueJson();
        if(json == null || json.containsKey(key) ==false){
            return null;
        }
        return json.getString(key);
    }
}
