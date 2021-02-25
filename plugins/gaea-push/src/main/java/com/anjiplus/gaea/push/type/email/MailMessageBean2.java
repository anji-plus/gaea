//package com.anjiplus.gaea.push.type.email;
//
//import com.anjiplus.gaea.push.domain.common.MailAccount;
//import com.anjiplus.gaea.push.utils.ExchangeClient;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.builder.ToStringBuilder;
//import org.apache.commons.lang3.builder.ToStringStyle;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.activation.DataHandler;
//import javax.mail.Message;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.*;
//import javax.mail.util.ByteArrayDataSource;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Properties;
//
///**
// * @author anji gaea teams
// * @Date: 2020/10/20
// * @Description:
// */
//@Component
//@EnableConfigurationProperties
//public class MailMessageBean2 {
//
//    /**
//     * 发送者
//     */
//    @Autowired
//    private JavaMailSender mailSender;
//
//    /**
//     * 邮件配置
//     */
//    @Autowired
//    private MailAutoProperties mailAutoProperties;
//
//    /**
//     * 发送邮件 MimeMessage
//     *
//     * @param mailParam
//     * @param mailContent
//     * @throws Exception
//     */
//    public void sedMailByMimeMessage(MailParam mailParam, String mailContent) throws Exception {
//        MailAccount account = new MailAccount();
//        account.setAccount(mailAccount);
//        account.setAddress(mailUserName);
//        log.info("account: {}", ToStringBuilder.reflectionToString(account, ToStringStyle.JSON_STYLE));
//        mailSender.send(getMimeMessage(null, mailParam, mailContent, account));
//    }
//
//    /**
//     * 发送邮件 MimeMessage
//     *
//     * @param mailParam
//     * @param mailContent
//     * @param account
//     * @throws Exception
//     */
//    public void sedMailByMimeMessage(MailParam mailParam, String mailContent, MailAccount account) throws Exception {
//        //1、连接邮件服务器的参数配置
//        Properties props = new Properties();
//        //设置用户的认证方式
//        props.setProperty("mail.smtp.auth", account.getAuth());
//        //设置传输协议
////        props.setProperty("mail.transport.protocol", transportProtocol);
//        //设置发件人的SMTP服务器地址
//        props.setProperty("mail.smtp.host", account.getHost());
//        //2、创建定义整个应用程序所需的环境信息的 Session 对象
//        Session session = Session.getInstance(props);
//        //设置调试信息在控制台打印出来
//        session.setDebug(account.isDebug());
//        //3、创建邮件的实例对象
//        Message msg = getMimeMessage(session, mailParam, mailContent, account);
//        //4、根据session对象获取邮件传输对象Transport
//        Transport transport = session.getTransport();
//        //设置发件人的账户名和密码
//        transport.connect(account.getAccount(), account.getPassword());
//        //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
//        transport.sendMessage(msg, msg.getAllRecipients());
//        //5、关闭邮件连接
//        transport.close();
//    }
//
//
//    /**
//     * 发送系统邮件 Exchange
//     *
//     * @param mailParam
//     * @param mailContent
//     * @throws Exception
//     */
//    public void sendMailByExchange(MailParam mailParam, String mailContent) throws Exception {
//        MailAccount account = new MailAccount();
//        account.setAddress(mailUserName);
//        account.setHost(mailHost);
//        account.setPassword(mailPassword);
//        log.info("account: {}", ToStringBuilder.reflectionToString(account, ToStringStyle.JSON_STYLE));
//        sendMailByExchange(mailParam, mailContent, account);
//    }
//
//    /**
//     * 发送系统邮件 Exchange
//     *
//     * @param mailParam
//     * @param mailContent
//     * @param account
//     * @throws Exception
//     */
//    public static void sendMailByExchange(MailParam mailParam, String mailContent, MailAccount account) throws Exception {
//        ExchangeClient client = new ExchangeClient.ExchangeClientBuilder()
//                .hostname(account.getHost())
//                .exchangeVersion(ExchangeVersion.Exchange2010)
//                .username(account.getAddress())
//                .password(account.getPassword())
//                .recipientTo(mailParam.getTo()) // 寄给某人
//                .recipientCc(mailParam.getCopy()) // 抄送
//                .recipientBcc(mailParam.getBcc()) // 私密发送邮件
//                .subject(mailParam.getSubject())// 主题
//                .message(mailContent) // 邮件内容
//                .attachments(mailParam.getFileMap()) //文件
//                .build();
//        client.sendExchange();
//
//    }
//
//
//    /**
//     * 获得创建一封邮件的实例对象
//     *
//     * @return
//     */
//    private MimeMessage getMimeMessage(Session session, MailParam mailParam, String mailContent, MailAccount account) throws Exception {
//        //1.创建一封邮件的实例对象
//        MimeMessage message;
//        if (Objects.isNull(session)) {
//            message = mailSender.createMimeMessage();
//        } else {
//            message = new MimeMessage(session);
//        }
//        return getMimeMessageInfo(message, mailParam, mailContent, account);
//    }
//
//
//    /**
//     * 获得创建一封邮件的实例对象
//     *
//     * @return
//     */
//    private MimeMessage getMimeMessageInfo(MimeMessage m, MailParam mailParam, String mailContent, MailAccount account) throws Exception {
//        //1.创建一封邮件的实例对象
//        MimeMessage message = m;
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//        //收件人
//        if (StringUtils.isNotEmpty(mailParam.getTo())) {
//            if (mailParam.getTo().indexOf(";") > -1) {
//                helper.setTo(mailParam.getTo().split(";"));
//            } else {
//                helper.setTo(mailParam.getTo().split(","));
//            }
//        }
//        //发件人
//        helper.setFrom(new InternetAddress(account.getAddress(), account.getAccount(), "UTF-8"));
//
//
//        //标题
//        helper.setSubject(mailParam.getSubject());
//        //抄送
//        if (StringUtils.isNotEmpty(mailParam.getCopy())) {
//            if (mailParam.getCopy().indexOf(";") > -1) {
//                helper.setCc(mailParam.getCopy().split(";"));
//            } else {
//                helper.setCc(mailParam.getCopy().split(","));
//            }
//        }
//        //私密发送邮件
//        if (StringUtils.isNotEmpty(mailParam.getBcc())) {
//            if (mailParam.getBcc().indexOf(";") > -1) {
//                helper.setBcc(mailParam.getTo().split(";"));
//            } else {
//                helper.setBcc(mailParam.getTo().split(","));
//            }
//        }
//        MimeMultipart mailpart = new MimeMultipart();
//        //文件
//        if (mailParam.getFileMap() != null && mailParam.getFileMap().size() > 0) {
//            for (Map.Entry<String, MultipartFile> fileEntry : mailParam.getFileMap().entrySet()) {
//                MimeBodyPart attachment = new MimeBodyPart();
//                //文件
//                DataHandler dh = new DataHandler(new ByteArrayDataSource(fileEntry.getValue().getBytes(), "application/octet-stream"));
////                DataHandler dh2 = new DataHandler(new ByteArrayInputStream(b));
//                // 将附件数据添加到"节点"
//                attachment.setDataHandler(dh);
//                // 设置附件的文件名（需要编码）
//                attachment.setContentID(fileEntry.getKey());
//                attachment.setFileName(MimeUtility.encodeText(fileEntry.getValue().getOriginalFilename()));
//                mailpart.addBodyPart(attachment);
//            }
//        }
//        MimeBodyPart text = new MimeBodyPart();
////        helper.setText(mailContent, true);
//        //邮件内容
//        text.setContent(mailContent, "text/html;charset=UTF-8");
//        mailpart.addBodyPart(text);
//        message.setContent(mailpart);
//        return message;
//    }
//}
