package com.anji.plus.modules.push.template.controller.param;


import com.anji.plus.gaea.curd.dto.Query;
import com.anji.plus.gaea.curd.dto.QueryEnum;
import com.anji.plus.gaea.curd.params.PageParam;

import java.io.Serializable;

/**
 * (GaeaPushTemplate)param
 *
 * @author lr
 * @since 2021-02-08 09:36:40
 */
public class GaeaPushTemplateParam extends PageParam implements Serializable {

    /**
     * 模板名称
     */
    @Query(QueryEnum.LIKE)
    private String templateName;

    /**
     * 推送类型
     */
    private String templateType;


    /**
     * 发送时间,最小值与最大值用逗号隔开
     */
    @Query(value = QueryEnum.BWT)
    private String createTime;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
