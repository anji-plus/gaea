package com.anjiplus.gaea.push.domain.vo;

import com.anjiplus.gaea.push.domain.common.SmsTemplateAccount;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class TemplateVO implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /**
     * 模糊搜索 结束时间
     */
    private String endTime;
    /**
     * 模糊搜索 搜索关键词
     */
    private String keyword;
    /**
     * 模糊搜索 开始时间
    */
    private String startTime;

    /**
     * 短信模板信息
     */
    SmsTemplateAccount smsTemplateAccount;


}
