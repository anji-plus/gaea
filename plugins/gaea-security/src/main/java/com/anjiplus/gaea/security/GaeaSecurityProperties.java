package com.anjiplus.gaea.security;

import com.github.anji.plus.gaea.constant.GaeaConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lr
 * @since 2021-01-20
 */
@ConfigurationProperties(prefix = GaeaConstant.COMPONENT_PREFIX + GaeaSecurityProperties.COMPONENT_NAME)
public class GaeaSecurityProperties {

    /**
     * 组件名称
     */
    public final static String COMPONENT_NAME = "security";


    /**
     * 显示验证码，默认三次
     */
    private int captchaTimes = 3;

    /**
     * 锁定次数
     */
    private int lockTimes = 5;

    public int getCaptchaTimes() {
        return captchaTimes;
    }

    public void setCaptchaTimes(int captchaTimes) {
        this.captchaTimes = captchaTimes;
    }

    public int getLockTimes() {
        return lockTimes;
    }

    public void setLockTimes(int lockTimes) {
        this.lockTimes = lockTimes;
    }
}
