package com.anji.mirror.push;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anji.mirror.push.utils.ExchangeClient;
import com.anji.mirror.push.utils.MailMessageUtil;
import com.anji.mirror.push.domain.common.MailParam;
import com.anjiplus.gaea.push.domain.common.MailAccount;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

/**
 * Created by raodeming on 2019/9/5.
 */
public class MailServiceImplTest {


    @Test
    public void test1() throws Exception {
        JavaMailSenderImpl jms = new JavaMailSenderImpl();
        jms.setHost("mail.xxxxx.com");
        jms.setPort(465);
        jms.setUsername("MS@xxxxx.com");
        jms.setPassword("123qweasd");
        jms.setDefaultEncoding("Utf-8");
        Properties p = new Properties();
        p.setProperty("mail.debug", "true");
        p.setProperty("mail.smtp.auth", "true");
        jms.setJavaMailProperties(p);



        MimeMessage mimeMessage = jms.createMimeMessage();
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("a@xxxxxx.com", "test");                //sender为自定义显示发件人名称
        helper.setTo("b@xxxxxx.com");
//        helper.setCc("test@163.com");
//        helper.setBcc("raodeming@xxxxxxx.com");

        helper.setSubject("我的测试");
        helper.setText("测试123");
        jms.send(mimeMessage);                      //邮件发送完毕
    }


    @Test
    public void test() {
        String a = "{\"jgSignId\":8}";
        JSONObject jsonObject = JSONObject.parseObject(a);

        String jgSignId = jsonObject.getString("jgSignId");
        System.out.println(Integer.parseInt(jgSignId));

        int i = 0;
        String s = String.valueOf(i);
        System.out.println(s);
        System.out.println("-----------------");
        List<Integer> lists = JSONArray.parseArray("[1, 2, 3]", Integer.class);
        lists.stream().forEach(System.out::println);

    }


    @Test
    public void test2() throws Exception {
        ExchangeClient client = new ExchangeClient.ExchangeClientBuilder()
                .hostname("mail.xxxxxxx.com")
                .exchangeVersion(ExchangeVersion.Exchange2010)
                .username("MS@xxxxxxxx.com")
                .password("password")
                .recipientTo("kean_qi@xxxxxx.com")
                .subject("测试")
                .message("Test Message")
                .build();
        client.sendExchange();

    }

    //发件人地址
    public static String address = "MS@xxxxxx.com";
    //收件人地址
    public static String from = "qizhiyuan@xxxxxx.com";
    //发件人账户名
    public static String account = "U";
    //发件人账户密码
    public static String password = "1234qwerasdf";
    //发件人的SMTP服务器地址
//    public static String host = "smtp.163.com";
    public static String host = "mail.xxxxxx.com";
    //用户的认证方式 是否认证
    public static Integer auth = 1;
    //是否是debuge 设置调试信息在控制台打印出来
    public static Integer debug = 1;

    @Autowired
    MailMessageUtil mailMessageUtil;

    @Test
    public void sedMailByMimeMessageTest() throws Exception {
        MailParam mailParam = new MailParam();
        mailParam.setTo(from);
//        mailParam.setFileMap();
        mailParam.setSubject("标题");
        String mailContent = "这是内容";
        MailAccount maccount = new MailAccount();
        maccount.setHost(host);
        maccount.setAddress(address);
        maccount.setAccount(account);
        maccount.setPassword(password);
        maccount.setAuth(auth == 1 ? "true": "false");
        maccount.setDebug(debug == 1);
        mailMessageUtil.sedMailByMimeMessage(mailParam, mailContent, maccount);

    }


    @Test
    public void sendMailByExchangeTest() throws Exception {
        MailParam mailParam = new MailParam();
        mailParam.setTo(from);
//        mailParam.setFileMap();
        mailParam.setSubject("标题");
        String mailContent = "这是内容";
        MailAccount maccount = new MailAccount();
        maccount.setHost(host);
        maccount.setAddress(address);
        maccount.setAccount(account);
        maccount.setPassword(password);
        maccount.setAuth(auth == 1 ? "true": "false");
        maccount.setDebug(debug == 1);
//        MailMessageUtil.sendMailByExchange(mailParam, mailContent, maccount);
        System.out.println(maccount.toString());
        //toString完成了对象转JSON的过程
        System.out.println(ToStringBuilder.reflectionToString(maccount, ToStringStyle.JSON_STYLE));;

    }

}
