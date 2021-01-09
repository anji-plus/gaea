package com.anji.mirror.auth.service;

import com.anji.mirror.auth.domain.po.UserPO;
import com.anji.mirror.auth.domain.vo.StringParamVO;
import com.anji.mirror.auth.domain.vo.UserVO;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.security.Authentication;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
public interface UserService extends IService<UserPO> {

    /** 创建
     * @param requestModel
     * @return
     */
    ResponseModel create(RequestModel<UserVO> requestModel);

    /** 根据id修改
     * @param requestModel
     * @return
     */
    ResponseModel updateById(RequestModel<UserVO> requestModel);

    /** 根据id删除
     * @param requestModel
     * @return
     */
    ResponseModel deleteById(RequestModel<UserVO> requestModel);

    /** 根据id查询一条记录
     * @param requestModel
     * @return
     */
    ResponseModel queryById(RequestModel<UserVO> requestModel);

    /** 根据参数分页查询列表
     * @param requestModel
     * @return
     */
    ResponseModel queryByPage(RequestModel<UserVO> requestModel);

    /** 用户登录
     * @param requestModel
     * @return
     */
    ResponseModel login(RequestModel<UserVO> requestModel);

    /** 用户退出
     * @param requestModel
     * @return
     */
    ResponseModel logout(RequestModel<UserVO> requestModel);

    /**
     * 用户分配组织角色
     * @param requestModel
     * @return
     */
    ResponseModel queryRoleTree(RequestModel<UserVO> requestModel);

    /**
     *
     * @param requestModel
     * @return
     */
    ResponseModel saveRoleTree(RequestModel<UserVO> requestModel);


    /************************** 以下为容器内调用方法 ****************************/
    /**
     * 查询用户的信息及权限
     *
     * @param userId
     * @return
     */
    //UserVO queryById(Long userId);

    void refreshUserCache(Long userId);
    /************************** 以上为容器内调用方法 ****************************/



    /************************** 以下为跨容器调用方法 ****************************/
    /** 判断token是否过期, 未过期token，判断用户是否有权限请求接口
     * @param token
     * @param servletPath
     * @return
     */
    Authentication getUserAuthByToken(String token, String servletPath);

    ResponseModel queryUserByUserNameOrMail(RequestModel<StringParamVO> requestModel);


    /**
     * 用户自己根据旧密码修改密码
     * @param requestModel
     * @return
     */
    ResponseModel updatePassword(RequestModel<UserVO> requestModel);

    /**
     *根据用户名查询
     * @param requestModel
     * @return
     */
    ResponseModel selectOption(RequestModel<UserVO> requestModel);

    /************************** 以上为跨容器调用方法 ****************************/
}
