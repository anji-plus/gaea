package com.github.anji.plus.modules.org.controller;

import com.github.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.github.anji.plus.modules.org.dao.entity.GaeaOrg;
import com.github.anji.plus.modules.org.controller.dto.GaeaOrgDTO;
import com.github.anji.plus.modules.org.controller.param.GaeaOrgParam;
import com.github.anji.plus.modules.org.service.GaeaOrgService;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 组织(GaeaOrg)实体类
 *
 * @author lirui
 * @since 2021-02-02 13:37:33
 */
@RestController
@RequestMapping("/gaeaOrg")
@Api(value = "/gaeaOrg", tags = "组织")
public class GaeaOrgController extends GaeaBaseController<GaeaOrgParam, GaeaOrg, GaeaOrgDTO> {
    @Autowired
    private GaeaOrgService gaeaOrgService;

    @Override
    public GaeaBaseService<GaeaOrgParam, GaeaOrg> getService() {
        return gaeaOrgService;
    }

    @Override
    public GaeaOrg getEntity() {
        return new GaeaOrg();
    }

    @Override
    public GaeaOrgDTO getDTO() {
        return new GaeaOrgDTO();
    }
}
