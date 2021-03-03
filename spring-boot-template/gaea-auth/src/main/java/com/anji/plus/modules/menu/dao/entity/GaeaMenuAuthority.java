package com.anji.plus.modules.menu.dao.entity;

import com.anji.plus.gaea.curd.entity.GaeaBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 菜单权限对应关系(GaeaMenuAction)实体类
 *
 * @author lr
 * @since 2021-02-02 16:51:55
 */
@TableName("gaea_menu_authority")
public class GaeaMenuAuthority extends GaeaBaseEntity implements Serializable {
    /**
     * 菜单id
     */
    private String menuCode;
    /**
     * 操作id，如按钮
     */
    private String authCode;

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
}
