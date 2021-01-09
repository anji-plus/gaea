package com.anji.mirror.push.utils;

import com.anji.mirror.push.domain.common.SmsParam;
import com.anji.mirror.push.domain.vo.TemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author anji gaea teams
 * @Date: 2020/10/22
 * @Description:
 */
@Component
@EnableConfigurationProperties
@Slf4j
public class SendSmsUtil {
    @Value("${customer.sms.aj.host}")
    private String ajHost;
    @Value("${customer.sms.aj.port}")
    private int ajPort;
    //上汽公钥
    @Value("${customer.sms.aj.smsPublicKey}")
    private String smsPublicKey;
    //上汽私钥
    @Value("${customer.sms.aj.smPrikey}")
    private String smPrikey;
    //上汽是否加密，1：加密，0：不加密
    @Value("${customer.sms.aj.smsEncry:1}")
    private Integer smsEncry;
    //扩展码
    @Value("${customer.sms.aj.smsExtendDecode:123456}")
    private String smsExtendDecode;
    @Value("${customer.sms.aj.sqajSignName}")
    private String sqajSignName;


    //极光appkey
    @Value("${customer.sms.jg.jgAppkey}")
    private String jgAppkey;
    //极光mastersecret
    @Value("${customer.sms.jg.jgMasterSecret}")
    private String jgMasterSecret;

    //阿里accesskey
    @Value("${customer.sms.ali.aliAccesskeyId}")
    private String aliAccesskeyId;
    //阿里Access Key Secret
    @Value("${customer.sms.ali.aliAccesskeySecret}")
    private String aliAccesskeySecret;



    /**
     * 发送极光短信
     *
     * @param mobile
     * @param tempPara
     * @return
     */
    public String sendJGSendSms(String mobile, Map tempPara, Integer jgSignId, Integer jgTemplateId) {
        if (StringUtils.isNotEmpty(jgAppkey)
                && StringUtils.isNotEmpty(jgMasterSecret)
                && Objects.nonNull(jgSignId)
                && Objects.nonNull(jgTemplateId)) {
            log.info("发送极光短信 sendJGSendSms: {}", tempPara);
            return JgSmsUtil.jgSendSms(jgAppkey, jgMasterSecret,
                    mobile, jgTemplateId, jgSignId, tempPara);
        }
        return null;
    }

    /**
     * 发送阿里短信
     *
     * @param phone
     * @param templatePara
     * @return
     */
    public String sendAliSendSms(String phone, Map templatePara, String aliSignName, String aliTemplateCode) {
        if (StringUtils.isNoneBlank(aliAccesskeyId)
                && StringUtils.isNoneBlank(aliAccesskeySecret)
                && StringUtils.isNoneBlank(aliSignName)
                && StringUtils.isNoneBlank(aliTemplateCode)) {
            log.info("发送阿里短信 sendAliSendSms: {} {}", phone, templatePara);
            return AliSmsUtil.aliSendSms(aliAccesskeyId, aliAccesskeySecret,
                    phone, aliSignName, aliTemplateCode, templatePara);
        }
        return null;
    }


    /**
     * 上汽安吉短信发送
     *
     * @param smsParam
     * @param smsTemplate
     * @return
     * @throws Exception
     */
    public String sendSqajSendSms(SmsParam smsParam, TemplateVO smsTemplate, String sqajSignName) throws Exception {
        if (StringUtils.isEmpty(smPrikey) || StringUtils.isEmpty(smsPublicKey)) {
            return null;
        }
        String result;
        String template = smsTemplate.getTemplate();
        //ip
        String ip = ajHost;
        int port = ajPort;
        //公钥
        String pubkey = smsPublicKey;
        //私钥
        String priceyKey = smPrikey;
        //是否加密
        boolean entry = smsEncry == 1;
        //手机号
        String mobiles = smsParam.getMobiles();
        String signName = sqajSignName;
        //如果不为空 重新赋值
        if (StringUtils.isNoneBlank(sqajSignName)) {
            signName = sqajSignName;
        }
        //内容
        String context = "【" + signName + "】" + template;
        //扩展码
        String extendDecode = smsExtendDecode;
        Date sendtime = null; // 非定时发送
        // 发送
        log.info("上汽安吉短信发送 smsParam: {},  smsTemplate: {}",
                ToStringBuilder.reflectionToString(smsParam, ToStringStyle.JSON_STYLE),
                ToStringBuilder.reflectionToString(smsTemplate, ToStringStyle.JSON_STYLE)
        );

        result = HttpInterfaceExmple.vSend(entry, pubkey, priceyKey, mobiles.split(","), context, extendDecode, sendtime, ip, port);
        return result;
    }
}

