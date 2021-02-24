package com.github.anji.plus.modules.log.controller;

import com.anjiplus.gaea.log.aspect.LogOperation;
import com.github.anji.plus.gaea.bean.ResponseBean;
import com.github.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import com.github.anji.plus.modules.log.controller.dto.GaeaLogDTO;
import com.github.anji.plus.modules.log.controller.param.GaeaLogParam;
import com.github.anji.plus.modules.log.entity.GaeaLog;
import com.github.anji.plus.modules.log.service.GaeaLogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * (GaeaLog)实体类
 *
 * @author peiyanni
 * @since 2021-02-18 16:30:25
 */
@RestController
@RequestMapping("/log")
@Api(value = "/log", tags = "")
public class GaeaLogController extends GaeaBaseController<GaeaLogParam, GaeaLog, GaeaLogDTO> {
    @Autowired
    private GaeaLogService gaeaLogService;

    @Override
    public GaeaBaseService<GaeaLogParam, GaeaLog> getService() {
        return gaeaLogService;
    }

    @Override
    public GaeaLog getEntity() {
        return new GaeaLog();
    }

    @Override
    public GaeaLogDTO getDTO() {
        return new GaeaLogDTO();
    }

    @RequestMapping("/callback")
    public ResponseBean callback(@RequestBody LogOperation logOperation){
        gaeaLogService.saveCallbackInfo(logOperation);
        return responseSuccess();
    }
    @RequestMapping("/exportLogToFile")
    public ResponseBean exportLogToFile(@RequestBody GaeaLogParam gaeaLogParam){
        return responseSuccessWithData(gaeaLogService.exportLogToFile(gaeaLogParam));
    }
}