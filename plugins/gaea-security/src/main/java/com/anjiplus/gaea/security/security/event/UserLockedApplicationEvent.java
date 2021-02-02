package com.anjiplus.gaea.security.security.event;

import org.springframework.context.ApplicationEvent;

/**
 * 用户锁定事件
 * @author lr
 * @since 2021-02-01
 */
public class UserLockedApplicationEvent extends ApplicationEvent {

    /**
     * 用户名
     */
    private String username;

    /**
     * 错误次数
     */
    private Integer errorTimes;

    public UserLockedApplicationEvent(String username,Integer errorTimes) {
        super(username);
        this.username = username;
        this.errorTimes = errorTimes;
    }

    public String getUsername() {
        return username;
    }

    public Integer getErrorTimes() {
        return errorTimes;
    }
}
