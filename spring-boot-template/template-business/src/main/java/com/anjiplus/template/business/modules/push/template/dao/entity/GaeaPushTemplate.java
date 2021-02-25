package com.anjiplus.template.business.modules.push.template.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.anji.plus.gaea.curd.entity.GaeaBaseEntity;

import java.io.Serializable;

/**
 * (GaeaPushTemplate)实体类
 *
 * @author lr
 * @since 2021-02-08 09:36:40
 */
@TableName("gaea_push_template")
public class GaeaPushTemplate extends GaeaBaseEntity implements Serializable {

    /**
     * 模板名称
     */
    private String templateName;
    /**
     * 模板代码
     */
    private String templateCode;
    /**
     * 模板类型，sms：短信；mail：邮件
     */
    private String templateType;
    /**
     * 模板
     */
    private String template;
    /**
     * 模板预览
     */
    private String templateShow;
    /**
     * 模板参数
     */
    private String templateParam;
    /**
     * 三方应用模板相关信息，极光，阿里
     */
    private String templateInfo;
    /**
     * 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG
     */
    private Integer enableFlag;
    /**
     * 0--未删除 1--已删除 DIC_NAME=DELETE_FLAG
     */
    private Integer deleteFlag;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTemplateShow() {
        return templateShow;
    }

    public void setTemplateShow(String templateShow) {
        this.templateShow = templateShow;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }

    public String getTemplateInfo() {
        return templateInfo;
    }

    public void setTemplateInfo(String templateInfo) {
        this.templateInfo = templateInfo;
    }

    public Integer getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(Integer enableFlag) {
        this.enableFlag = enableFlag;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }


}
