package com.github.anji.plus.gaea.event;

import org.springframework.context.ApplicationEvent;

/**
 * 登录事件
 * @author lr
 * @since 2021-01-25
 */
public class LoginEvent<T> extends ApplicationEvent {

    private T t;

    public LoginEvent(T t) {
        super(t);
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
