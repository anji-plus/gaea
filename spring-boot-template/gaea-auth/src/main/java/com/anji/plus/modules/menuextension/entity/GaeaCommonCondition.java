package com.anji.plus.modules.menuextension.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.anji.plus.gaea.curd.entity.GaeaBaseEntity;

import java.io.Serializable;

/**
 * (GaeaCommonCondition)实体类
 *
 * @author makejava
 * @since 2021-02-02 14:42:40
 */
@TableName("gaea_common_condition")
public class GaeaCommonCondition extends GaeaBaseEntity implements Serializable {
    /**
     * 菜单ID
     */
    private String menuCode;
    /**
     * 查询sql
     */
    private String commSql;
    /**
     * 查询条件名称label
     */
    private String label;
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

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getCommSql() {
        return commSql;
    }

    public void setCommSql(String commSql) {
        this.commSql = commSql;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
