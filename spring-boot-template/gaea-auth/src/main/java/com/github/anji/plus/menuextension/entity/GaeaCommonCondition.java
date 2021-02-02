package com.github.anji.plus.menuextension.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.anji.plus.gaea.curd.entity.GaeaBaseEntity;

import java.io.Serializable;

/**
 * (GaeaCommonCondition)实体类
 *
 * @author makejava
 * @since 2021-02-02 14:42:40
 */
@TableName("GaeaCommonCondition")
public class GaeaCommonCondition extends GaeaBaseEntity implements Serializable {
    /**
     * 菜单ID
     */
    private Long menuId;
    /**
     * 查询sql
     */
    private String sql;
    /**
     * 查询条件名称label
     */
    private String lable;
    /**
     * 0:可用,1:已作废
     */
    private Integer isDisabled;
    /**
     * 表code
     */
    private String tableCode;
    /**
     * 常用查询名称
     */
    private String searchName;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
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