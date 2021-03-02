package com.anjiplus.gaea.push.type.sms.utils;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by raodeming on 2019/10/17.
 */
public class JgSmsUtil {
    protected static final Logger LOG = LoggerFactory.getLogger(JgSmsUtil.class);

    public static String jgSendSms(String appkey, String masterSecret, String mobile, int tempId, int signId, Map tempPara) {

        SMSClient client = new SMSClient(masterSecret, appkey);
        SMSPayload payload = SMSPayload.newBuilder()
                .setMobileNumber(mobile)
                .setTempId(tempId)
                .setTempPara(tempPara)
                .setSignId(signId)
                .build();
        try {
            SendSMSResult res = client.sendTemplateSMS(payload);
            LOG.info(res.toString());
            return res.toString();
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        }
        return "";
    }

}
