package com.github.anji.plus.dto;

import com.github.anji.plus.enums.DynamicQueryOperatorType;
import com.github.anji.plus.enums.DynamicQueryValueType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * DynamicQueryBo--高级查询bo
 * </pre>
 *
 * @author peiyanni
 * @version DynamicQueryBo.java, v 0.1 2020/5/25 16:41 lusue Exp $
 */
@Data
public class DynamicQueryBo implements Serializable {

    /**
     * 条件名称-key,注意 这个是sql语句里where 字段
     */
    private String name;

    /**
     * 关系运算：EQ("="), NE("<>"), GT(">"), GE(">="), LT("<"), LE("<="), IN("IN"), LIKE("LIKE")
     */
    private String operator;

    /**
     * 值类型1:字符串,2:数字,3:日期，默认是字符串
     */
    private Integer valueType;

    /**
     * 日期精度
     */
    private String datePrecision;

    /**
     * 值
     */
    private String value;

    /**
     * 关系运算类型 枚举
     * @return
     */
    public DynamicQueryOperatorType getDynamicQueryOperatorType() {
        return DynamicQueryOperatorType.getEnum(operator);
    }

    /**
     * 获取 值类型枚举
     * @return
     */
    public DynamicQueryValueType getDynamicQueryValueType() {
        return DynamicQueryValueType.getEnum(valueType);
    }
}
