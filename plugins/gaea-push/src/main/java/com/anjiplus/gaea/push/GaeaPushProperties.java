package com.anjiplus.gaea.push;

import com.anji.plus.gaea.constant.GaeaConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 推送属性
 * @author lr
 * @since 2021-01-20
 */
@ConfigurationProperties(prefix = GaeaConstant.COMPONENT_PREFIX + GaeaPushProperties.COMPONENT_NAME)
public class GaeaPushProperties {

    /**
     * 组件名称
     */
    public final static String COMPONENT_NAME = "push";

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
