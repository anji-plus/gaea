package com.anjiplus.gaea.push.support;

import com.anjiplus.gaea.push.event.SendMessageEvent;
import com.anji.plus.gaea.bean.ResponseBean;
import com.anji.plus.gaea.utils.ApplicationContextUtils;

/**
 * 发送消息抽象类
 * @author lr
 * @since 2021-02-08
 */
public abstract class AbstractPushSender<T> {

    /**
     * 发送消息,由子类实现
     * @param message
     * @return
     * @throws Exception
     */
    protected abstract ResponseBean doSend(T message) throws Exception;


    /**
     * 发送消息，由外部调用
     * @param message
     * @return
     * @throws Exception
     */
    public void send(T message) {

        boolean sendSuccess = true;
        //发送
        try {
            doSend(message);
        } catch (Exception e) {
            //异常，设置发送失败
            sendSuccess = false;
        } finally {
            //发布发送事件
            ApplicationContextUtils.publishEvent(new SendMessageEvent(message, sendSuccess));
        }

    }
}
