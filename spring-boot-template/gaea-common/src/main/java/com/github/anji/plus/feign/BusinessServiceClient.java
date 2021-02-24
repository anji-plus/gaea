package com.github.anji.plus.feign;

import com.anjiplus.gaea.export.vo.ExportOperation;
import com.github.anji.plus.dto.DynamicQueryBo;
import com.github.anji.plus.gaea.bean.ResponseBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
