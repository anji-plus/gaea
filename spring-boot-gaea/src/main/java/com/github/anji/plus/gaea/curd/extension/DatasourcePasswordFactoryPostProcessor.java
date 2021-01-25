package com.github.anji.plus.gaea.curd.extension;

import com.github.anji.plus.gaea.utils.JasyptUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.util.Properties;

/**
 * 配置文件关键字段加密
 * @author lr
 * @since 2020-12-21
 */
public class DatasourcePasswordFactoryPostProcessor implements BeanFactoryPostProcessor {

    /**
     * dataSourceProperties对应的用户名
     */
    private String username = "spring.datasource.username";

    /**
     * dataSourceProperties对应的密码
     */
    private String password = "spring.datasource.password";

    /**
     * 对数据库用户名进行加解密
     *
     * @param configurableListableBeanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

        ConfigurableEnvironment environment = (StandardServletEnvironment) configurableListableBeanFactory.getBean("environment");

        //加密的用户名
        String propertyUsername = environment.getProperty(username);
        //加密的密码
        String propertyPassword = environment.getProperty(password);

        Properties properties = new Properties();

        properties.put(username, JasyptUtils.decrypt(propertyUsername));
        properties.put(password,JasyptUtils.decrypt(propertyPassword));

        //放在最前面
        environment.getPropertySources().addFirst(new PropertiesPropertySource("defaultProperties",properties));


    }
}
