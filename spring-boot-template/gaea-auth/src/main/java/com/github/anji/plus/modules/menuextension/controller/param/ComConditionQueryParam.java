package com.github.anji.plus.modules.menuextension.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 功能描述：
 * 常用查询查询实体
 * @Author: peiyanni
 * @Date: 2021/2/2 16:28
 */
@Getter
@Setter
public class ComConditionQueryParam implements Serializable {
    /**
     * 菜单id
     */
    private Long menuId;
    /**
     * 表格code
     */
    private String tableCode;
    /**
     * 创建人
     */
    private Long createBy;
}
