package com.anjiplus.template.common.event;

import com.alibaba.fastjson.JSON;
import com.anjiplus.template.common.feign.BusinessServiceClient;
import com.anjiplus.gaea.export.event.GaeaExportApplicationEvent;
import com.anjiplus.gaea.export.vo.ExportOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * 功能描述：
 * 盖亚组件--导出信息监听
 *
 * @Author: peiyanni
 * @Date: 2021/1/27 15:17
 */
@Component
@Slf4j
@EnableAsync
public class GaeaExportEventListener {
    @Autowired
    private BusinessServiceClient businessServiceClient;

    @EventListener
    @Async
    public void getExportInfo(GaeaExportApplicationEvent event) {
        ExportOperation exportOperation = event.getExportOperation();
        log.info("--gaea-export:requestData:{}", JSON.toJSONString(exportOperation));
        Boolean isSuccess = businessServiceClient.export(exportOperation);
        log.info("--gaea-export:result---{}",isSuccess);
    }
}
