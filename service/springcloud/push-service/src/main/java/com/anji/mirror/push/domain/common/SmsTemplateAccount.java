package com.anji.mirror.push.domain.common;

import lombok.Data;

/**
 * @author anji gaea teams
 * @Date: 2020/11/8
 * @Description: 短信模板对应账号信息
 */
@Data
public class SmsTemplateAccount {
//
//    /**
//     * 极光PublicKey
//     */
//    private String jgAppkey;
//    /**
//     * 极光Prikey
//     */
//    private String jgMasterSecret;
//    /**
//     * 阿里PublicKey
//     */
//    private String aliAccesskeyId;
//    /**
//     * 阿里Prikey
//     */
//    private String aliAccesskeySecret;
//    /**
//     * 安吉PublicKey
//     */
//    private String smsPublicKey;
//    /**
//     * 安吉Prikey
//     */
//    private String smPrikey;
//    /**
//     * 安吉短信是否加密，1：加密，0：不加密
//     */
//    private String smsEncry;
//    /**
//     * 安吉短信扩展码 默认为空
//     */
//    private Integer smsExtendDecode;

    /**
     * 短信发送优先级 "[1,2,3]"  1：安吉,2：极光,3：阿里
     */
    private String sendOrder;

    /**
     * 短信平台选择
     */
    private String signNameSelected;

    /**
     * 上汽安吉短信sign 默认上汽安吉
     *
     * http://10.108.129.124/custom/login
     */
    private String ajSignName;

    /**
     * 极光签名id-短信 极光服务平台  极光短信 签名管理 签名id  即短信提示平台名称
     */
    private int jgSignId;

    /**
     * 极光模板id-短信 https://docs.jiguang.cn//jsms/guideline/JSMS_consoleguide/
     * 极光服务平台  极光短信 模板管理 模板ID 即短信提示短信内容模板
     */
    private int jgTemplateId;

    /**
     * https://dysms.console.aliyun.com/dysms.htm
     * 阿里模板code 短信模板ID。请在控制台模板管理页面模板CODE一列查看。
     */
    private String aliTemplateCode;

    /**
     * https://dysms.console.aliyun.com/dysms.htm
     * 阿里签名名称-短信 短信签名名称。请在控制台签名管理页面签名名称一列查看。
     */
    private String aliSignName;

}
