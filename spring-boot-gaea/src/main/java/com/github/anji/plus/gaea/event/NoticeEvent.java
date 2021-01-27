package com.github.anji.plus.gaea.event;

import org.springframework.context.ApplicationEvent;

/**
 * 通知事件
 * @author lr
 * @since 2020-12-23
 */
public class NoticeEvent<T> extends ApplicationEvent {

    private T t;

    public NoticeEvent(T t) {
        super(t);
        this.t = t;
    }

}
