package com.anjiplus.gaea.push.event;

import org.springframework.context.ApplicationEvent;

/**
 * 发送事件
 *
 * @author lr
 * @since 2021-02-08
 */
public class SendMessageEvent<T> extends ApplicationEvent {

    /**
     * 是否成功
     */
    private boolean success;

    public SendMessageEvent(T source, boolean success) {
        super(source);
        this.success = success;
    }
}
