package com.anji.mirror.push.service;

import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anjiplus.gaea.push.domain.po.PushHistoryPO;
import com.anjiplus.gaea.push.domain.vo.PushHistoryVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-10-26
 */
public interface PushHistoryService extends IService<PushHistoryPO> {

    /**
     * 创建
     *
     * @param requestModel
     * @return
     */
    ResponseModel create(RequestModel<PushHistoryVO> requestModel);

    /**
     * 根据id修改
     *
     * @param requestModel
     * @return
     */
    ResponseModel updateById(RequestModel<PushHistoryVO> requestModel);

    /**
     * 根据id删除
     *
     * @param requestModel
     * @return
     */
    ResponseModel deleteById(RequestModel<PushHistoryVO> requestModel);

    /**
     * 根据id查询一条记录
     *
     * @param requestModel
     * @return
     */
    ResponseModel queryById(RequestModel<PushHistoryVO> requestModel);

    /**
     * 根据参数分页查询列表
     *
     * @param requestModel
     * @return
     */
    ResponseModel queryByPage(RequestModel<PushHistoryVO> requestModel);

    /**
     * 三个月复制一次表
     */
    void alertEventArchive();

}
