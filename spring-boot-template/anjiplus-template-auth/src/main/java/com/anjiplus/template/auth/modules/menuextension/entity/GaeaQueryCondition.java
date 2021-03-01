package com.anjiplus.template.auth.modules.menuextension.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.anji.plus.gaea.curd.entity.GaeaBaseEntity;

import java.io.Serializable;

/**
 * (GaeaQueryCondition)实体类
 *
 * @author makejava
 * @since 2021-02-04 17:16:01
 */
@TableName("gaea_query_condition")
public class GaeaQueryCondition extends GaeaBaseEntity implements Serializable {
    /**
     * 表格code
     */
    private String tableCode;
    /**
     * 菜单ID
     */
    private String menuCode;
    /**
     * 条件名称-key
     */
    private String name;
    /**
     * 条件名称-value
     */
    private String nameValue;
    /**
     * 条件值类型(1:字符串,2:数字,3:日期)
     */
    private Integer valueType;
    /**
     * 查询条件(1:>、2:<、3:>=、4:<=、5:LIKE)(暂时不使用此字段)
     */
    private Integer condition;
    /**
     * 条件类型(1:文本框、2:下拉框、3:联想控件、4:日期控件、5:数字、6:多记录文本)(1:文本框、2:下拉框、3:日期控件、4:多记录文本、5:数字、6:联想控件)（此处只取前四种类型，下拉框和联想控件合并，数字类型归为文本）
     */
    private Integer type;
    /**
     * 排列序号
     */
    private Integer place;
    /**
     * 数据源(1:接口、2:固定内容)(联想控件接口统一格式,code,name)
     */
    private Integer dataSource;
    /**
     * 数据源值
     */
    private String dataSourceValue;
    /**
     * 0:可用,1:已作废
     */
    private Integer isDisabled;
    /**
     * 日期精度
     */
    private String datePrecision;

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameValue() {
        return nameValue;
    }

    public void setNameValue(String nameValue) {
        this.nameValue = nameValue;
    }

    public Integer getValueType() {
        return valueType;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }

    public Integer getCondition() {
        return condition;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public Integer getDataSource() {
        return dataSource;
    }

    public void setDataSource(Integer dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSourceValue() {
        return dataSourceValue;
    }

    public void setDataSourceValue(String dataSourceValue) {
        this.dataSourceValue = dataSourceValue;
    }

    public Integer getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Integer isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getDatePrecision() {
        return datePrecision;
    }

    public void setDatePrecision(String datePrecision) {
        this.datePrecision = datePrecision;
    }


}
