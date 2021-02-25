package com.github.anji.plus.modules.helper.controller.dto;

import com.github.anji.plus.gaea.curd.dto.GaeaBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 帮助中心表(GaeaHelp)实体类
 *
 * @author lr
 * @since 2021-02-22 10:36:38
 */
@ApiModel(value = "帮助中心表")
public class GaeaHelpDTO extends GaeaBaseDTO implements Serializable {

    /**
     * 帮助分类，字典=HELP_CATEGORY
     */
    @ApiModelProperty(value = "帮助分类，字典=HELP_CATEGORY")
    private String helpCategory;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String helpTitle;
    /**
     * 文本内容
     */
    @ApiModelProperty(value = "文本内容")
    @NotNull
    private Object helpContent;
    /**
     * 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG
     */
    @ApiModelProperty(value = "0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG")
    private Integer enabled;
    /**
     * 排序号
     */
    @ApiModelProperty(value = "排序号")
    private Integer sort;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    public String getHelpCategory() {
        return helpCategory;
    }

    public void setHelpCategory(String helpCategory) {
        this.helpCategory = helpCategory;
    }

    public String getHelpTitle() {
        return helpTitle;
    }

    public void setHelpTitle(String helpTitle) {
        this.helpTitle = helpTitle;
    }

    public Object getHelpContent() {
        return helpContent;
    }

    public void setHelpContent(Object helpContent) {
        this.helpContent = helpContent;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
