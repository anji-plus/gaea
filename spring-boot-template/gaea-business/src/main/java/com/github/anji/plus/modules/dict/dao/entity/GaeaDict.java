package com.github.anji.plus.modules.dict.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.anji.plus.gaea.curd.entity.GaeaBaseEntity;

import java.io.Serializable;

/**
 * 数组字典(GaeaDict)实体类
 *
 * @author lirui
 * @since 2021-02-03 12:47:45
 */
@TableName("gaea_dict")
public class GaeaDict extends GaeaBaseEntity implements Serializable {
    /**
     * 字典名称
     */
    private String dictName;
    /**
     * 字典编码
     */
    private String dictCode;
    /**
     * 语言标识
     */
    private String locale;
    /**
     * 描述
     */
    private String remark;

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
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


}
