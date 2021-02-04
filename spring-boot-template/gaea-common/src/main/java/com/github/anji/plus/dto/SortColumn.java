package com.github.anji.plus.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * SortColumn
 * 排序字段
 * 不传，框架默认按id倒叙
 * </pre>
 *
 * @author peiyanni
 * @version SortColumn.java
 */
@Data
@ApiModel(value = "SortColumn", description = "排序字段分装")
public class SortColumn implements Serializable {

    @ApiModelProperty(value = "排序字段名称，注意 这里不是表的字段，是返回前台表格，json中的key")
    private String columnName;

    @ApiModelProperty(value = "是否升序；ture:升序，false：降序")
    private boolean asc;

}
