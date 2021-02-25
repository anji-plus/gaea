package com.anji.plus.gaea.encryption;

import com.anji.plus.gaea.utils.JasyptUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

/**
 * 配置文件关键字段加密
 * @author lr
 * @since 2021-01-06
 */
public class EncryptionEnvironmentPostProcessor implements EnvironmentPostProcessor {

//    private GaeaProperties gaeaProperties;
//
//    public EncryptionFactoryPostProcessor(GaeaProperties gaeaProperties) {
//        this.gaeaProperties = gaeaProperties;
//    }

    /**
     * dataSourceProperties对应的用户名
     */
    private String username = "spring.datasource.username";

    /**
     * dataSourceProperties对应的密码
     */
    private String password = "spring.datasource.password";

    /**
     * 加密方法
     * @param environment
     * @param application
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        //加密私钥
//        String secret = gaeaPropertiesties.getSecret();
        String secret = "anji-plus";

        //加密的用户名
        String propertyUsername = environment.getProperty(username);
        //加密的密码
        String propertyPassword = environment.getProperty(password);

        if (StringUtils.isBlank(propertyUsername) || StringUtils.isBlank(propertyPassword)) {
            return;
        }

        Properties properties = new Properties();

        properties.put(username, JasyptUtils.decrypt(propertyUsername, secret));
        properties.put(password,JasyptUtils.decrypt(propertyPassword, secret));

        //放在最前面
        environment.getPropertySources().addFirst(new PropertiesPropertySource("gaeaProperties",properties));
    }
}
