package com.anji.plus.modules.menuextension.controller;

import com.anji.plus.dto.DynamicQueryBo;
import com.anji.plus.gaea.annotation.log.GaeaAuditLog;
import com.anji.plus.gaea.bean.ResponseBean;
import com.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.anji.plus.gaea.curd.service.GaeaBaseService;
import com.anji.plus.gaea.holder.UserContentHolder;
import com.anji.plus.modules.menuextension.controller.dto.GaeaCommonConditionDTO;
import com.anji.plus.modules.menuextension.controller.param.ComConditionQueryParam;
import com.anji.plus.modules.menuextension.controller.param.CommonConditionInputBO;
import com.anji.plus.modules.menuextension.controller.param.GaeaCommonConditionParam;
import com.anji.plus.modules.menuextension.entity.GaeaCommonCondition;
import com.anji.plus.modules.menuextension.service.GaeaCommonConditionService;
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
@RequestMapping("/commonCondition")
@Api(value = "/commonCondition", tags = "")
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
        resquestParam.setCreateBy(UserContentHolder.getContext().getUsername());
        List<GaeaCommonConditionDTO> result = gaeaCommonConditionService.queryByCondition(resquestParam);
        return responseSuccessWithData(result);
    }

    @PostMapping("/getDynamicQueryBoListById")
    public List<DynamicQueryBo> getDynamicQueryBoListById(@RequestParam("commonId") Long commonId) {
        List<DynamicQueryBo> result = gaeaCommonConditionService.getDynamicQueryBoListById(commonId);
        return result;
    }
    @ApiOperation("常用查询条件新增")
    @PostMapping("/saveCondition")
    @GaeaAuditLog(pageTitle = "新增常用查询")
    public ResponseBean saveCommonCondition(@RequestBody CommonConditionInputBO requestParam){
        return responseSuccessWithData(gaeaCommonConditionService.saveCommonCondition(requestParam));
    }




}
