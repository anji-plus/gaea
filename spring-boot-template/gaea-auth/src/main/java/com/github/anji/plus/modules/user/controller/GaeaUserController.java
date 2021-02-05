package com.github.anji.plus.modules.user.controller;

import com.anji.captcha.model.common.ResponseModel;
import com.github.anji.plus.gaea.bean.ResponseBean;
import com.github.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.github.anji.plus.modules.menu.controller.dto.TreeDTO;
import com.github.anji.plus.modules.role.controller.param.RoleOrgReqParam;
import com.github.anji.plus.modules.user.controller.param.UserRoleOrgReqParam;
import com.github.anji.plus.modules.user.dao.entity.GaeaUser;
import com.github.anji.plus.modules.user.controller.dto.GaeaUserDTO;
import com.github.anji.plus.modules.user.controller.param.GaeaUserParam;
import com.github.anji.plus.modules.user.service.GaeaUserService;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import io.swagger.annotations.Api;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/user")
@Api(value = "/user", tags = "用户表")
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

    /**
     * 查询用户关联的机构角色树
     *
     * @param username
     * @return
     */
    @GetMapping("/queryRoleTree/{username}")
    public ResponseBean queryRoleTree(@PathVariable("username") String username) {
        TreeDTO data = gaeaUserService.queryRoleTree(username);
        return responseSuccessWithData(data);
    }

    /**
     * 保存用户角色机构关联关系
     *
     * @param reqParam
     * @return
     */
    @PostMapping("/saveRoleTree")
    public ResponseBean saveRoleTree(@RequestBody UserRoleOrgReqParam reqParam) {
        Boolean data = gaeaUserService.saveRoleTree(reqParam);
        return responseSuccessWithData(data);
    }


}
