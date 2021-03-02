package com.anji.plus.modules.user.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.anji.plus.gaea.curd.entity.GaeaBaseEntity;

import java.io.Serializable;

/**
 * (GaeaUserRoleOrg)实体类
 *
 * @author makejava
 * @since 2021-02-03 18:01:03
 */
@TableName("gaea_user_role_org")
public class GaeaUserRoleOrg extends GaeaBaseEntity implements Serializable {
    /**
     * 用户名
     */
    private String username;
    /**
     * 角色code
     */
    private String roleCode;
    /**
     * 所属机构code
     */
    private String orgCode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }


}
