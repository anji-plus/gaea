package com.anji.mirror.push;


import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Created by raodeming on 2019/9/6.
 */

public class SendEmail {
    public static final String HOST = "mail.xxxxxx.com";
    public static final String PROTOCOL = "smtp";
    public static final int PORT = 25;
    public static final String FROM = "xxxxxx@xxxxxx.com";// 发件人的email
    public static final String PWD = "xxxxx";// 发件人密码

    /**
     * 获取Session
     *
     * @return
     */
    private static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);// 设置服务器地址
        props.put("mail.store.protocol", PROTOCOL);// 设置协议
        props.put("mail.smtp.port", PORT);// 设置端口
        props.put("mail.smtp.auth", true);

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("anjifimc", PWD);
            }

        };
        Session session = Session.getDefaultInstance(props, authenticator);

        return session;
    }

    public static void send(String toEmail, String content, String subject) {
        Session session = getSession();
        try {
            // System.out.println("--send--" + content);
            // Instantiate a message
            Message msg = new MimeMessage(session);

            // Set message attributes
            msg.setFrom(new InternetAddress(FROM));
            InternetAddress[] address = { new InternetAddress(toEmail) };
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);

            msg.setSentDate(new Date());
            msg.setContent(content, "text/html;charset=utf-8");

            // Send the message
            Transport.send(msg);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static void sendSSLEmail(String emailAddress, String content, String subject) {
        //emailAddress  收件人邮箱
        boolean isSSL = true;
        String host = "mail.xxxxx.com";
        int port = 465;
//        int port = 25;
        String from = "a@xxxxxx.com"; // 发件人的email
        boolean isAuth = true;
        final String password = "qwe123asd";

        Properties props = new Properties();
        props.put("mail.smtp.ssl.enable", isSSL);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", isAuth);

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("anjifimc" , password);
                return new PasswordAuthentication("MS" , password);
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.out.println("----测试");
        SendEmail.sendSSLEmail("bbbb@xxxxxx.com", "<a href='http://www.baidu.com'>请点击激活</a>", "subject");
        System.out.println("----完成");
    }
}