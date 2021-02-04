package com.anjiplus.gaea.log.event;

import com.anjiplus.gaea.log.aspect.LogOperation;
import org.springframework.context.ApplicationEvent;

/**
 * 审计日志事件
 *
 * @author lr
 * @since 2021-01-20
 */
public class AuditLogApplicationEvent extends ApplicationEvent {
    private LogOperation logOperation;

    public void setLogOperation(LogOperation logOperation) {
        this.logOperation = logOperation;
    }

    public AuditLogApplicationEvent(Object obj, LogOperation logOperation) {
        super(obj);
        setLogOperation(logOperation);
    }

    public LogOperation getLogOperation() {
        return this.logOperation;
    }
}
