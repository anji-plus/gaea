package com.anjiplus.template.auth.modules.menu.controller.param;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 功能描述：
 *
 * @Author: peiyanni
 * @Date: 2021/2/7 13:03
 */
@Getter
@Setter
public class LeftMenuReqParam implements Serializable {
    /**
     * 用户当前所属机构
     */
    private String orgCode;
}
