package com.anji.plus.gaea.event;

import org.springframework.context.ApplicationEvent;

/**
 * 异常事件
 * @author lr
 * @since 2021-01-25
 */
public class ExceptionEvent extends ApplicationEvent {

    private Throwable throwable;

    public ExceptionEvent(Throwable throwable) {
        super(throwable);
        this.throwable = throwable;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
