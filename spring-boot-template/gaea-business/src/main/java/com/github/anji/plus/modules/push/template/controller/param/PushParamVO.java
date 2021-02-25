package com.github.anji.plus.modules.push.template.controller.param;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Map;

/**
 * @author anji gaea teams
 * @Date: 2020/10/27
 * @Description:
 */
public class PushParamVO implements Serializable {
    private static final long serialVersionUID = 1141241214L;

    /** AlertChannelEnum
     * 模板类型: 1:邮件; 2:钉钉 3:短信；
     */
    Integer pushType;
    /**
     * 模板code
     */
    String templateCode;
    /**
     * 标题
     */
    String subject;
    /**
     * 发送者：email： 邮箱 以,分开   其他可以不填
     */
    String from;
    /**
     * 接收者 邮箱或手机号 以,分开
     */
    String to;
    /**
     * 抄送
     */
    String copy;
    /**
     * 密送
     */
    String Bcc;
    /**
     * 发送内容
     */
    Map paramMap;
    /**
     * 邮件发送文件
     */
    Map<String, MultipartFile> fileMap;

    /**
     * 发送内容json转字符串
     */
    String param;

    public Integer getPushType() {
        return pushType;
    }

    public void setPushType(Integer pushType) {
        this.pushType = pushType;
    }

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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCopy() {
        return copy;
    }

    public void setCopy(String copy) {
        this.copy = copy;
    }

    public String getBcc() {
        return Bcc;
    }

    public void setBcc(String bcc) {
        Bcc = bcc;
    }

    public Map getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, MultipartFile> getFileMap() {
        return fileMap;
    }

    public void setFileMap(Map<String, MultipartFile> fileMap) {
        this.fileMap = fileMap;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}


