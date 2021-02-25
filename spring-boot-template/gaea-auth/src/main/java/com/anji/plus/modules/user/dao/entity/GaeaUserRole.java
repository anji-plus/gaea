package com.anji.plus.modules.user.dao.entity;

import com.anji.plus.gaea.curd.entity.GaeaBaseEntity;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户角色对应关系(GaeaUserRole)实体类
 *
 * @author lr
 * @since 2021-02-02 14:59:43
 */
@TableName("gaea_user_role")
public class GaeaUserRole extends GaeaBaseEntity implements Serializable {

    private String username;

    private String roleCode;

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


}
