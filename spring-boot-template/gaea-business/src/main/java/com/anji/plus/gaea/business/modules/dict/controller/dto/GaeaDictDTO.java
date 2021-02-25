package com.anji.plus.gaea.business.modules.dict.controller.dto;

import com.anji.plus.gaea.curd.dto.GaeaBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * (GaeaDict)实体类
 *
 * @author lr
 * @since 2021-02-23 10:01:02
 */
@ApiModel(value = "")
public class GaeaDictDTO extends GaeaBaseDTO implements Serializable {
    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称")
    private String dictName;
    /**
     * 字典描述
     */
    @ApiModelProperty(value = "字典描述")
    private String dictDesc;
    /**
     * 代码英文
     */
    @ApiModelProperty(value = "代码英文")
    private String itemName;
    /**
     * 代码值，对应select option 值
     */
    @ApiModelProperty(value = "代码值，对应select option 值")
    private String itemValue;
    /**
     * 代码描述，对应select option 中文描述
     */
    @ApiModelProperty(value = "代码描述，对应select option 中文描述")
    private String itemDesc;
    /**
     * 字典扩展配置，select change时回传给前端
     */
    @ApiModelProperty(value = "字典扩展配置，select change时回传给前端")
    private String itemExtend;
    /**
     * 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG
     */
    @ApiModelProperty(value = "0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG")
    private Integer enabled;
    /**
     * 语言标识
     */
    @ApiModelProperty(value = "语言标识")
    private String locale;
    /**
     * 排序号
     */
    @ApiModelProperty(value = "排序号")
    private Integer sort;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictDesc() {
        return dictDesc;
    }

    public void setDictDesc(String dictDesc) {
        this.dictDesc = dictDesc;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemExtend() {
        return itemExtend;
    }

    public void setItemExtend(String itemExtend) {
        this.itemExtend = itemExtend;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
