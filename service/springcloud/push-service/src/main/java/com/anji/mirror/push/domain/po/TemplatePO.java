package com.anji.mirror.push.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("t_template")
public class TemplatePO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "template_id", type = IdType.AUTO)
    private Long templateId;

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
     * 短信模板发送排序例如：[1,2,3] 1:安吉 2：极光 3：阿里
     */
//    private String smsSorts;

    /**
     * 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG
     */
    private Integer enableFlag;

    /**
     * 0--未删除 1--已删除 DIC_NAME=DELETE_FLAG
     */
    private Integer deleteFlag;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;


    @TableField(exist = false)
    private Map paramMap;//没有此数据

    @TableField(exist = false)
    private String html;//没有此数据

}
