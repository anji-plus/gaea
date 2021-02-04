package com.anjiplus.gaea.archiver.config;

import com.anjiplus.gaea.archiver.service.ArchiverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Date;
/**
 * springboot自动装配类
 *
 * @author 木子李·De
 * @since 2021/2/3 14:16
 */
@Configuration
@EnableScheduling
@EnableConfigurationProperties(ArchiverProperties.class)
@ComponentScan("com.anjiplus.gaea.archiver")
public class AutoConfiguration implements SchedulingConfigurer {
    private static Logger logger = LoggerFactory.getLogger(AutoConfiguration.class);

    @Autowired
    private ArchiverProperties archiverProperties;

    @Autowired
    private ArchiverService archiverService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(new TriggerTask(new Runnable() {
            @Override
            public void run() {
                archiverService.doArchiveTable();
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                // 定义定时器的Cron间隔，默认每月2号，凌晨3:0:0执行归档
                String cron = "0 0 3 2 */1 ?";
                if(archiverProperties.getArchiveScheduledCron() != null && archiverProperties.getArchiveScheduledCron().trim().length() > 5){
                    cron = archiverProperties.getArchiveScheduledCron().trim();
                }
                //调用执行器
                return new CronTrigger(cron).nextExecutionTime(triggerContext);
            }
        }));
    }
}
