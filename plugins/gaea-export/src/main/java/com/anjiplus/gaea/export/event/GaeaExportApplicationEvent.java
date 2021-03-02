package com.anjiplus.gaea.export.event;

import com.anjiplus.gaea.export.vo.ExportOperation;
import org.springframework.context.ApplicationEvent;

/**
 * 功能描述：
 * 导出信息-事件发布
 * @Author: peiyanni
 * @Date: 2021/1/27 17:26
 */
public class GaeaExportApplicationEvent extends ApplicationEvent {

    private ExportOperation exportOperation;

    public GaeaExportApplicationEvent(Object source,ExportOperation exportOperation) {
        super(source);
        this.exportOperation=exportOperation;
    }

    public ExportOperation getExportOperation() {
        return exportOperation;
    }

    public void setExportOperation(ExportOperation exportOperation) {
        this.exportOperation = exportOperation;
    }
}
