package com.anjiplus.gaea.push;

import com.github.anji.plus.gaea.annotation.condition.ConditionalOnGaeaComponent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 日志组件
 * @author lr
 * @since 2021-01-15
 */
@Configuration
@EnableConfigurationProperties(GaeaPushProperties.class)
@ConditionalOnGaeaComponent(GaeaPushProperties.COMPONENT_NAME)
public class GaeaPushConfiguration {

}
