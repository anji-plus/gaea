package com.anji.plus.modules.setting.controller.dto;

import com.anji.plus.gaea.curd.dto.GaeaBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * (GaeaSetting)实体类
 *
 * @author peiyanni
 * @since 2021-02-05 16:58:57
 */
@ApiModel(value = "")
public class GaeaSettingDTO extends GaeaBaseDTO {
    /**
     * 参数名称
     */
    @ApiModelProperty(value = "参数名称")
    private String settingName;
    /**
     * 参数描述
     */
    @ApiModelProperty(value = "参数描述")
    private String settingLabel;
    /**
     * 参数值类型，input input-number json-array
     */
    @ApiModelProperty(value = "参数值类型，input input-number json-array")
    private String settingType;
    /**
     * 参数表单
     */
    @ApiModelProperty(value = "参数表单")
    private String settingForm;
    /**
     * 表单保存的值，int\String\Json
     */
    @ApiModelProperty(value = "表单保存的值，int,String,Json")
    private String settingValue;
    /**
     * 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG
     */
    @ApiModelProperty(value = "0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG")
    private Integer enable;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
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
