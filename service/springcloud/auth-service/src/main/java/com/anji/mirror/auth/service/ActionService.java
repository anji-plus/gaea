package com.anji.mirror.auth.service;

import com.anji.mirror.auth.domain.po.ActionPO;
import com.anji.mirror.auth.domain.vo.ActionVO;
import com.anji.mirror.auth.domain.vo.RoleMenuActionVO;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 按钮表 服务类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
public interface ActionService extends IService<ActionPO> {

    /** 创建
     * @param requestModel
     * @return
     */
    ResponseModel create(RequestModel<ActionVO> requestModel);

    /** 根据id修改
     * @param requestModel
     * @return
     */
    ResponseModel updateById(RequestModel<ActionVO> requestModel);

    /** 根据id删除
     * @param requestModel
     * @return
     */
    ResponseModel deleteById(RequestModel<ActionVO> requestModel);

    /**
     * 根据 RoleMenuActionVO 删除关联关系
     * 分别删除 角色按钮关联关系  角色菜单按钮关联关系
     * @param roleMenuActionVO
     * @return
     */
    ResponseModel deleteCorrelation(RoleMenuActionVO roleMenuActionVO);

        /** 根据id查询一条记录
         * @param requestModel
         * @return
         */
    ResponseModel queryById(RequestModel<ActionVO> requestModel);

    /** 根据参数分页查询列表
     * @param requestModel
     * @return
     */
    ResponseModel queryByPage(RequestModel<ActionVO> requestModel);

}
