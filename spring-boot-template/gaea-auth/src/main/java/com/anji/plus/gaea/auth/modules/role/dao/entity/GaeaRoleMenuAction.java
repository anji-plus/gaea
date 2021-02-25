package com.anji.plus.gaea.auth.modules.role.dao.entity;

import com.anji.plus.gaea.curd.entity.GaeaBaseEntity;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (GaeaRoleMenuAction)实体类
 *
 * @author lr
 * @since 2021-02-02 13:43:52
 */
@TableName("gaea_role_menu_action")
public class GaeaRoleMenuAction extends GaeaBaseEntity implements Serializable {
            /**
    * 角色编码
    */
    private String roleCode;
        /**
    * 菜单编码
    */
    private String menuCode;
        /**
    * 按钮编码
    */
    private String actionCode;

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

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }


}
