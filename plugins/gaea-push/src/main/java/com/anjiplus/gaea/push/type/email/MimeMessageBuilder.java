package com.anjiplus.gaea.push.type.email;

import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import java.util.Date;

/**
 * 构建邮件信息
 * @author lr
 * @since 2021-02-08
 */
public class MimeMessageBuilder {

    private MimeMessageHelper mimeMessageHelper;

    public MimeMessageBuilder(MimeMessageHelper mimeMessageHelper) {
        this.mimeMessageHelper = mimeMessageHelper;
    }
    /**
     * 设置发件人
     * @param from
     * @return
     * @throws MessagingException
     */
    public MimeMessageBuilder from(String from) throws MessagingException {
        mimeMessageHelper.setFrom(from);
        return this;
    }

    /**
     * 设置收件人
     * @param to
     * @return
     * @throws MessagingException
     */
    public MimeMessageBuilder to(String to) throws MessagingException {
        mimeMessageHelper.setTo(to);
        return this;
    }



    /**
     * 设置抄送人
     * @param cc
     * @return
     * @throws MessagingException
     */
    public MimeMessageBuilder cc(String cc) throws MessagingException {
        if (StringUtils.isNotBlank(cc)) {
            mimeMessageHelper.setCc(cc);
        }
        return this;
    }



    /**
     * 设置秘密发送
     * @param bcc
     * @return
     * @throws MessagingException
     */
    public MimeMessageBuilder bcc(String bcc) throws MessagingException {
        if (StringUtils.isNotBlank(bcc)) {
            mimeMessageHelper.setBcc(bcc);
        }
        return this;
    }

    /**
     * 设置主题
     * @param subject
     * @return
     * @throws MessagingException
     */
    public MimeMessageBuilder subject(String subject) throws MessagingException {
        mimeMessageHelper.setSubject(subject);
        return this;
    }

    /**
     * 设置内容
     * @param text
     * @return
     * @throws MessagingException
     */
    public MimeMessageBuilder text(String text) throws MessagingException {
        mimeMessageHelper.setText(text,true);
        return this;
    }

    /**
     * 设置内容
     * @param text 邮件内容
     * @param html 是否是超文本
     * @return
     * @throws MessagingException
     */
    public MimeMessageBuilder text(String text, boolean html) throws MessagingException {
        mimeMessageHelper.setText(text,html);
        return this;
    }

    /**
     * 设置发送时间
     * @param sentDate
     * @return
     * @throws MessagingException
     */
    public MimeMessageBuilder sentDate(Date sentDate) throws MessagingException {
        mimeMessageHelper.setSentDate(sentDate);
        return this;
    }

}
