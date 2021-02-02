package com.github.anji.plus.menuextension.controller;

import com.github.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import com.github.anji.plus.menuextension.controller.dto.GaeaQueryConditionDTO;
import com.github.anji.plus.menuextension.controller.param.GaeaQueryConditionParam;
import com.github.anji.plus.menuextension.entity.GaeaQueryCondition;
import com.github.anji.plus.menuextension.service.GaeaQueryConditionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (GaeaQueryCondition)实体类
 * 高级查询相关
 * @author makejava
 * @since 2021-02-02 14:42:15
 */
@RestController
@RequestMapping("/gaeaQueryCondition")
@Api(value = "/gaeaQueryCondition", tags = "")
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
}