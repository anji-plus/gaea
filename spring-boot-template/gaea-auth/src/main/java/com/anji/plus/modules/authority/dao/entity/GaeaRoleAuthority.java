package com.anji.plus.modules.authority.dao.entity;

import com.anji.plus.gaea.curd.entity.GaeaBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
/**
 * 角色与权限对应关系(GaeaRoleAuthority)实体类
 *
 * @author lirui
 * @since 2021-03-01 15:23:23
 */
@TableName("gaea_role_authority")
public class GaeaRoleAuthority extends GaeaBaseEntity implements Serializable {

    private String roleCode;

    private String authorityPath;

    /**
     * 权限代码
     */
    private String authCode;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getAuthorityPath() {
        return authorityPath;
    }

    public void setAuthorityPath(String authorityPath) {
        this.authorityPath = authorityPath;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
