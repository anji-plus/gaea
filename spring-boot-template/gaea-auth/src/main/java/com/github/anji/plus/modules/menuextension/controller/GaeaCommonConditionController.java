package com.github.anji.plus.modules.menuextension.controller;

import com.github.anji.plus.gaea.bean.ResponseBean;
import com.github.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import com.github.anji.plus.modules.menuextension.controller.dto.GaeaCommonConditionDTO;
import com.github.anji.plus.modules.menuextension.controller.param.ComConditionQueryParam;
import com.github.anji.plus.modules.menuextension.controller.param.GaeaCommonConditionParam;
import com.github.anji.plus.modules.menuextension.entity.GaeaCommonCondition;
import com.github.anji.plus.modules.menuextension.service.GaeaCommonConditionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * (GaeaCommonCondition)实体类
 * 常用查询相关
 * @author makejava
 * @since 2021-02-02 14:42:41
 */
@RestController
@RequestMapping("/gaeaCommonCondition")
@Api(value = "/gaeaCommonCondition", tags = "")
public class GaeaCommonConditionController extends GaeaBaseController<GaeaCommonConditionParam, GaeaCommonCondition, GaeaCommonConditionDTO> {
    @Autowired
    private GaeaCommonConditionService gaeaCommonConditionService;

    @Override
    public GaeaBaseService<GaeaCommonConditionParam, GaeaCommonCondition> getService() {
        return gaeaCommonConditionService;
    }

    @Override
    public GaeaCommonCondition getEntity() {
        return new GaeaCommonCondition();
    }

    @Override
    public GaeaCommonConditionDTO getDTO() {
        return new GaeaCommonConditionDTO();
    }


    @ApiOperation("常用查询条件查询")
    @PostMapping("/queryByCondition/v1")
    public ResponseBean queryByCondition(@RequestBody ComConditionQueryParam resquestParam) {
        List<GaeaCommonConditionDTO> result = gaeaCommonConditionService.queryByCondition(resquestParam);
        return responseSuccessWithData(result);
    }




}