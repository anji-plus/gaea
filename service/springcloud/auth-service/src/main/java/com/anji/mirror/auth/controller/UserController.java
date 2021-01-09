package com.anji.mirror.auth.controller;

import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.anji.mirror.auth.domain.vo.StringParamVO;
import com.anji.mirror.auth.domain.vo.UserVO;
import com.anji.mirror.auth.service.UserService;
import com.anji.mirror.common.annotation.Log;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.security.Authentication;
import com.anji.mirror.common.security.HasPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CaptchaService captchaService;

    @PostMapping("/create")
    @HasPermission("userManage:add")
    @Log(pageTitle = "新增用户")
    public ResponseModel create(@RequestBody RequestModel<UserVO> requestModel) {
        return userService.create(requestModel);
    }

    @PostMapping("/updateById")
    @HasPermission("userManage:edit")
    @Log(pageTitle = "更新用户")
    public ResponseModel updateById(@RequestBody RequestModel<UserVO> requestModel) {
        return userService.updateById(requestModel);
    }

    @PostMapping("/deleteById")
    @HasPermission("userManage:delete")
    @Log(pageTitle = "删除用户")
    public ResponseModel deleteById(@RequestBody RequestModel<UserVO> requestModel) {
        return userService.deleteById(requestModel);
    }

    @PostMapping("/queryById")
    @HasPermission("userManage:find")
    @Log(pageTitle = "根据id查询用户")
    public ResponseModel queryById(@RequestBody RequestModel<UserVO> requestModel) {
        ResponseModel responseModel = userService.queryById(requestModel);
        requestModel.getData().setPassword(null);
        return responseModel;
    }

    @PostMapping("/queryByPage")
    @HasPermission("userManage:find")
    @Log(pageTitle = "分页查询用户")
    public ResponseModel queryByPage(@RequestBody RequestModel<UserVO> requestModel) {
        return userService.queryByPage(requestModel);
    }

    @PostMapping("/login")
    @Log(pageTitle = "登录")
    public ResponseModel login(@RequestBody RequestModel<UserVO> requestModel) {
        return userService.login(requestModel);
    }

    @PostMapping("/logout")
    @Log(pageTitle = "登出")
    public ResponseModel logout(@RequestBody RequestModel<UserVO> requestModel) {
        return userService.logout(requestModel);
    }

    @PostMapping("/queryRoleTree")
    @HasPermission("userManage:find")
    @Log(pageTitle = "查询用户分配组织角色")
    public ResponseModel queryRoleTree(@RequestBody RequestModel<UserVO> requestModel) {
        return userService.queryRoleTree(requestModel);
    }

    @PostMapping("/saveRoleTree")
    @HasPermission("userManage:edit")
    @Log(pageTitle = "保存用户分配组织角色")
    public ResponseModel saveRoleTree(@RequestBody RequestModel<UserVO> requestModel) {
        return userService.saveRoleTree(requestModel);
    }

    @PostMapping("/captcha/get")
    @Log(pageTitle = "获取验证码")
    public com.anji.captcha.model.common.ResponseModel get(@RequestBody RequestModel<CaptchaVO> requestModel) {
        return captchaService.get(requestModel.getData());
    }

    @PostMapping("/captcha/check")
    @Log(pageTitle = "校验验证码")
    public com.anji.captcha.model.common.ResponseModel check(@RequestBody RequestModel<CaptchaVO> requestModel) {
        return captchaService.check(requestModel.getData());
    }

    @PostMapping("/updatePassword")
    @Log(pageTitle = "用户自己根据旧密码修改密码")
    public ResponseModel updatePassword(@RequestBody RequestModel<UserVO> requestModel) {
        return userService.updatePassword(requestModel);
    }

    @PostMapping("/selectOption")
    @Log(pageTitle = "根据用户名查询")
    public ResponseModel selectOption(@RequestBody RequestModel<UserVO> requestModel){
        return userService.selectOption(requestModel);
    }

    /************************** 以下为跨服务调用方法 ****************************/
    //微服务调用--判断token是否过期, 未过期token，判断用户是否有权限请求接口
    @GetMapping("/getUserAuthByToken")
    @Log(pageTitle = "判断用户是否有权限请求接口")
    public Authentication getUserAuthByToken(@RequestParam("token") String token, @RequestParam("servletPath") String servletPath){
        return userService.getUserAuthByToken(token, servletPath);
    }

    @PostMapping("/queryUserByUserNameOrMail")
    @Log(pageTitle = "根据用户名or邮箱查询用户")
    public ResponseModel queryUserByUserNameOrMail(@RequestBody RequestModel<StringParamVO> requestModel){
        return userService.queryUserByUserNameOrMail(requestModel);
    }

    /************************** 以上为跨服务调用方法 ****************************/

}

