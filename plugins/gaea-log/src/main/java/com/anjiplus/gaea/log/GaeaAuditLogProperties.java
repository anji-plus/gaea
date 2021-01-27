package com.anjiplus.gaea.log;

import com.github.anji.plus.gaea.constant.GaeaConstant;
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
     * 是否发布事件
     */
    private boolean publishEvent = true;

    public boolean isPublishEvent() {
        return publishEvent;
    }

    public void setPublishEvent(boolean publishEvent) {
        this.publishEvent = publishEvent;
    }
}
