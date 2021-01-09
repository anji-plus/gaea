package com.anji.mirror.auth.service;

import com.anji.mirror.auth.domain.po.SettingPO;
import com.anji.mirror.auth.domain.vo.SettingVO;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-10-10
 */
public interface SettingService extends IService<SettingPO> {

    /** 创建
     * @param requestModel
     * @return
     */
    ResponseModel create(RequestModel<SettingVO> requestModel);

    /** 根据id修改
     * @param requestModel
     * @return
     */
    ResponseModel updateById(RequestModel<SettingVO> requestModel);

    /** 根据id删除
     * @param requestModel
     * @return
     */
    ResponseModel deleteById(RequestModel<SettingVO> requestModel);

    /** 根据id查询一条记录
     * @param requestModel
     * @return
     */
    ResponseModel queryById(RequestModel<SettingVO> requestModel);

    /** 根据settingName查询一条记录
     * @param settingName
     * @return
     */
    SettingVO queryBySettingName(String settingName);

    Integer getIntegerBySettingName(String settingName);

    /** 根据参数分页查询列表
     * @param requestModel
     * @return
     */
    ResponseModel queryByPage(RequestModel<SettingVO> requestModel);

    /** 根据id切换状态
     * @param requestModel
     * @return
     */
    ResponseModel switchEnableById(RequestModel<SettingVO> requestModel);

    ResponseModel queryByName(RequestModel<SettingVO> requestModel);
}
