package com.github.anji.plus.modules.action.controller.dto;

import com.github.anji.plus.gaea.curd.dto.GaeaBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 按钮权限(GaeaAction)实体类
 *
 * @author lirui
 * @since 2021-02-02 13:37:04
 */
@ApiModel(value = "按钮权限")
public class GaeaActionDTO extends GaeaBaseDTO {
        /**
    * 主键
    */
    @ApiModelProperty(value = "主键")
    private Long actionId;
        /**
    * 权限动作
    */
    @ApiModelProperty(value = "权限动作")
    private String actionCode;
        /**
    * 权限说明
    */
    @ApiModelProperty(value = "权限说明")
    private String actionName;
        /**
    * 排序，升序
    */
    @ApiModelProperty(value = "排序，升序")
    private Integer sort;
        /**
    * 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG
    */
    @ApiModelProperty(value = "0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG")
    private Integer enabled;
        /**
    *  0--未删除 1--已删除 DIC_NAME=DEL_FLAG
    */
    @ApiModelProperty(value = " 0--未删除 1--已删除 DIC_NAME=DEL_FLAG")
    private Integer deleteFlag;

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

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
