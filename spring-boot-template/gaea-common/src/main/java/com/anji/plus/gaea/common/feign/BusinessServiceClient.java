package com.anji.plus.gaea.common.feign;

import com.anjiplus.gaea.export.vo.ExportOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <pre>
 * BusinessServiceClient
 * </pre>
 * , fallback = BusinessServiceClientFallback.class
 *
 * @author peiyanni
 * @version BusinessServiceClient.java
 */
@FeignClient(name = "gaea-business", fallback = BusinessServiceClientFallback.class)
public interface BusinessServiceClient {

    /**
     * 导出日志保存到表中
     *
     * @param exportOperation
     * @return ResponseBean
     */
    @RequestMapping(value = "/export/saveExportLog", method = RequestMethod.POST)
    Boolean export(@RequestBody ExportOperation exportOperation);
}
