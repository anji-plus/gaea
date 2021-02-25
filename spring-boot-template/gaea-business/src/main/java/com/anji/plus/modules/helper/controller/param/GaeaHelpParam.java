package com.anji.plus.modules.helper.controller.param;


import com.anji.plus.gaea.curd.params.PageParam;

import java.io.Serializable;

/**
 * 帮助中心表(GaeaHelp)param
 *
 * @author lr
 * @since 2021-02-22 10:36:38
 */
public class GaeaHelpParam extends PageParam implements Serializable {

    private String helpCategory;

    private String helpTitle;

    private Integer enabled;


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

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
}
