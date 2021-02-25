package com.anjiplus.gaea.log.config;

import com.anji.plus.gaea.constant.GaeaConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 审计日志属性
 * @author lr
 * @since 2021-01-20
 */
@ConfigurationProperties(prefix = GaeaConstant.COMPONENT_PREFIX + GaeaAuditLogProperties.COMPONENT_NAME)
public class GaeaAuditLogProperties {

    /**
     * 组件名称
     */
    public final static String COMPONENT_NAME = "audit-log";
    /**
     * 微服务架构，可以设置回调地址来获取日志数据
     */
    public String callbackUrl;
    /**
     * 单体模式下，可以设置监听事件获取日志数据，
     */
    public boolean publishEvent;

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public boolean isPublishEvent() {
        return publishEvent;
    }

    public void setPublishEvent(boolean publishEvent) {
        this.publishEvent = publishEvent;
    }
}
