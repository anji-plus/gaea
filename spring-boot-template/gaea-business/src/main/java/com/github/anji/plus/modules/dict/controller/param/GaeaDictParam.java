package com.github.anji.plus.modules.dict.controller.param;


import com.github.anji.plus.gaea.curd.params.PageParam;
import java.io.Serializable;

/**
 * 数组字典(GaeaDict)param
 *
 * @author lirui
 * @since 2021-02-03 12:47:45
 */
public class GaeaDictParam extends PageParam implements Serializable {


    /**
     * 数据字典编码
     */
    private String dictCode;

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }
}
