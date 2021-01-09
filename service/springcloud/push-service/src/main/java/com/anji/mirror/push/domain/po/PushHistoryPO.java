package com.anji.mirror.push.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-10-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_history")
public class PushHistoryPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "push_id", type = IdType.AUTO)
    private Long pushId;

    /**
     * 模板类型，sms：短信；mail：邮件; dingtalk:
     */
    private String templateType;

    /**
     * 模板code
     */
    private String templateCode;

    /**
     * 内容
     */
    private String content;

    /**
     * sms:短信消息, mail:邮件标题, dingtalk: 钉钉消息
     */
    private String pushTitle;

    /**
     * 发送者
     */
    private String pushFrom;

    /**
     * 接受者,以,或;隔开
     */
    private String pushTo;

    /**
     * 发送的手机号（钉钉，短信）,以,隔开
     */
    private String mobiles;

    /**
     * 邮件抄送人,以,或;隔开
     */
    private String mailCopy;

    /**
     * 邮件密送人,以,或;隔开
     */
    private String mailBcc;

    /**
     * 操作者(短信-运营商、钉钉、邮箱)
     */
    private String operator;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;

    /**
     * 发送状态,1,成功;0,失败
     */
    private Integer sendStatus;

    /**
     * 发送结果
     */
    private String sendResult;

    private String createdBy;

    private LocalDateTime createdTime;

    private String updatedBy;

    private LocalDateTime updatedTime;


}
