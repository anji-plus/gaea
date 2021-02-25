package com.anji.plus.gaea.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

/**
 * Spring工具类
 * @author lr
 * @since 2021-01-12
 */
public class ApplicationContextUtils implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.applicationContext = applicationContext;
    }

    /**
     * 根据名称和类型获取SpringBean
     * @param name
     * @param requireType
     * @param <T>
     * @return
     */
    public static  <T> T getBean(String name, Class<T> requireType) {
        return applicationContext.getBean(name, requireType);
    }

    /**
     * 根据名称和类型获取SpringBean
     * @param requireType
     * @param <T>
     * @return
     */
    public static  <T> T getBean(Class<T> requireType) {
        return applicationContext.getBean(requireType);
    }


    /**
     * 发布事件
     * @param applicationEvent 事件
     */
    public static void publishEvent(ApplicationEvent applicationEvent) {
        applicationContext.publishEvent(applicationEvent);
    }
}
