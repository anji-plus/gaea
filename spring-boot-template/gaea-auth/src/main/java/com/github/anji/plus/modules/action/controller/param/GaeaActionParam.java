package com.github.anji.plus.modules.action.controller.param;


import com.github.anji.plus.gaea.curd.params.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 按钮权限(GaeaAction)param
 *
 * @author makejava
 * @since 2021-02-04 14:59:48
 */
@Getter
@Setter
public class GaeaActionParam extends PageParam implements Serializable {

    /**
     * 权限动作
     */
    @ApiModelProperty(value = "按钮code")
    private String actionCode;
    /**
     * 权限说明
     */
    @ApiModelProperty(value = "按钮名称")
    private String actionName;

    @ApiModelProperty(value = "0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG")
    private Integer enabled;

}