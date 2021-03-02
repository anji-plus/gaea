package com.anjiplus.gaea.push.type.sms;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.anjiplus.gaea.push.GaeaPushProperties.COMPONENT_NAME;
import static com.anji.plus.gaea.constant.GaeaConstant.COMPONENT_PREFIX;

/**
 * 短信属性配置
 *
 * @author lr
 * @since 2021-02-07
 */
@ConfigurationProperties(prefix = GaeaPushSmsProperties.COMPONENT_SMS_NAME)
public class GaeaPushSmsProperties {

    /**
     * 组件名称
     */
    public final static String COMPONENT_SMS_NAME = COMPONENT_PREFIX + COMPONENT_NAME + ".sms";

    /**
     * 阿里云短信
     */
    private Aliyun aliyun;

    /**
     * 极光短信
     */
    private Jiguang jiguang;

    /**
     * 上汽云
     */
    private Saic saic;

    public Aliyun getAliyun() {
        return aliyun;
    }

    public void setAliyun(Aliyun aliyun) {
        this.aliyun = aliyun;
    }

    public Jiguang getJiguang() {
        return jiguang;
    }

    public void setJiguang(Jiguang jiguang) {
        this.jiguang = jiguang;
    }

    public Saic getSaic() {
        return saic;
    }

    public void setSaic(Saic saic) {
        this.saic = saic;
    }

    /**
     * 上汽云短信
     */
    public class Saic {

        /**
         * 上汽云主机
         */
        private String host;

        /**
         * 上汽云端口
         */
        private int port;
        /**
         * 上汽公钥
         */
        private String publicKey;
        /**
         * 上汽私钥
         */
        private String privatekey;
        /**
         * 上汽是否加密，1：加密，0：不加密
         */
        private Boolean encry;
        /**
         * 扩展码
         */
        private String extendDecode;

        /**
         * 签名
         */
        private String signName;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public String getPrivatekey() {
            return privatekey;
        }

        public void setPrivatekey(String privatekey) {
            this.privatekey = privatekey;
        }

        public Boolean getEncry() {
            return encry;
        }

        public void setEncry(Boolean encry) {
            this.encry = encry;
        }

        public String getExtendDecode() {
            return extendDecode;
        }

        public void setExtendDecode(String extendDecode) {
            this.extendDecode = extendDecode;
        }

        public String getSignName() {
            return signName;
        }

        public void setSignName(String signName) {
            this.signName = signName;
        }
    }

    /**
     * 阿里云短信
     *
     * @author lr
     * @since 2021-02-07
     */
    public class Aliyun {

        /**
         * 阿里云accessKeyId
         */
        private String accessKeyId;

        /**
         * 阿里云secret
         */
        private String secret;

        public String getAccessKeyId() {
            return accessKeyId;
        }

        public void setAccessKeyId(String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }

    /**
     * 极光推送短信
     *
     * @author lr
     * @since 2021-02-07
     */
    public class Jiguang {

        /**
         * 极光appKey
         */
        private String appkey;

        /**
         * 极光masterSecret
         */
        private String masterSecret;

        public String getAppkey() {
            return appkey;
        }

        public void setAppkey(String appkey) {
            this.appkey = appkey;
        }

        public String getMasterSecret() {
            return masterSecret;
        }

        public void setMasterSecret(String masterSecret) {
            this.masterSecret = masterSecret;
        }
    }
}
