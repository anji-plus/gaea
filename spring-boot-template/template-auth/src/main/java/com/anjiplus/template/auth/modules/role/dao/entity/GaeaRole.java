package com.anjiplus.template.auth.modules.role.dao.entity;

import com.anji.plus.gaea.curd.entity.GaeaBaseEntity;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 角色(GaeaRole)实体类
 *
 * @author lr
 * @since 2021-02-02 13:37:54
 */
@TableName("gaea_role")
public class GaeaRole extends GaeaBaseEntity implements Serializable {
            /**
    * 角色编码
    */
    private String roleCode;

    private String roleName;
        /**
    * 1：可用 0：禁用
    */
    private Integer enabled;
        /**
    * 描述
    */
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
