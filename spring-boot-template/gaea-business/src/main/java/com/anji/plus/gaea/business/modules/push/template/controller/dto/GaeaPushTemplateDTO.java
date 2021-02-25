package com.anji.plus.gaea.business.modules.push.template.controller.dto;

import com.anji.plus.gaea.curd.dto.GaeaBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Map;

/**
 * (GaeaPushTemplate)实体类
 *
 * @author lr
 * @since 2021-02-08 09:36:40
 */
@ApiModel(value = "")
public class GaeaPushTemplateDTO extends GaeaBaseDTO implements Serializable {
    /**
     * 模板名称
     */
    @ApiModelProperty(value = "模板名称")
    private String templateName;
    /**
     * 模板代码
     */
    @ApiModelProperty(value = "模板代码")
    private String templateCode;
    /**
     * 模板类型，sms：短信；mail：邮件
     */
    @ApiModelProperty(value = "模板类型，sms：短信；mail：邮件")
    private String templateType;
    /**
     * 模板
     */
    @ApiModelProperty(value = "模板")
    private String template;
    /**
     * 模板预览
     */
    @ApiModelProperty(value = "模板预览")
    private String templateShow;
    /**
     * 模板参数
     */
    @ApiModelProperty(value = "模板参数")
    private String templateParam;
    /**
     * 三方应用模板相关信息，极光，阿里
     */
    @ApiModelProperty(value = "三方应用模板相关信息，极光，阿里")
    private String templateInfo;
    /**
     * 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG
     */
    @ApiModelProperty(value = "0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG")
    private Integer enableFlag;
    /**
     * 0--未删除 1--已删除 DIC_NAME=DELETE_FLAG
     */
    @ApiModelProperty(value = "0--未删除 1--已删除 DIC_NAME=DELETE_FLAG")
    private Integer deleteFlag;

    private Map paramMap;//没有此数据

    private String html;//没有此数据

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


    public Map getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map paramMap) {
        this.paramMap = paramMap;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
