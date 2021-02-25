package com.anji.plus.gaea.auth.modules.menuextension.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.anji.plus.gaea.curd.entity.GaeaBaseEntity;

import java.io.Serializable;

/**
 * (GaeaMenuUserExtension)实体类
 *
 * @author makejava
 * @since 2021-02-04 17:15:35
 */
@TableName("gaea_menu_user_extension")
public class GaeaMenuUserExtension extends GaeaBaseEntity implements Serializable {
    /**
     * 表格code
     */
    private String tableCode;
    /**
     * 菜单id
     */
    private String menuCode;
    /**
     * 用户ID
     */
    private String username;
    /**
     * 列中文名
     */
    private String name;
    /**
     * 列编码-和返回的表格数据里的key一致
     */
    private String code;
    /**
     * 是否可排序,0-不可排序，1-可排序
     */
    private Integer sortable;
    /**
     * 排序号
     */
    private Integer sortNo;
    /**
     * 0:不可见；1:可见
     */
    private Integer visible;
    /**
     * 宽度
     */
    private Integer width;
    /**
     * 删除标志
     */
    private Integer isDisabled;
    /**
     * 排序字段
     */
    private String sortCode;
    /**
     * 1升序，0降序，默认1
     */
    private Integer sortOrder;
    /**
     * 组名
     */
    private String groupName;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSortable() {
        return sortable;
    }

    public void setSortable(Integer sortable) {
        this.sortable = sortable;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Integer isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


}
