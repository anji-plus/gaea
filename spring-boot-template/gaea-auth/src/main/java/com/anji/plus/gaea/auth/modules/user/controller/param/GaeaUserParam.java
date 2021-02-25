package com.anji.plus.gaea.auth.modules.user.controller.param;


import com.anji.plus.gaea.curd.params.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户表(GaeaUser)param
 *
 * @author lr
 * @since 2021-02-02 13:38:12
 */
@Getter
@Setter
public class GaeaUserParam extends PageParam implements Serializable {
    @ApiModelProperty(value = "用户登录名")
    private String username;

    @ApiModelProperty(value = "真实姓名")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "1：可用 0：禁用")
    private Integer enabled;
}
