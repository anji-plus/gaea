package com.anji.plus.modules.authority.dao.entity;

import com.anji.plus.gaea.curd.entity.GaeaBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 菜单表(GaeaAuthority)实体类
 *
 * @author lirui
 * @since 2021-03-01 10:03:51
 */
@TableName("gaea_authority")
public class GaeaAuthority extends GaeaBaseEntity implements Serializable {

    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * 权限代码
     */
    private String authCode;
    /**
     * 权限名称
     */
    private String authName;
    /**
     * 父级代码
     */
    private String parentCode;
    /**
     * 请求路径
     */
    private String path;
    /**
     * 排序，升序
     */
    private Integer sort;
    /**
     * 0--禁用 1--启用
     */
    private Integer enabled;

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
}
