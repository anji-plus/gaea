package com.anjiplus.gaea.push;

import com.anjiplus.gaea.push.type.email.MailAutoProperties;
import com.anjiplus.gaea.push.type.email.MailPushSender;
import com.anjiplus.gaea.push.type.sms.GaeaPushSmsProperties;
import com.anjiplus.gaea.push.type.sms.SmsPushSender;
import com.anji.plus.gaea.annotation.condition.ConditionalOnGaeaComponent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 推送组件
 * @author lr
 * @since 2021-01-15
 */
@Configuration
@EnableConfigurationProperties({GaeaPushProperties.class,GaeaPushSmsProperties.class, MailAutoProperties.class})
@ConditionalOnGaeaComponent(GaeaPushProperties.COMPONENT_NAME)
public class GaeaPushConfiguration {

    /**
     * 发送
     * @return
     */
    @Bean
    public SmsPushSender sendSmsBean() {
        return new SmsPushSender();
    }


    /**
     * 邮件发送
     * @return
     */
    @Bean
    public MailPushSender mailPushSender() {
        return new MailPushSender();
    }
}
