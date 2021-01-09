package com.anji.mirror.auth.service;

import com.anji.mirror.auth.domain.po.ExportPO;
import com.anji.mirror.common.model.ExportVO;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 导出中心 服务类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-11-23
 */
public interface ExportService extends IService<ExportPO> {

    /** 创建
     * @param requestModel
     * @return
     */
    ResponseModel create(RequestModel<ExportVO> requestModel);

    /** 根据id修改
     * @param requestModel
     * @return
     */
    ResponseModel updateById(RequestModel<ExportVO> requestModel);

    /** 根据id删除
     * @param requestModel
     * @return
     */
    ResponseModel deleteById(RequestModel<ExportVO> requestModel);

    /** 根据id查询一条记录
     * @param requestModel
     * @return
     */
    ResponseModel queryById(RequestModel<ExportVO> requestModel);

    /** 根据参数分页查询列表
     * @param requestModel
     * @return
     */
    ResponseModel queryByPage(RequestModel<ExportVO> requestModel);

    /**
     * 导出(PDF或者excel)
     * 简单的导出和模板导出
     *
     * @param exportVO
     * @return
     */
    boolean export(ExportVO exportVO);

}
