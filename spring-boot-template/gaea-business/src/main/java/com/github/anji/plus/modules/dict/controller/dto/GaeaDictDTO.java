package com.github.anji.plus.modules.dict.controller.dto;

import java.util.Date;
import com.github.anji.plus.gaea.curd.dto.GaeaBaseDTO;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
/**
 * 数组字典(GaeaDict)实体类
 *
 * @author lirui
 * @since 2021-02-03 12:47:45
 */
@ApiModel(value = "数组字典")
public class GaeaDictDTO extends GaeaBaseDTO implements Serializable {
            /**
    * 字典名称
    */    
    @ApiModelProperty(value = "字典名称")
    private String dictName;
        /**
    * 字典编码
    */    
    @ApiModelProperty(value = "字典编码")
    private String dictCode;
        /**
    * 语言标识
    */    
    @ApiModelProperty(value = "语言标识")
    private String locale;
        /**
    * 描述
    */    
    @ApiModelProperty(value = "描述")
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