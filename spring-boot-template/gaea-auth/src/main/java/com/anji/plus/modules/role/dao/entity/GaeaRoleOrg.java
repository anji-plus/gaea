package com.anji.plus.modules.role.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.anji.plus.gaea.curd.entity.GaeaBaseEntity;

import java.io.Serializable;

/**
 * 角色与组织(GaeaRoleOrg)实体类
 *
 * @author makejava
 * @since 2021-02-03 16:43:59
 */
@TableName("gaea_role_org")
public class GaeaRoleOrg extends GaeaBaseEntity implements Serializable {

    /**
     * roleid
     */
    private String roleCode;
    /**
     * 组织id
     */
    private String orgCode;


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
