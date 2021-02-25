package com.anji.plus.modules.menu.controller.param;


import com.anji.plus.gaea.curd.params.PageParam;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 菜单表(GaeaMenu)param
 *
 * @author lr
 * @since 2021-02-02 13:36:43
 */
@Setter
@Getter
public class GaeaMenuParam extends PageParam implements Serializable {
    /**
     * 菜单代码
     */
    private String menuCode;
    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 系统代码
     */
    private String sysCode;

    /**
     * 启用状态
     */
    private Integer enabled;

    /**
     * 菜单路径
     */
    private String path;




}
