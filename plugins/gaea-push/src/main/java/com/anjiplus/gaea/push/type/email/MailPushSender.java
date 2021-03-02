package com.anjiplus.gaea.push.type.email;

import com.anjiplus.gaea.push.support.AbstractPushSender;
import com.anji.plus.gaea.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 发送邮件
 * @author lr
 * @since 2021-02-08
 */
public class MailPushSender extends AbstractPushSender<MailPushDetails> {

    /**
     * 发送者
     */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送邮件
     * @param details
     * @throws MessagingException
     */
    @Override
    public ResponseBean doSend(MailPushDetails details) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        MimeMessageBuilder builder = new MimeMessageBuilder(mimeMessageHelper);

        //发送人
        builder.from(details.getFrom())
                .to(details.getTo())
                .subject(details.getSubject())
                .cc(details.getCc())
                .bcc(details.getBcc())
                .text(details.getText(), details.getHtml())
                .sentDate(details.getSentDate());

        mailSender.send(mimeMessage);
        return ResponseBean.builder().build();
    }

    /**
     * 简单邮件
     * @param from
     * @param to
     * @param subject
     * @param text
     */
    public void simpleEmail(String from, String to, String subject, String text) {
        //创建简单邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        //谁发的
        message.setFrom(from);
        //谁要接收
        message.setTo(to);
        //邮件标题
        message.setSubject(subject);
        //邮件内容
        message.setText(text);
        mailSender.send(message);
    }

}
