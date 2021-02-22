package com.github.anji.plus.modules.menuextension.controller;

import com.github.anji.plus.gaea.bean.ResponseBean;
import com.github.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import com.github.anji.plus.modules.menuextension.controller.dto.GaeaQueryConditionDTO;
import com.github.anji.plus.modules.menuextension.controller.param.GaeaQueryConditionParam;
import com.github.anji.plus.modules.menuextension.entity.GaeaQueryCondition;
import com.github.anji.plus.modules.menuextension.service.GaeaQueryConditionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (GaeaQueryCondition)实体类
 *
 * @author makejava
 * @since 2021-02-04 17:16:02
 */
@RestController
@RequestMapping("/querycondition")
@Api(value = "/querycondition", tags = "")
public class GaeaQueryConditionController extends GaeaBaseController<GaeaQueryConditionParam, GaeaQueryCondition, GaeaQueryConditionDTO> {
    @Autowired
    private GaeaQueryConditionService gaeaQueryConditionService;

    @Override
    public GaeaBaseService<GaeaQueryConditionParam, GaeaQueryCondition> getService() {
        return gaeaQueryConditionService;
    }

    @Override
    public GaeaQueryCondition getEntity() {
        return new GaeaQueryCondition();
    }

    @Override
    public GaeaQueryConditionDTO getDTO() {
        return new GaeaQueryConditionDTO();
    }

    @PostMapping("/list")
    public ResponseBean queryCondition(@RequestBody GaeaQueryConditionDTO requestParam) {
        return responseSuccessWithData(gaeaQueryConditionService.queryCondition(requestParam));
    }
}