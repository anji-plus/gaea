package com.anji.plus.modules.user.controller;

import com.anji.plus.gaea.bean.ResponseBean;
import com.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.anji.plus.gaea.curd.service.GaeaBaseService;
import com.anji.plus.modules.menu.controller.dto.TreeDTO;
import com.anji.plus.modules.user.controller.dto.GaeaUserDTO;
import com.anji.plus.modules.user.controller.param.GaeaUserParam;
import com.anji.plus.modules.user.controller.param.GaeaUserPasswordParam;
import com.anji.plus.modules.user.controller.param.UserRoleOrgReqParam;
import com.anji.plus.modules.user.dao.entity.GaeaUser;
import com.anji.plus.modules.user.service.GaeaUserService;
import com.anjiplus.gaea.log.annotation.GaeaAuditLog;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/insertUser")
    @GaeaAuditLog(pageTitle = "新增用户")
    public ResponseBean saveGaeauser(@Validated @RequestBody GaeaUserDTO dto){
        return responseSuccessWithData(gaeaUserService.saveGaeaUser(dto));
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
    @GaeaAuditLog(pageTitle = "分配用户角色")
    public ResponseBean saveRoleTree(@RequestBody UserRoleOrgReqParam reqParam) {
        Boolean data = gaeaUserService.saveRoleTree(reqParam);
        return responseSuccessWithData(data);
    }

    /**
     * 用户修改密码
     * @param reqParam
     * @return
     */
    @PostMapping("/updatePassword")
    @GaeaAuditLog(pageTitle = "修改密码")
    public ResponseBean updatePassword(@RequestBody GaeaUserPasswordParam reqParam){
        return responseSuccessWithData(gaeaUserService.updatePassword(reqParam));
    }

    /**
     * 用户重置密码
     * @param reqParam
     * @return
     */
    @PostMapping("/resetPwd")
    @GaeaAuditLog(pageTitle = "重置密码")
    public ResponseBean resetPassword(@RequestBody GaeaUserPasswordParam reqParam){
        return responseSuccessWithData(gaeaUserService.setDefaultPwd(reqParam.getUsername()));
    }


}
