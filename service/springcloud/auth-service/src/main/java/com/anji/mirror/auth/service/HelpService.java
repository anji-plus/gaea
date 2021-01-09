package com.anji.mirror.auth.service;

import com.anji.mirror.auth.domain.po.HelpPO;
import com.anji.mirror.auth.domain.vo.HelpVO;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.haitong.nla.auth.domain.vo.SerarchConditionVO;


/**
 * <p>
 * 帮助中心表 服务类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-11-23
 */
public interface HelpService extends IService<HelpPO> {

    /** 创建
     * @param requestModel
     * @return
     */
    ResponseModel create(RequestModel<HelpVO> requestModel);

    /** 根据id修改
     * @param requestModel
     * @return
     */
    ResponseModel updateById(RequestModel<HelpVO> requestModel);

    /** 根据id删除
     * @param requestModel
     * @return
     */
    ResponseModel deleteById(RequestModel<HelpVO> requestModel);

    /** 根据id查询一条记录
     * @param requestModel
     * @return
     */
    ResponseModel queryById(RequestModel<HelpVO> requestModel);

    /** 根据参数分页查询列表
     * @param requestModel
     * @return
     */
    ResponseModel queryByPage(RequestModel<HelpVO> requestModel);

    /** 校验标题
     * @param requestModel
     * @return
     */
    ResponseModel titleCheck(RequestModel<HelpVO> requestModel);

    /** 根据分类查询标题列表
     * @param requestModel
     * @return
     */
    ResponseModel querytitleByCategory(RequestModel<HelpVO> requestModel);


    /** 关键词搜索
     * @param requestModel
     * @return
     */
    ResponseModel searchKeyWord(RequestModel<SerarchConditionVO> requestModel);

}
