package com.anji.plus.modules.menu.controller.dto;

import com.anji.plus.gaea.curd.dto.GaeaBaseDTO;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单权限对应关系(GaeaMenuAction)实体类
 *
 * @author lr
 * @since 2021-02-02 16:51:55
 */
public class GaeaMenuAuthorityDTO extends GaeaBaseDTO implements Serializable {
    /**
     * 菜单编号
     */
    private String menuCode;
    /**
     * 权限编号
     */
    private List<String> authCodes;

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public List<String> getAuthCodes() {
        return authCodes;
    }

    public void setAuthCodes(List<String> authCodes) {
        this.authCodes = authCodes;
    }
}
