package com.github.anji.plus.module.demo.controller;

import java.util.Date;
import com.github.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.github.anji.plus.module.demo.entity.TAction;
import com.github.anji.plus.module.demo.controller.dto.TActionDTO;
import com.github.anji.plus.module.demo.controller.param.TActionParam;
import com.github.anji.plus.module.demo.service.TActionService;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 运营权限表(TAction)实体类
 *
 * @author lirui
 * @since 2021-01-29 18:45:49
 */
@RestController
@RequestMapping("/tAction")
@Api(value = "/tAction", tags = "运营权限表")
public class TActionController extends GaeaBaseController<TActionParam, TAction, TActionDTO> {
    @Autowired
    private TActionService tActionService;
    
    @Override
    public GaeaBaseService<TActionParam, TAction> getService() {
        return tActionService;
    }

    @Override
    public TAction getEntity() {
        return new TAction();
    }

    @Override
    public TActionDTO getDTO() {
        return new TActionDTO();
    }
}