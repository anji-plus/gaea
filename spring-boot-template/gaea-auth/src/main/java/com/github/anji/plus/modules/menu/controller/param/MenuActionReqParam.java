package com.github.anji.plus.modules.menu.controller.param;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：
 * 菜单按钮新增请求实体
 * @Author: peiyanni
 * @Date: 2021/2/3 15:27
 */
@Getter
@Setter
public class MenuActionReqParam implements Serializable {
    /**
     * 菜单code
     */
    private String menuCode;
    /**
     * 所选按钮code集合
     */
    private List<String> actionCodes;
}
