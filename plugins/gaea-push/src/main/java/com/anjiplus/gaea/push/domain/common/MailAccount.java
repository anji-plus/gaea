package com.anjiplus.gaea.push.domain.common;

import lombok.Data;

/**
 * @author anji gaea teams
 * @Date: 2020/10/20
 * @Description:
 */
@Data
public class MailAccount {
    //设置发件人的SMTP服务器地址
    String host;
    //是否认证 true \ false
    String auth;
    //邮箱账户
    String address;
    //邮箱账户
    String account;
    //邮箱密码
    String password;
    //是否debug
    boolean debug;

}
