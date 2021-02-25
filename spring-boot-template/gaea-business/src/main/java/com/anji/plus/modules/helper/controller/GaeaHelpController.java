package com.anji.plus.modules.helper.controller;

import com.anji.plus.gaea.bean.ResponseBean;
import com.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.anji.plus.gaea.exception.BusinessExceptionBuilder;
import com.anji.plus.modules.helper.dao.entity.GaeaHelp;
import com.anji.plus.modules.helper.controller.dto.GaeaHelpDTO;
import com.anji.plus.modules.helper.controller.param.GaeaHelpParam;
import com.anji.plus.modules.helper.service.GaeaHelpService;
import com.anji.plus.gaea.curd.service.GaeaBaseService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 帮助中心表(GaeaHelp)实体类
 *
 * @author lr
 * @since 2021-02-22 10:36:38
 */
@RestController
@RequestMapping("/gaeaHelp")
@Api(value = "/gaeaHelp", tags = "帮助中心表")
public class GaeaHelpController extends GaeaBaseController<GaeaHelpParam, GaeaHelp, GaeaHelpDTO> {
    @Autowired
    private GaeaHelpService gaeaHelpService;

    @Override
    public GaeaBaseService<GaeaHelpParam, GaeaHelp> getService() {
        return gaeaHelpService;
    }

    @Override
    public GaeaHelp getEntity() {
        return new GaeaHelp();
    }

    @Override
    public GaeaHelpDTO getDTO() {
        return new GaeaHelpDTO();
    }


    @GetMapping("/demo")
    public ResponseBean demo() {
        throw BusinessExceptionBuilder.build("javax.validation.constraints.AssertFalse.message");
    }
}
