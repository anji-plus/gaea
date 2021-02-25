package com.anjiplus.template.auth.modules.setting.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.anji.plus.gaea.curd.entity.GaeaBaseEntity;

import java.io.Serializable;

/**
 * (GaeaSetting)实体类
 *
 * @author makejava
 * @since 2021-02-05 16:58:35
 */
@TableName("gaea_setting")
public class GaeaSetting extends GaeaBaseEntity implements Serializable {
    /**
     * 参数名称
     */
    private String settingName;
    /**
     * 参数描述
     */
    private String settingLabel;
    /**
     * 参数值类型，input input-number json-array
     */
    private String settingType;
    /**
     * 参数表单
     */
    private String settingForm;
    /**
     * 表单保存的值，int\String\Json
     */
    private String settingValue;
    /**
     * 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG
     */
    private Integer enable;
    /**
     * 备注
     */
    private String remark;

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

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
