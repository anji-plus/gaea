package com.github.anji.plus.gaea.annotation.enabled;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 组件装配
 * @author lr
 * @since 2021-01-15
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(GaeaConfigurationImportSelector.class)
public @interface EnabledGaeaConfiguration {
}
