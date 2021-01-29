package com.github.anji.plus.gaea.event;

import org.springframework.context.ApplicationEvent;

/**
 * 登出事件
 * @author lr
 * @since 2021-01-25
 */
public class LogoutEvent<T> extends ApplicationEvent {

    private T t;

    public LogoutEvent(T t) {
        super(t);
        this.t = t;
    }

}
