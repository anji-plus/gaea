package com.anjiplus.gaea.push.type.sms;

/**
 * 短信发送明细
 * @author lr
 * @since 2021-02-08
 */
public class SmsPushDetails {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 短信
     */
    private String message;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
