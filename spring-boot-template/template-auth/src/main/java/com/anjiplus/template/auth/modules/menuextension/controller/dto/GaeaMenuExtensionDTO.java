package com.anjiplus.template.auth.modules.menuextension.controller.dto;

import com.anji.plus.gaea.curd.dto.GaeaBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * (GaeaMenuExtension)实体类
 *
 * @author makejava
 * @since 2021-02-04 17:14:15
 */
@ApiModel(value = "")
public class GaeaMenuExtensionDTO extends GaeaBaseDTO {
    /**
     * 表格code
     */
    @ApiModelProperty(value = "表格code")
    private String tableCode;
    /**
     * 菜单id
     */
    @ApiModelProperty(value = "菜单id")
    private String menuCode;
    /**
     * 列中文名
     */
    @ApiModelProperty(value = "列中文名")
    private String name;
    /**
     * 列编码-和返回的表格数据里的key一致
     */
    @ApiModelProperty(value = "列编码-和返回的表格数据里的key一致")
    private String code;
    /**
     * 是否可排序,0-不可排序，1-可排序
     */
    @ApiModelProperty(value = "是否可排序,0-不可排序，1-可排序")
    private Integer sortable;
    /**
     * 排序号
     */
    @ApiModelProperty(value = "排序号")
    private Integer sortNo;
    /**
     * 0:不可见；1:可见
     */
    @ApiModelProperty(value = "0:不可见；1:可见")
    private Integer visible;
    /**
     * 宽度
     */
    @ApiModelProperty(value = "宽度")
    private Integer width;
    /**
     * 删除标志
     */
    @ApiModelProperty(value = "删除标志")
    private Integer isDisabled;
    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private String sortCode;
    /**
     * 1升序，0降序，默认1
     */
    @ApiModelProperty(value = "1升序，0降序，默认1")
    private Integer sortOrder;
    /**
     * 组名
     */
    @ApiModelProperty(value = "组名")
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
