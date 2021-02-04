package com.github.anji.plus.modules.dict.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.anji.plus.gaea.curd.entity.GaeaBaseEntity;

import java.io.Serializable;

/**
 * 数据字典项(GaeaDictItem)实体类
 *
 * @author lirui
 * @since 2021-02-03 12:49:37
 */
@TableName("gaea_dict_item")
public class GaeaDictItem extends GaeaBaseEntity implements Serializable {
    /**
     * 数据字典编码
     */
    private String dictCode;
    /**
     * 字典项名称
     */
    private String itemName;
    /**
     * 字典项值
     */
    private String itemValue;
    /**
     * 语言标识
     */
    private String locale;
    /**
     * 描述
     */
    private String remark;
    /**
     * 排序
     */
    private Integer sort;

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
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

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }


}
