package com.anji.mirror.auth.service;

import com.anji.mirror.auth.domain.po.OrgPO;
import com.anji.mirror.auth.domain.vo.OrgVO;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 组织机构表 服务类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
public interface OrgService extends IService<OrgPO> {

    /** 创建
     * @param requestModel
     * @return
     */
    ResponseModel create(RequestModel<OrgVO> requestModel);

    /** 根据id修改
     * @param requestModel
     * @return
     */
    ResponseModel updateById(RequestModel<OrgVO> requestModel);

    /** 根据id删除
     * @param requestModel
     * @return
     */
    ResponseModel deleteById(RequestModel<OrgVO> requestModel);

    /** 根据id查询一条记录
     * @param requestModel
     * @return
     */
    ResponseModel queryById(RequestModel<OrgVO> requestModel);

    /** 根据参数分页查询列表
     * @param requestModel
     * @return
     */
    ResponseModel queryByPage(RequestModel<OrgVO> requestModel);

    /**
     * 前端select选择器下拉数据
     * @param requestModel
     * @return
     */
    ResponseModel selectOption(RequestModel<OrgVO> requestModel);
}
