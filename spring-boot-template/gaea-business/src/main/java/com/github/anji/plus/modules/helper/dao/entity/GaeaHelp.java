package com.github.anji.plus.modules.helper.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.anji.plus.gaea.curd.entity.GaeaBaseEntity;

import java.io.Serializable;

/**
 * 帮助中心表(GaeaHelp)实体类
 *
 * @author lr
 * @since 2021-02-22 10:36:38
 */
@TableName("gaea_help")
public class GaeaHelp extends GaeaBaseEntity implements Serializable {
    /**
     * 帮助分类，字典=HELP_CATEGORY
     */
    private String helpCategory;
    /**
     * 标题
     */
    private String helpTitle;
    /**
     * 文本内容
     */
    private Object helpContent;
    /**
     * 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG
     */
    private Integer enabled;
    /**
     * 排序号
     */
    private Integer sort;
    /**
     * 备注
     */
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
