package com.anjiplus.template.business.modules.dict.controller.param;


import com.anji.plus.gaea.curd.params.PageParam;

import java.io.Serializable;

/**
 * (GaeaDict)param
 *
 * @author lr
 * @since 2021-02-23 10:01:02
 */
public class GaeaDictParam extends PageParam implements Serializable {
    /**
     * 字典名称
     */
    private String dictName;
    /**
     * 字典描述
     */
    private String dictDesc;
    /**
     * 代码英文
     */
    private String itemName;
    /**
     * 代码值，对应select option 值
     */
    private String itemValue;

    /**
     * 启用状态
     */
    private Integer enabled;

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

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
}
