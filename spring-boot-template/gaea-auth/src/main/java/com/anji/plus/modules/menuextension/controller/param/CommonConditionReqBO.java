package com.anji.plus.modules.menuextension.controller.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * CommonConditionReqBO
 * @author	peiyanni
 * @version CommonConditionReqBO.java,
 * </pre>
 */
@Data
@ApiModel(value = "CommonConditionReqBO对象", description = "新增/编辑常用查询条件表BO")
public class CommonConditionReqBO implements Serializable {

    @ApiModelProperty(value = "查询条件名称label")
    private String label;

    @ApiModelProperty(value = "条件名称-key,注意 这个是sql语句里where 字段")
    private String name;

    @ApiModelProperty(value = "关系运算：EQ(\"=\"), NE(\"<>\"), GT(\">\"), GE(\">=\"), LT(\"<\"), LE(\"<=\"), IN(\"IN\"), LIKE(\"LIKE\")")
    private String operator;

    @ApiModelProperty(value = "值类型0:字符串,1:数字,2:日期，默认是字符串")
    private Integer valueType;

    @ApiModelProperty(value = "日期精度")
    private String datePrecision;

    @ApiModelProperty(value = "值")
    private String value;

    @ApiModelProperty(value = "下拉框的label")
    private String valueName;

    @ApiModelProperty(value = "1:文本框、2:下拉框、3:日期控件、4:时间控件、5:日期时间控件、6:多记录文本")
    private Integer type;
}