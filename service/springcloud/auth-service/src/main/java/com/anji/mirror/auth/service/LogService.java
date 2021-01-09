package com.anji.mirror.auth.service;

import com.anji.mirror.auth.domain.po.LogPO;
import com.anji.mirror.common.model.LogVO;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 用户操作日志表 服务类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
public interface LogService extends IService<LogPO> {

    /** 创建
     * @param requestModel
     * @return
     */
    ResponseModel create(RequestModel<LogVO> requestModel);

    /** 根据id修改
     * @param requestModel
     * @return
     */
    ResponseModel updateById(RequestModel<LogVO> requestModel);

    /** 根据id删除
     * @param requestModel
     * @return
     */
    ResponseModel deleteById(RequestModel<LogVO> requestModel);

    /** 根据id查询一条记录
     * @param requestModel
     * @return
     */
    ResponseModel queryById(RequestModel<LogVO> requestModel);

    /** 根据参数分页查询列表
     * @param requestModel
     * @return
     */
    ResponseModel queryByPage(RequestModel<LogVO> requestModel);

    /** 微服务调用--所有接口进来后保存操作日志
     * @param logVO
     */
    void saveOperatorLog(LogVO logVO);
}
