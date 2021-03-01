package com.anji.plus.modules.authority.controller.param;


import com.anji.plus.gaea.curd.dto.Query;
import com.anji.plus.gaea.curd.dto.QueryEnum;
import com.anji.plus.gaea.curd.params.PageParam;
import java.io.Serializable;

/**
 * 菜单表(GaeaAuthority)param
 *
 * @author lirui
 * @since 2021-03-01 10:03:51
 */
public class GaeaAuthorityParam extends PageParam implements Serializable {

    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * 权限代码
     */
    @Query(QueryEnum.LIKE)
    private String authCode;

    /**
     * 0--禁用 1--启用
     */
    private Integer enabled;

    /**
     * 请求路径
     */
    @Query(QueryEnum.LIKE)
    private String path;

    /**
     * 父级代码
     */
    private String parentCode;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
