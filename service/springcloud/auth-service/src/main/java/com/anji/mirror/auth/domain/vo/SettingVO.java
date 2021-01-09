package com.anji.mirror.auth.domain.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@Data
@EqualsAndHashCode(callSuper = false)
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

    private String itemDesc;



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
