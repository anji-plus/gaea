package com.anji.mirror.auth.service;

import com.anji.mirror.auth.domain.po.RolePO;
import com.anji.mirror.auth.domain.vo.RoleVO;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
public interface RoleService extends IService<RolePO> {

    /** 创建
     * @param requestModel
     * @return
     */
    ResponseModel create(RequestModel<RoleVO> requestModel);

    /** 根据id修改
     * @param requestModel
     * @return
     */
    ResponseModel updateById(RequestModel<RoleVO> requestModel);

    /** 根据id删除
     * @param requestModel
     * @return
     */
    ResponseModel deleteById(RequestModel<RoleVO> requestModel);

    /** 根据id查询一条记录
     * @param requestModel
     * @return
     */
    ResponseModel queryById(RequestModel<RoleVO> requestModel);

    /** 根据参数分页查询列表
     * @param requestModel
     * @return
     */
    ResponseModel queryByPage(RequestModel<RoleVO> requestModel);

    /** 查询角色的菜单按钮树
     * @param requestModel
     * @return
     */
    ResponseModel queryMenuActionTreeForRole(RequestModel<RoleVO> requestModel);

    /** 保存角色的菜单按钮树
     * @param requestModel
     * @return
     */
    ResponseModel saveMenuActionTreeForRole(RequestModel<RoleVO> requestModel);


    /** 查询角色关联的组织机构
     * @param requestModel
     * @return
     */
    ResponseModel queryOrgTreeForRole(RequestModel<RoleVO> requestModel);

    /**
     * 查询用户角色关联的组织机构
     * @param requestModel
     * @return
     */
    ResponseModel queryUserOrgTreeForRole(RequestModel<RoleVO> requestModel);

    /** 保存角色关联的组织机构
     * @param requestModel
     * @return
     */
    ResponseModel saveOrgTreeForRole(RequestModel<RoleVO> requestModel);

    /**
     * 保存用户角色关联的组织机构
     * @param requestModel
     * @return
     */
    ResponseModel saveOrgTreeForUserRole(RequestModel<RoleVO> requestModel);
}
