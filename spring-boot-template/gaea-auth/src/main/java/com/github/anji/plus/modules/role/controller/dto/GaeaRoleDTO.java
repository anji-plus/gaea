package com.github.anji.plus.modules.role.controller.dto;

import com.github.anji.plus.gaea.curd.dto.GaeaBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 角色(GaeaRole)实体类
 *
 * @author lirui
 * @since 2021-02-02 13:37:54
 */
@ApiModel(value = "角色")
public class GaeaRoleDTO extends GaeaBaseDTO {
            /**
    * 角色编码
    */
    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    private String roleName;
        /**
    * 1：可用 0：禁用
    */
    @ApiModelProperty(value = "1：可用 0：禁用")
    private Integer enabled;
        /**
    * 描述
    */
    @ApiModelProperty(value = "描述")
    private String remark;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
