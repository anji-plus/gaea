package com.anjiplus.gaea.push.domain.common;

import lombok.Data;

import java.util.Map;

/**
 * Created by raodeming on 2020/4/2.
 */
@Data
public class DingTalkParam {

    String sign;

    String secret;

    String appKey;

    String appSecret;

    /**
     * 应用agentId
     */
    Long agentId;

    /**
     * 接收者的用户手机列表，最大用户列表长度：100
     */
    String mobiles;

    /**
     * 消息json数据
     * {
     * "msgType":"text",  //必须
     * "title":"123",
     * "content":"123"
     * }
     */
    String jobNews;

    /**
     * 模板code
     */
    String templateCode;

    /**
     * 发送内容
     */
    Map paramMap;

    /**
     * 标题
     */
    String subject;

}
