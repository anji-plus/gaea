package com.anji.plus.modules.push.history.controller;

import com.anji.plus.gaea.bean.ResponseBean;
import com.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.anji.plus.gaea.curd.service.GaeaBaseService;
import com.anji.plus.modules.push.history.controller.dto.GaeaPushHistoryDTO;
import com.anji.plus.modules.push.history.controller.param.GaeaPushHistoryParam;
import com.anji.plus.modules.push.history.dao.entity.GaeaPushHistory;
import com.anji.plus.modules.push.history.service.GaeaPushHistoryService;
import com.anji.plus.modules.push.history.service.impl.PushChartStatisticsVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (GaeaPushHistory)实体类
 *
 * @author lr
 * @since 2021-02-08 09:36:10
 */
@RestController
@RequestMapping("/gaeaPushHistory")
@Api(value = "/gaeaPushHistory", tags = "")
public class GaeaPushHistoryController extends GaeaBaseController<GaeaPushHistoryParam, GaeaPushHistory, GaeaPushHistoryDTO> {
    @Autowired
    private GaeaPushHistoryService gaeaPushHistoryService;

    @Override
    public GaeaBaseService<GaeaPushHistoryParam, GaeaPushHistory> getService() {
        return gaeaPushHistoryService;
    }

    @Override
    public GaeaPushHistory getEntity() {
        return new GaeaPushHistory();
    }

    @Override
    public GaeaPushHistoryDTO getDTO() {
        return new GaeaPushHistoryDTO();
    }


    @PostMapping("/getPushStatistics")
    public ResponseBean getPushStatistics(@RequestBody PushChartStatisticsVO requestModel) {
        return gaeaPushHistoryService.getPushStatistics(requestModel);
    }
}
