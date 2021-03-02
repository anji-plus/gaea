package com.anjiplus.gaea.push.type.email;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Map;

/**
 * 邮件发送明细
 *
 * @author lr
 * @since 2021-02-08
 */
public class MailPushDetails {

    /**
     * 模板编号
     */
    private String templateCode;

    /**
     * 发送主题
     */
    private String subject;

    /**
     * 收件人
     */
    private String to;

    /**
     * 发送人
     */
    private String from;

    /**
     * 抄送
     */
    private String cc;

    /**
     * 密送
     */
    private String bcc;

    /**
     * 邮件内容
     */
    private String text;

    /**
     * 是否是html
     */
    private boolean html;

    /**
     * 发送日期
     */
    private Date sentDate;

    private String param;

    private Map<String, MultipartFile> fileMap;

    private Map paramMap;

    private String secret;

    private String sign;

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Map<String, MultipartFile> getFileMap() {
        return fileMap;
    }

    public void setFileMap(Map<String, MultipartFile> fileMap) {
        this.fileMap = fileMap;
    }

    public Map getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map paramMap) {
        this.paramMap = paramMap;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }
}
