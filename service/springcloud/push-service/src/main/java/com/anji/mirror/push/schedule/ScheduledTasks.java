package com.anji.mirror.push.schedule;

import com.anji.mirror.push.service.PushHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务类
 */

@Slf4j
@EnableScheduling
@Component
public class ScheduledTasks {
    @Autowired
    PushHistoryService pushHistoryService;

    /**
     * t_alert_event表归档，每月1号将3个月前的那个月归档到t_alert_event_yyyyMM
     */
//    @Scheduled(cron = "0 30 1 1 * ?")//每月1号凌晨一点半
    @Scheduled(cron = "0 30 1 1 4,7,10,1 ?")//每三月1号凌晨一点半
//    @Scheduled(cron = "30 * * * * ?")//测试(每30秒执行一次)
    public void alertEventArchive() {
        try {
            log.info("t_history表归档，每月1号将3个月前的那个月归档到t_history_yyyyMM");
            pushHistoryService.alertEventArchive();
        } catch (Exception e) {
            log.error("t_alert_event表归档异常", e);
        }
    }


}
