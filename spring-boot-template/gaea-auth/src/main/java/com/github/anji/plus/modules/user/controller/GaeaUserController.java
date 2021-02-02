package com.github.anji.plus.modules.user.controller;

import com.github.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.github.anji.plus.modules.user.dao.entity.GaeaUser;
import com.github.anji.plus.modules.user.controller.dto.GaeaUserDTO;
import com.github.anji.plus.modules.user.controller.param.GaeaUserParam;
import com.github.anji.plus.modules.user.service.GaeaUserService;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户表(GaeaUser)实体类
 *
 * @author lirui
 * @since 2021-02-02 13:38:12
 */
@RestController
@RequestMapping("/gaeaUser")
@Api(value = "/gaeaUser", tags = "用户表")
public class GaeaUserController extends GaeaBaseController<GaeaUserParam, GaeaUser, GaeaUserDTO> {
    @Autowired
    private GaeaUserService gaeaUserService;

    @Override
    public GaeaBaseService<GaeaUserParam, GaeaUser> getService() {
        return gaeaUserService;
    }

    @Override
    public GaeaUser getEntity() {
        return new GaeaUser();
    }

    @Override
    public GaeaUserDTO getDTO() {
        return new GaeaUserDTO();
    }
}
