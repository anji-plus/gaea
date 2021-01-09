package com.anji.mirror.auth.service;

import com.anji.mirror.auth.domain.po.DictPO;
import com.anji.mirror.auth.domain.vo.DictVO;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 数据字典表 服务类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
public interface DictService extends IService<DictPO> {

    /** 创建
     * @param requestModel
     * @return
     */
    ResponseModel create(RequestModel<DictVO> requestModel);

    /** 根据id修改
     * @param requestModel
     * @return
     */
    ResponseModel updateById(RequestModel<DictVO> requestModel);

    /** 根据id删除
     * @param requestModel
     * @return
     */
    ResponseModel deleteById(RequestModel<DictVO> requestModel);

    /** 根据id查询一条记录
     * @param requestModel
     * @return
     */
    ResponseModel queryById(RequestModel<DictVO> requestModel);

    /** 根据参数分页查询列表
     * @param requestModel
     * @return
     */
    ResponseModel queryByPage(RequestModel<DictVO> requestModel);

    /** 前端select组件接口
     * @param requestModel
     * @return
     */
    ResponseModel queryForCodeSelect(RequestModel<DictVO> requestModel);
}
