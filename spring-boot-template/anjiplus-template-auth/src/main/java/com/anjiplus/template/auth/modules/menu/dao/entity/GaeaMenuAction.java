package com.anjiplus.template.auth.modules.menu.dao.entity;

import com.anji.plus.gaea.curd.entity.GaeaBaseEntity;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 菜单按钮对应关系(GaeaMenuAction)实体类
 *
 * @author lr
 * @since 2021-02-02 16:51:55
 */
@TableName("gaea_menu_action")
public class GaeaMenuAction extends GaeaBaseEntity implements Serializable {
            /**
    * 菜单id
    */
    private String menuCode;
        /**
    * 操作id，如按钮
    */
    private String actionCode;

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
