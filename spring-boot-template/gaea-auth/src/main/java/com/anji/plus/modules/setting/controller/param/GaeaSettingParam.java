package com.anji.plus.modules.setting.controller.param;


import com.anji.plus.gaea.curd.params.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * (GaeaSetting)param
 *
 * @author peiyanni
 * @since 2021-02-05 16:58:58
 */
@Setter
@Getter
public class GaeaSettingParam extends PageParam implements Serializable {
    /**
     * 参数名称
     */
    @ApiModelProperty(value = "参数名称")
    private String settingName;
    /**
     * 参数描述
     */
    @ApiModelProperty(value = "参数描述")
    private String settingLabel;
}
