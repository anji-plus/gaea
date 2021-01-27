package com.anjiplus.gaea.log.event;

import com.anjiplus.gaea.log.LogOperation;
import org.springframework.context.ApplicationEvent;

/**
 * 审计日志事件
 * @author lr
 * @since 2021-01-20
 */
public class AuditLogApplicationEvent extends ApplicationEvent {

    public AuditLogApplicationEvent(LogOperation logOperation) {
        super(logOperation);
    }

    /**
     * 获取事件源
     * @return
     */
    public LogOperation getLogOperation() {
        return (LogOperation) getSource();
    }
}
