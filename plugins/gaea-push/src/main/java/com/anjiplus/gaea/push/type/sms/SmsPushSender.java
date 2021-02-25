package com.anjiplus.gaea.push.type.sms;

import com.alibaba.fastjson.JSONObject;
import com.anjiplus.gaea.push.domain.common.SmsParam;
import com.anjiplus.gaea.push.domain.vo.TemplateVO;
import com.anjiplus.gaea.push.type.sms.utils.AliSmsUtil;
import com.anjiplus.gaea.push.type.sms.utils.SaicUtils;
import com.anjiplus.gaea.push.support.AbstractPushSender;
import com.anjiplus.gaea.push.utils.JgSmsUtil;
import com.anji.plus.gaea.bean.ResponseBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * 短信发送
 * @author lr
 * @since 2021-02-07
 */
public class SmsPushSender extends AbstractPushSender<SmsPushDetails> {

    private Logger logger = LoggerFactory.getLogger(SmsPushSender.class);

    @Autowired
    private GaeaPushSmsProperties  gaeaPushSmsProperties;


    @Override
    public ResponseBean doSend(SmsPushDetails message) throws Exception {
        return null;
    }


    /**
     * 发送极光短信
     *
     * @param mobile
     * @param tempPara
     * @return
     */
    public String sendJGSms(String mobile, Map tempPara, Integer jgSignId, Integer jgTemplateId) {
        String appKey = gaeaPushSmsProperties.getJiguang().getAppkey();
        String masterSecret = gaeaPushSmsProperties.getJiguang().getMasterSecret();
        if (StringUtils.isNotEmpty(appKey)
                && StringUtils.isNotEmpty(masterSecret)
                && Objects.nonNull(jgSignId)
                && Objects.nonNull(jgTemplateId)) {

            logger.info("发送极光短信 sendJGSendSms: {}", tempPara);

            return JgSmsUtil.jgSendSms(appKey, masterSecret,
                    mobile, jgTemplateId, jgSignId, tempPara);
        }
        return null;
    }

    /**
     * 发送阿里云短信
     *
     * @param phone
     * @param templatePara
     * @return
     */
    public String sendAliSms(String phone, Map templatePara, String signName, String aliTemplateCode) {

        String accessKeyId = gaeaPushSmsProperties.getAliyun().getAccessKeyId();
        String secret = gaeaPushSmsProperties.getAliyun().getSecret();

        if (StringUtils.isNoneBlank(accessKeyId)
                && StringUtils.isNoneBlank(secret)
                && StringUtils.isNoneBlank(signName)
                && StringUtils.isNoneBlank(aliTemplateCode)) {
            logger.info("发送阿里短信 sendAliSendSms: {} {}", phone, templatePara);

            return AliSmsUtil.aliSendSms(accessKeyId, secret,
                    phone, signName, aliTemplateCode, templatePara);
        }
        return null;
    }


    /**
     * 上汽安吉短信发送
     * @param smsParam
     * @param smsTemplate
     * @return
     * @throws Exception
     */
    public String sendSaicSms(SmsParam smsParam, TemplateVO smsTemplate, String signNameTemp) throws Exception {
        return sendSaicSms(smsParam, smsTemplate, signNameTemp, null);
    }

    /**
     * 上汽安吉短信定时发送
     * @param smsParam
     * @param smsTemplate
     * @return
     * @throws Exception
     */
    public String sendSaicSms(SmsParam smsParam, TemplateVO smsTemplate, String signNameTemp, Date sendTime) throws Exception {

        //获取上汽云配置信息
        GaeaPushSmsProperties.Saic saic = gaeaPushSmsProperties.getSaic();

        //公钥
        String pubkey = saic.getPublicKey();
        //私钥
        String priceyKey = saic.getPrivatekey();

        if (StringUtils.isEmpty(priceyKey) || StringUtils.isEmpty(pubkey)) {
            return null;
        }

        String result;
        String template = smsTemplate.getTemplate();
        //host
        String host = saic.getHost();
        //端口
        int port = saic.getPort();

        //是否加密
        boolean entry = saic.getEncry();
        //手机号
        String mobiles = smsParam.getMobiles();
        String signName = saic.getSignName();

        //如果不为空 重新赋值
        if (StringUtils.isNoneBlank(signNameTemp)) {
            signName = signNameTemp;
        }
        //内容
        String context = "【" + signName + "】" + template;
        //扩展码
        String extendDecode = saic.getExtendDecode();
        // 发送
        logger.info("上汽安吉短信发送 smsParam: {},  smsTemplate: {}", JSONObject.toJSONString(smsParam), JSONObject.toJSONString(smsTemplate));

        result = SaicUtils.send(entry, pubkey, priceyKey, mobiles.split(","), context, extendDecode, sendTime, host, port);
        return result;
    }
}

