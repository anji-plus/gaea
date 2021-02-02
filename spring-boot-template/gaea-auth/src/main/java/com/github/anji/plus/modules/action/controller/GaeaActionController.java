package com.github.anji.plus.modules.action.controller;

import com.github.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.github.anji.plus.modules.action.dao.entity.GaeaAction;
import com.github.anji.plus.modules.action.controller.dto.GaeaActionDTO;
import com.github.anji.plus.modules.action.controller.param.GaeaActionParam;
import com.github.anji.plus.modules.action.service.GaeaActionService;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 按钮权限(GaeaAction)实体类
 *
 * @author lirui
 * @since 2021-02-02 13:37:04
 */
@RestController
@RequestMapping("/gaeaAction")
@Api(value = "/gaeaAction", tags = "按钮权限")
public class GaeaActionController extends GaeaBaseController<GaeaActionParam, GaeaAction, GaeaActionDTO> {
    @Autowired
    private GaeaActionService gaeaActionService;

    @Override
    public GaeaBaseService<GaeaActionParam, GaeaAction> getService() {
        return gaeaActionService;
    }

    @Override
    public GaeaAction getEntity() {
        return new GaeaAction();
    }

    @Override
    public GaeaActionDTO getDTO() {
        return new GaeaActionDTO();
    }
}
