package com.github.anji.plus.gaea.annotation.enabled;

import com.github.anji.plus.gaea.annotation.condition.ConditionalOnGaeaComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lr
 * @since 2021-01-15
 */
public class GaeaConfigurationImportSelector implements ImportSelector , EnvironmentAware {

    private Logger logger = LoggerFactory.getLogger(GaeaConfigurationImportSelector.class);

    /**
     * 应用订阅盖亚的组件
     */

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        List<String> list =
                SpringFactoriesLoader.loadFactoryNames(EnabledGaeaConfiguration.class, GaeaConfigurationImportSelector.class.getClassLoader());
        //没有任何组件
        if (CollectionUtils.isEmpty(list)) {
            return new String[0];
        }

        //过滤掉没有@ConditionalOnGaeaComponent的类
        List<String> importAutoConfigurations = list.stream().filter(className -> {
            try {
                Class<?> gaeaExtensionClass = Class.forName(className);
                return gaeaExtensionClass.isAnnotationPresent(ConditionalOnGaeaComponent.class);
            } catch (ClassNotFoundException e) {
                return false;
            }
        }).collect(Collectors.toList());

        logger.info("盖亚装载的组件：{}", importAutoConfigurations);

        return importAutoConfigurations.toArray(new String[0]);
    }
}
