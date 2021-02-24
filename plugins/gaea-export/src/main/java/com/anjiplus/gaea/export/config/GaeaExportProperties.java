package com.anjiplus.gaea.export.config;

import com.github.anji.plus.gaea.constant.GaeaConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 导出中心属性
 * @author pyn
 * @since 2021-01-27
 */
@ConfigurationProperties(prefix = GaeaConstant.COMPONENT_PREFIX + GaeaExportProperties.COMPONENT_NAME)
public class GaeaExportProperties {

    /**
     * 组件名称
     */
    public final static String COMPONENT_NAME = "export";

}
