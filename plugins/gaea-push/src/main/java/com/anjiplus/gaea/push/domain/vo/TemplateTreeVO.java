package com.anjiplus.gaea.push.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TemplateTreeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 模板名称 template_name
     */
    private String label;
    /**
     * 模板id template_id
     */
    private Long value;

    /**
     * 模板代码 template_code
     */
    private String code;

    /**
     *  显示模板
     */
    private String show;

    /**
     *  显示模板参数
     */
    private Map param;

    /**
     * children
     */
    private List<TemplateTreeVO> children;


}
