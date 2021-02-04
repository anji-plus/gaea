package com.github.anji.plus.modules.menuextension.controller.dto;

import com.github.anji.plus.gaea.curd.dto.GaeaBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * (GaeaCommonCondition)实体类
 * 常用查询
 * @author makejava
 * @since 2021-02-02 14:42:40
 */
@ApiModel(value = "")
public class GaeaCommonConditionDTO extends GaeaBaseDTO {
    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单ID")
    private String menuCode;
    /**
     * 查询sql
     */
    @ApiModelProperty(value = "查询sql")
    private String sql;
    /**
     * 查询条件名称label
     */
    @ApiModelProperty(value = "查询条件名称label")
    private String lable;
    /**
     * 0:可用,1:已作废
     */
    @ApiModelProperty(value = "0:可用,1:已作废")
    private Integer isDisabled;
    /**
     * 表code
     */
    @ApiModelProperty(value = "表code")
    private String tableCode;
    /**
     * 常用查询名称
     */
    @ApiModelProperty(value = "常用查询名称")
    private String searchName;

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public Integer getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Integer isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }


}