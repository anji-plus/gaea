package com.anjiplus.gaea.push.type.sms.utils;


import com.alibaba.fastjson.JSONObject;
import com.anji.plus.gaea.constant.GaeaConstant;
import com.anji.plus.gaea.http.HttpClientUtils;
import com.anji.plus.gaea.utils.GaeaDateUtils;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 上汽云发送
 * @author lr
 * @since 2021-02-07
 */
public class SaicUtils {

    /**
     * 上汽云发送
     * @param encrypt 是否加密
     * @param public_key 公钥
     * @param private_key 私钥
     * @param mobiles 发送手机号
     * @param context 发送内容
     * @param extendedCode
     * @param sendTime 定时发送
     * @param ip
     * @param port
     * @return
     * @throws IOException
     */
    public static String send(boolean encrypt, String public_key, String private_key, String[] mobiles, String context, String extendedCode, Date sendTime, String ip, int port) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("public_key", public_key);
        //防止为空
        if (StringUtils.isBlank(extendedCode)){
            extendedCode = "";
        }
        SendSmsData data = new SendSmsData(mobiles, context, sendTime, extendedCode);
        String smsData = JSONObject.toJSONString(data);

        if (encrypt) {
            smsData = EncryptionUtils.parseByte2HexStr(EncryptionUtils.aesEncrypt(smsData, private_key));
            params.put("encry", "true");
        } else {
            smsData = URLEncoder.encode(smsData, GaeaConstant.CHARSET_UTF8);
            params.put("encry", "false");
        }
        String sign = EncryptionUtils.parseByte2HexStr(EncryptionUtils.aesEncrypt(GaeaDateUtils.toString(new Date(), "yyyyMMddHHmmssSSS"), private_key));
        params.put("sms_data", smsData);
        params.put("sign", sign);

        String url = "http://" + ip + ":" + port + "/interface/sendsms";
        //发送请求
        Response response = HttpClientUtils.post(url, null, params);

        return response.body().string();
    }


    public static class SendSmsData {

        private String[] mobiles;

        private String smscontent;

        private String extendedcode;

        private Date sendtime;

        public SendSmsData(String[] mobiles, String smscontent, Date sendtime, String extendedcode) {
            this.mobiles = mobiles;
            this.smscontent = smscontent;
            this.extendedcode = extendedcode;
            this.sendtime = sendtime;
        }

        public String[] getMobiles() {
            return mobiles;
        }

        public void setMobiles(String[] mobiles) {
            this.mobiles = mobiles;
        }

        public String getSmscontent() {
            return smscontent;
        }

        public void setSmscontent(String smscontent) {
            this.smscontent = smscontent;
        }

        public String getExtendedcode() {
            return extendedcode;
        }

        public void setExtendedcode(String extendedcode) {
            this.extendedcode = extendedcode;
        }

        public Date getSendtime() {
            return sendtime;
        }

        public void setSendtime(Date sendtime) {
            this.sendtime = sendtime;
        }


    }

    public static void main(String[] args) throws Exception {
        //ip
        String ip = "10.108.129.124";
        int port = 9999;
        //公钥
        String pubkey = "GXKJ-1657-3819-824D";
        //私钥
        String prikey = "3819824D32854143";
        //是否加密
        boolean encrypt = true;
        //手机号
        String mobiles = "18810092406";
        //内容
        String context = "【安吉加加】：你的验证码为:123456";
        //扩展码
        String extendedcode = "123456";
        //定时发送 Date sendtime = DateUtil.parseDate("2016-03-15 12:00:00", "yyyy-MM-dd HH:mm:ss");
        //非定时发送
        Date sendtime = null;
        // 发送
        String result = send(encrypt, pubkey, prikey, mobiles.split(","), context, extendedcode, sendtime, ip, port);
        System.out.println(result);
    }

}
