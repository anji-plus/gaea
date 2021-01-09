package com.anji.mirror.push.domain.common;

import lombok.Data;

import java.util.Map;

@Data
public class SmsParam {

    String projectCode;

    String mobiles;

    String context;

    String sign;

    String secret;

    String smsSign;

    String param;

    Map paramMap;

    String templateCode;

}
