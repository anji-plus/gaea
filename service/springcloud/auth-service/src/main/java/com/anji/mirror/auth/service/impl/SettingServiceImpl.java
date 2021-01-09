package com.anji.mirror.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.anji.mirror.auth.domain.po.SettingPO;
import com.anji.mirror.auth.domain.vo.SettingVO;
import com.anji.mirror.common.enums.EnableFlagEnum;
import com.anji.mirror.auth.mapper.SettingMapper;
import com.anji.mirror.auth.service.SettingService;
import com.anji.mirror.common.enums.RepCodeEnum;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.utils.BeanUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-10-10
 */
@Service
public class SettingServiceImpl extends ServiceImpl<SettingMapper, SettingPO> implements SettingService {

    @Autowired
    private SettingMapper settingMapper;

    /** 根据数据库必填项，校验是否为空，不校验主键
     * @param settingVO
     * @return
     */
    private ResponseModel validateCreateFieldNotNull(SettingVO settingVO){
        if(settingVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        /* 该片段由生成器产生，请根据实际情况修改
        if(settingVO.getSettingId() == null){
            return RepCodeEnum.NULL_ERROR.parseError("settingId");
        }
        if(StringUtils.isBlank(settingVO.getSettingName())){
            return RepCodeEnum.NULL_ERROR.parseError("settingName");
        }
        if(StringUtils.isBlank(settingVO.getSettingLabel())){
            return RepCodeEnum.NULL_ERROR.parseError("settingLabel");
        }
        if(StringUtils.isBlank(settingVO.getSettingType())){
            return RepCodeEnum.NULL_ERROR.parseError("settingType");
        }
        if(StringUtils.isBlank(settingVO.getSettingForm())){
            return RepCodeEnum.NULL_ERROR.parseError("settingForm");
        }
        if(StringUtils.isBlank(settingVO.getSettingValue())){
            return RepCodeEnum.NULL_ERROR.parseError("settingValue");
        }
        if(settingVO.getEnableFlag() == null){
            return RepCodeEnum.NULL_ERROR.parseError("enableFlag");
        }
        if(StringUtils.isBlank(settingVO.getRemark())){
            return RepCodeEnum.NULL_ERROR.parseError("remark");
        }
        if(StringUtils.isBlank(settingVO.getCreatedBy())){
            return RepCodeEnum.NULL_ERROR.parseError("createdBy");
        }
        if(settingVO.getCreatedTime() == null){
            return RepCodeEnum.NULL_ERROR.parseError("createdTime");
        }
        if(StringUtils.isBlank(settingVO.getUpdatedBy())){
            return RepCodeEnum.NULL_ERROR.parseError("updatedBy");
        }
        if(settingVO.getUpdatedTime() == null){
            return RepCodeEnum.NULL_ERROR.parseError("updatedTime");
        }
        */
        return ResponseModel.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel create(RequestModel<SettingVO> requestModel) {
        //参数校验
        SettingVO settingVO = requestModel.getData();
        ResponseModel valid = validateCreateFieldNotNull(settingVO);
        if(valid.isError()){
            return valid;
        }
        //业务校验
        if (null != queryBySettingName(settingVO.getSettingName()) ){//存在
            return RepCodeEnum.EXIST_ERROR.parseError("参数名称");
        }
        //业务处理
        String operator = requestModel.getOpUserName();
        if(settingVO.getEnableFlag()==null){
            settingVO.setEnableFlag(EnableFlagEnum.ENABLE.getCodeValue());
        }
        settingVO.setCreatedBy(operator);
        settingVO.setCreatedTime(LocalDateTime.now());
        settingVO.setUpdatedBy(operator);
        settingVO.setUpdatedTime(LocalDateTime.now());

        SettingPO settingPO = new SettingPO();
        BeanUtils.copyProperties(settingVO, settingPO);
        boolean flag = save(settingPO);

        //返回结果
        if(flag){
            return ResponseModel.success(settingPO);
        }else{
            return ResponseModel.fail(RepCodeEnum.FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel updateById(RequestModel<SettingVO> requestModel) {
        //参数校验
        SettingVO settingVO = requestModel.getData();
        if(settingVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long settingId = settingVO.getSettingId();
        if(settingId == null){
            return RepCodeEnum.NULL_ERROR.parseError("settingId");
        }
        //业务校验
        //...todo

        //业务处理
        SettingPO settingPO = getById(settingId);
        if(settingPO == null){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("settingId="+settingId.longValue());
        }
        settingVO.setUpdatedBy(requestModel.getOpUserName());
        settingVO.setUpdatedTime(LocalDateTime.now());
        BeanUtils.copyProperties(settingVO, settingPO, true);
        boolean flag = updateById(settingPO);

        //返回结果
        if(flag){
            return ResponseModel.success(settingPO);
        }else{
            return ResponseModel.fail("修改失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel deleteById(RequestModel<SettingVO> requestModel) {
        //参数校验
        SettingVO settingVO = requestModel.getData();
        if(settingVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long settingId = settingVO.getSettingId();
        if(settingId == null){
            return RepCodeEnum.NULL_ERROR.parseError("settingId");
        }

        //业务处理
        SettingPO settingPO = getById(settingId);
        if(settingPO == null){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("settingId="+settingId.longValue());
        }
        boolean flag = removeById(settingId);

        //返回结果
        if(flag){
            return ResponseModel.success("删除成功");
        }else{
            return ResponseModel.fail("删除失败");
        }
    }

    @Override
    public ResponseModel queryById(RequestModel<SettingVO> requestModel) {
        //参数校验
        SettingVO settingVO = requestModel.getData();
        if(settingVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long settingId = settingVO.getSettingId();
        if(settingId == null){
            return RepCodeEnum.NULL_ERROR.parseError("settingId");
        }

        //业务处理
        SettingPO settingPO = getById(settingId);
        if(settingPO == null){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("settingId="+settingId.longValue());
        }

        //返回结果
        BeanUtils.copyProperties(settingPO, settingVO);
        return ResponseModel.success(settingVO);
    }

    @Override
    public SettingVO queryBySettingName(String settingName){
        if(StringUtils.isBlank(settingName)){
            return null;
        }
        //根据参数名的查询
        QueryWrapper<SettingPO> wrapper = Wrappers.query();
        wrapper.eq("setting_name", settingName);
        SettingPO settingPO = settingMapper.selectOne(wrapper);
        if(settingPO == null){
            return null;
        }
        //返回结果
        SettingVO settingVO = new SettingVO();
        BeanUtils.copyProperties(settingPO, settingVO);
        return settingVO;
    }

    @Override
    public Integer getIntegerBySettingName(String settingName) {
        SettingVO settingVO=queryBySettingName(settingName);
        if(settingVO==null)
            return null;
        return settingVO.getIntSettingValue();
    }

    @Override
    public ResponseModel queryByPage(RequestModel<SettingVO> requestModel) {
        //参数校验
        SettingVO settingVO = requestModel.getData();
        if(settingVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        //...todo

        //分页参数
        Page<SettingVO> page = new Page<SettingVO>(requestModel.getCurrentPage(), requestModel.getPageSize());

        //业务处理
        IPage<SettingVO> pageList = settingMapper.queryByPage(page, settingVO);

        //返回结果
        return ResponseModel.success(pageList);
    }

    @Override
    public ResponseModel switchEnableById(RequestModel<SettingVO> requestModel) {
        //参数校验
        SettingVO settingVO = requestModel.getData();
        if(settingVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        //...todo
        if(settingVO.getSettingId() == null){
            return RepCodeEnum.NULL_ERROR.parseError("settingId");
        }

        SettingPO settingPO = settingMapper.selectById(settingVO.getSettingId());
        if(settingPO == null){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("Id"+settingVO.getSettingId());
        }

        if(settingPO.getEnableFlag() == EnableFlagEnum.ENABLE.getCodeValue()){
            settingPO.setEnableFlag(EnableFlagEnum.DISABLE.getCodeValue());
        }else{
            settingPO.setEnableFlag(EnableFlagEnum.ENABLE.getCodeValue());
        }
        settingMapper.updateById(settingPO);

        SettingVO result = new SettingVO();
        BeanUtils.copyProperties(settingPO, result);
        return ResponseModel.success(result);
    }

    @Override
    public ResponseModel queryByName(RequestModel<SettingVO> requestModel) {
        //参数校验
        SettingVO settingVO = requestModel.getData();
        if(settingVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        if (StringUtils.isBlank(settingVO.getSettingName())) {
            return RepCodeEnum.NULL_ERROR.parseError("参数名称");
        }
        return ResponseModel.success(queryBySettingName(settingVO.getSettingName()));
    }
}
