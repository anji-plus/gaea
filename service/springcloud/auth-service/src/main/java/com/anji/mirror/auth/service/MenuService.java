package com.anji.mirror.auth.service;

import com.anji.mirror.auth.domain.po.MenuPO;
import com.anji.mirror.auth.domain.vo.MenuVO;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
public interface MenuService extends IService<MenuPO> {

    /** 创建
     * @param requestModel
     * @return
     */
    ResponseModel create(RequestModel<MenuVO> requestModel);

    /** 根据id修改
     * @param requestModel
     * @return
     */
    ResponseModel updateById(RequestModel<MenuVO> requestModel);

    /** 根据id删除
     * @param requestModel
     * @return
     */
    ResponseModel deleteById(RequestModel<MenuVO> requestModel);

    /** 根据id查询一条记录
     * @param requestModel
     * @return
     */
    ResponseModel queryById(RequestModel<MenuVO> requestModel);

    /** 根据参数分页查询列表
     * @param requestModel
     * @return
     */
    ResponseModel queryByPage(RequestModel<MenuVO> requestModel);

    /** 查询菜单的关联按钮树
     * @param requestModel
     * @return
     */
    ResponseModel queryActionTreeForMenu(RequestModel<MenuVO> requestModel);

    /** 保存菜单的关联按钮树
     * @param requestModel
     * @return
     */
    ResponseModel saveActionTreeForMenu(RequestModel<MenuVO> requestModel);

    /************************** 以下为容器内调用方法 ****************************/
    /** 查询用户userId，有哪些组织(map.val)的权限码(map.key)
     * @param userId
     * @return
     * {
     *     "accessWithOrgIds": Map<String, List<Long>>,
     *     "accessWithOrgCodes": Map<String, List<String>>{
     *         "authManage:find": [1,2,3]
     *     }
     * }
     */
    Map<String, Map> queryMenuActionCodeByUserId(Long userId);
    /************************** 以上为容器内调用方法 ****************************/
}
