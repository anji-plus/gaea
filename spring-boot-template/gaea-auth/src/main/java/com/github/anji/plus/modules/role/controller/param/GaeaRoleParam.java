package com.github.anji.plus.modules.role.controller.param;


import com.github.anji.plus.gaea.curd.params.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 角色(GaeaRole)param
 *
 * @author lirui
 * @since 2021-02-02 13:37:54
 */
@Getter
@Setter
public class GaeaRoleParam extends PageParam implements Serializable {

    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 1：可用 0：禁用
     */
    private Integer enabled;
    /**
     * 描述
     */
    private String remark;
}
