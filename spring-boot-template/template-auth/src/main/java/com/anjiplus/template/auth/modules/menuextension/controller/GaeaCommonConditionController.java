package com.anjiplus.template.auth.modules.menuextension.controller;

import com.anjiplus.template.common.dto.DynamicQueryBo;
import com.anji.plus.gaea.bean.ResponseBean;
import com.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.anji.plus.gaea.curd.service.GaeaBaseService;
import com.anjiplus.template.auth.modules.menuextension.controller.dto.GaeaCommonConditionDTO;
import com.anjiplus.template.auth.modules.menuextension.controller.param.ComConditionQueryParam;
import com.anjiplus.template.auth.modules.menuextension.controller.param.GaeaCommonConditionParam;
import com.anjiplus.template.auth.modules.menuextension.entity.GaeaCommonCondition;
import com.anjiplus.template.auth.modules.menuextension.service.GaeaCommonConditionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * (GaeaCommonCondition)实体类
 * 常用查询相关
 * @author peiyanni
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
    @PostMapping("/queryByCondition")
    public ResponseBean queryByCondition(@RequestBody ComConditionQueryParam resquestParam) {
        List<GaeaCommonConditionDTO> result = gaeaCommonConditionService.queryByCondition(resquestParam);
        return responseSuccessWithData(result);
    }

    @PostMapping("/getDynamicQueryBoListById")
    public List<DynamicQueryBo> getDynamicQueryBoListById(@RequestParam("commonId") Long commonId) {
        List<DynamicQueryBo> result = gaeaCommonConditionService.getDynamicQueryBoListById(commonId);
        return result;
    }



}
