package com.anji.plus.modules.push.history.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.anji.plus.gaea.curd.entity.GaeaBaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * (GaeaPushHistory)实体类
 *
 * @author lr
 * @since 2021-02-08 09:36:10
 */
@TableName("gaea_push_history")
public class GaeaPushHistory extends GaeaBaseEntity implements Serializable {

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
    private Object content;
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
    private Date sendTime;
    /**
     * 发送状态,1,成功;0,失败
     */
    private Integer sendStatus;
    /**
     * 发送结果
     */
    private Object sendResult;


    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getPushTitle() {
        return pushTitle;
    }

    public void setPushTitle(String pushTitle) {
        this.pushTitle = pushTitle;
    }

    public String getPushFrom() {
        return pushFrom;
    }

    public void setPushFrom(String pushFrom) {
        this.pushFrom = pushFrom;
    }

    public String getPushTo() {
        return pushTo;
    }

    public void setPushTo(String pushTo) {
        this.pushTo = pushTo;
    }

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    public String getMailCopy() {
        return mailCopy;
    }

    public void setMailCopy(String mailCopy) {
        this.mailCopy = mailCopy;
    }

    public String getMailBcc() {
        return mailBcc;
    }

    public void setMailBcc(String mailBcc) {
        this.mailBcc = mailBcc;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Object getSendResult() {
        return sendResult;
    }

    public void setSendResult(Object sendResult) {
        this.sendResult = sendResult;
    }


}
