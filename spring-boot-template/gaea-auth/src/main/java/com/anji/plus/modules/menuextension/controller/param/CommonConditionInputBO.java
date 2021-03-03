package com.anji.plus.modules.menuextension.controller.param;

import com.anji.plus.modules.menuextension.entity.GaeaCommonCondition;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：
 *
 * @Author: peiyanni
 * @Date: 2021/3/2 16:11
 */
@Getter
@Setter
public class CommonConditionInputBO implements Serializable {
    @ApiModelProperty(value = "菜单code")
    private String menuCode;

    @ApiModelProperty(value = "表Code")
    private String tableCode;

    @ApiModelProperty(value = "常用查询名称")
    private String searchName;

    @ApiModelProperty(value = "常用查询内容")
    List<CommonConditionReqBO> commonConditionReqBOList;

    public GaeaCommonCondition inputBO2Entity() {
        GaeaCommonCondition commoncondition = new GaeaCommonCondition();
        commoncondition.setMenuCode(menuCode);
        commoncondition.setSearchName(searchName);
        commoncondition.setTableCode(tableCode);
        return commoncondition;
    }
}
