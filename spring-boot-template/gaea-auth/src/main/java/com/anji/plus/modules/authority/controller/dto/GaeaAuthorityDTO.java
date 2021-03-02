package com.anji.plus.modules.authority.controller.dto;

import com.anji.plus.gaea.curd.dto.GaeaBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单表(GaeaAuthority)实体类
 *
 * @author lirui
 * @since 2021-03-01 10:03:51
 */
@ApiModel(value = "权限表")
public class GaeaAuthorityDTO extends GaeaBaseDTO implements Serializable {


    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称")
    private String applicationName;

    /**
     * 权限代码
     */
    @ApiModelProperty(value = "权限代码")
    private String authCode;
    /**
     * 权限名称
     */
    @ApiModelProperty(value = "权限名称")
    private String authName;
    /**
     * 父级代码
     */
    @ApiModelProperty(value = "父级代码")
    private String parentCode;
    /**
     * 请求路径
     */
    @ApiModelProperty(value = "请求路径")
    private String path;
    /**
     * 排序，升序
     */
    @ApiModelProperty(value = "排序，升序")
    private Integer sort;
    /**
     * 0--禁用 1--启用
     */
    @ApiModelProperty(value = "0--禁用 1--启用")
    private Integer enabled;


    private List<String> authorities;

    private String roleCode;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
