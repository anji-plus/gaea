package com.anji.plus.modules.role.dao.entity;

import com.anji.plus.gaea.curd.entity.GaeaBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * (GaeaRoleMenuAction)实体类
 *
 * @author lr
 * @since 2021-02-02 13:43:52
 */
@TableName("gaea_role_menu_authority")
public class GaeaRoleMenuAuthority extends GaeaBaseEntity implements Serializable {
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 权限编码
     */
    private String authCode;

    /**
     * 权限路径
     */
    private String authPath;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthPath() {
        return authPath;
    }

    public void setAuthPath(String authPath) {
        this.authPath = authPath;
    }
}
