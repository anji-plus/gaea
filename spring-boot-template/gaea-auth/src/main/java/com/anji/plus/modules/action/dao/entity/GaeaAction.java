package com.anji.plus.modules.action.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.anji.plus.gaea.curd.entity.GaeaBaseEntity;

import java.io.Serializable;

/**
 * 按钮权限(GaeaAction)实体类
 *
 * @author makejava
 * @since 2021-02-04 14:59:44
 */
@TableName("gaea_action")
public class GaeaAction extends GaeaBaseEntity implements Serializable {
    /**
     * 权限动作
     */
    private String actionCode;
    /**
     * 权限说明
     */
    private String actionName;
    /**
     * 排序，升序
     */
    private Integer sort;
    /**
     * 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG
     */
    private Integer enabled;
    /**
     * 0--未删除 1--已删除 DIC_NAME=DEL_FLAG
     */
    private Integer deleteFlag;

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }


}
