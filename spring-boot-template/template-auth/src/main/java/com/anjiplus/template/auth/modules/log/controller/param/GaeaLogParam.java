package com.anjiplus.template.auth.modules.log.controller.param;


import com.anji.plus.gaea.curd.params.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * (GaeaLog)param
 *
 * @author peiyanni
 * @since 2021-02-18 16:30:25
 */
@Setter
@Getter
public class GaeaLogParam extends PageParam implements Serializable {
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String userName;
    /**
     * 请求路径
     */
    @ApiModelProperty(value = "请求路径")
    private String requestUrl;
    /**
     * 页面或按钮标题
     */
    @ApiModelProperty(value = "页面或按钮标题")
    private String pageTitle;
}
