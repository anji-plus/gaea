package com.anji.mirror.push.utils;

import com.anji.mirror.common.utils.DateUtil;
import com.anji.mirror.common.utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HttpInterfaceExmple {

    public static String vSend(boolean isEncry, String public_key, String private_key, String[] mobiles, String smscontext, String extendedcode, Date sendTime, String ip, int port) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("public_key", public_key);
        //防止为空
        if (StringUtils.isBlank(extendedcode)){
            extendedcode = "";
        }
        SendSmsData data = new SendSmsData(mobiles, smscontext, sendTime, extendedcode);
        String smsdata = JsonHelper.toJsonString(data);
        if (isEncry) {
            smsdata = EncryptionUtils.parseByte2HexStr(EncryptionUtils.aesEncrypt(smsdata, private_key));
            params.put("encry", "true");
        } else {
            smsdata = URLEncoder.encode(smsdata, "UTF-8");
            params.put("encry", "false");
        }
        String sign = EncryptionUtils.parseByte2HexStr(EncryptionUtils.aesEncrypt(DateUtil.toString(new Date(), "yyyyMMddHHmmssSSS"), private_key));
        params.put("sms_data", smsdata);
        params.put("sign", sign);
        System.out.println("send sms >>>>>> ");
        System.out.println("\tpublic_key : " + public_key);
        System.out.println("\tsms_data : " + smsdata);
        System.out.println("\tsign : " + sign);
        HttpRequestBody request = new HttpRequestBody("http://" + ip + ":" + port + "/interface/sendsms", "UTF-8", null, null, null, params);
        HttpClient client = new HttpClient(180, 180, false);
        long begin = System.currentTimeMillis();
        HttpResponseBody res = client.service(request);
        long end = System.currentTimeMillis();
        System.out.println("send sms result  >>>>>>\n\t" + res.getResultString());
        System.out.println("send sms use time : " + (end - begin) + "ms\n");
        return res.getResultString();
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

    public static void main(String[] args) throws UnsupportedEncodingException {
        //ip
        String ip = "10.108.129.124";
        int port = 9999;
        //公钥
        String pubkey = "GXKJ-1657-3819-824D";
        //私钥
        String prikey = "3819824D32854143";
        //是否加密
        boolean encry = true;
        //手机号
        String mobiles = "18810092406";
        //内容
        String context = "【安吉加加】：你的验证码为:123456";
        //扩展码
        String extendedcode = "123456";
        // 定时时间
        //Date sendtime = DateUtil.parseDate("2016-03-15 12:00:00", "yyyy-MM-dd HH:mm:ss"); // 定时发送
        Date sendtime = null; // 非定时发送
        // 发送
        String result = vSend(encry, pubkey, prikey, mobiles.split(","), context, extendedcode, sendtime, ip, port);
        System.out.println(result);
    }

}
