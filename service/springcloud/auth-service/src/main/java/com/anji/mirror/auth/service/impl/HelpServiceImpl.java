package com.anji.mirror.auth.service.impl;

import com.anji.mirror.auth.domain.po.HelpPO;
import com.anji.mirror.auth.domain.vo.HelpVO;
import com.anji.mirror.common.enums.EnableFlagEnum;
import com.anji.mirror.auth.enums.RepCodeEnum;
import com.anji.mirror.auth.mapper.HelpMapper;
import com.anji.mirror.auth.service.HelpService;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.utils.BeanUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haitong.nla.auth.domain.vo.SerarchConditionVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 帮助中心表 服务实现类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-11-23
 */
@Service
public class HelpServiceImpl extends ServiceImpl<HelpMapper, HelpPO> implements HelpService {

    @Autowired
    private HelpMapper helpMapper;

    /** 根据数据库必填项，校验是否为空，不校验主键
     * @param helpVO
     * @return
     */
    private ResponseModel validateCreateFieldNotNull(HelpVO helpVO){
        if(helpVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        if(StringUtils.isBlank(helpVO.getHelpCategory())){
            return RepCodeEnum.NULL_ERROR.parseError("help_category");
        }
        if(StringUtils.isBlank(helpVO.getHelpTitle())){
            return RepCodeEnum.NULL_ERROR.parseError("help_title");
        }
        if(StringUtils.isBlank(helpVO.getHelpContent())){
            return RepCodeEnum.NULL_ERROR.parseError("help_content");
        }
        if(helpVO.getSort() == null){
            return RepCodeEnum.NULL_ERROR.parseError("sort");
        }

        return ResponseModel.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel create(RequestModel<HelpVO> requestModel) {
        //参数校验
        HelpVO helpVO = requestModel.getData();
        ResponseModel valid = validateCreateFieldNotNull(helpVO);
        if(valid.isError()){
            return valid;
        }
        //业务校验
        //1、不允许重复标题
        HelpVO queryVO = new HelpVO();
        queryVO.setHelpCategory(helpVO.getHelpCategory());
        queryVO.setHelpTitle(helpVO.getHelpTitle().trim());
        List<HelpVO> helpVOS = helpMapper.queryByCondition(queryVO);
        if (!CollectionUtils.isEmpty(helpVOS)) {
            return RepCodeEnum.TITLE_EXISTS_ERROR.parseError(helpVO.getHelpTitle().trim());
        }


        //业务处理
        String operator = requestModel.getOpUserName();
        if(helpVO.getEnableFlag()==null){
            helpVO.setEnableFlag(EnableFlagEnum.ENABLE.getCodeValue());
        }
        helpVO.setCreatedBy(operator);
        helpVO.setCreatedTime(LocalDateTime.now());
        helpVO.setUpdatedBy(operator);
        helpVO.setUpdatedTime(LocalDateTime.now());

        HelpPO helpPO = new HelpPO();
        BeanUtils.copyProperties(helpVO, helpPO);
        boolean flag = save(helpPO);

        //返回结果
        if(flag){
            return ResponseModel.success(helpPO);
        }else{
            return ResponseModel.fail(RepCodeEnum.FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel updateById(RequestModel<HelpVO> requestModel) {
        //参数校验
        HelpVO helpVO = requestModel.getData();
        if(helpVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long helpId = helpVO.getHelpId();
        if(helpId == null){
            return RepCodeEnum.NULL_ERROR.parseError("helpId");
        }
        //业务校验
        //1、不允许重复标题
        HelpVO queryVO = new HelpVO();
        queryVO.setHelpCategory(helpVO.getHelpCategory());
        queryVO.setHelpTitle(helpVO.getHelpTitle().trim());
        List<HelpVO> helpVOS = helpMapper.queryByCondition(queryVO);
        if (!CollectionUtils.isEmpty(helpVOS)) {
            for(HelpVO helpVO1 : helpVOS) {
                if (!helpVO1.getHelpId().equals(helpVO.getHelpId())) {
                    return RepCodeEnum.TITLE_EXISTS_ERROR.parseError(helpVO.getHelpTitle().trim());
                }
            }
        }

        //业务处理
        HelpPO helpPO = getById(helpId);
        if(helpPO == null){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("helpId="+helpId.longValue());
        }
        String operator = requestModel.getOpUserName();
        helpVO.setUpdatedBy(operator);
        helpVO.setUpdatedTime(LocalDateTime.now());
        BeanUtils.copyProperties(helpVO, helpPO, true);
        boolean flag = updateById(helpPO);

        //返回结果
        if(flag){
            return ResponseModel.success(helpPO);
        }else{
            return ResponseModel.fail("修改失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel deleteById(RequestModel<HelpVO> requestModel) {
        //参数校验
        HelpVO helpVO = requestModel.getData();
        if(helpVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long helpId = helpVO.getHelpId();
        if(helpId == null){
            return RepCodeEnum.NULL_ERROR.parseError("helpId");
        }

        //业务处理
        HelpPO helpPO = getById(helpId);
        if(helpPO == null){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("helpId="+helpId.longValue());
        }
        boolean flag = removeById(helpId);

        //返回结果
        if(flag){
            return ResponseModel.success("删除成功");
        }else{
            return ResponseModel.fail("删除失败");
        }
    }

    @Override
    public ResponseModel queryById(RequestModel<HelpVO> requestModel) {
        //参数校验
        HelpVO helpVO = requestModel.getData();
        if(helpVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long helpId = helpVO.getHelpId();
        if(helpId == null){
            return RepCodeEnum.NULL_ERROR.parseError("helpId");
        }

        //业务处理
        HelpPO helpPO = getById(helpId);
        if(helpPO == null){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("helpId="+helpId.longValue());
        }

        //返回结果
        BeanUtils.copyProperties(helpPO, helpVO);
        return ResponseModel.success(helpVO);
    }

    @Override
    public ResponseModel queryByPage(RequestModel<HelpVO> requestModel) {
        //参数校验
        HelpVO helpVO = requestModel.getData();
        if(helpVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        //...todo

        //分页参数
        Page<HelpVO> page = new Page<HelpVO>(requestModel.getCurrentPage(), requestModel.getPageSize());

        //业务处理
        IPage<HelpVO> pageList = helpMapper.queryByPage(page, helpVO);

        //返回结果
        return ResponseModel.success(pageList);
    }

    @Override
    public ResponseModel titleCheck(RequestModel<HelpVO> requestModel) {
        //参数校验
        HelpVO helpVO = requestModel.getData();
        if(helpVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }

        //不允许重复标题
        HelpVO queryVO = new HelpVO();
        queryVO.setHelpCategory(helpVO.getHelpCategory());
        queryVO.setHelpTitle(helpVO.getHelpTitle().trim());
        List<HelpVO> helpVOS = helpMapper.queryByCondition(queryVO);
        if (!CollectionUtils.isEmpty(helpVOS)) {
            return RepCodeEnum.TITLE_EXISTS_ERROR.parseError(helpVO.getHelpTitle().trim());
        }

        //返回结果
        return ResponseModel.success(null);
    }

    @Override
    public ResponseModel searchKeyWord(RequestModel<SerarchConditionVO> requestModel) {
        //参数校验
        SerarchConditionVO serarchConditionVO = requestModel.getData();
        if(serarchConditionVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }

        //分页参数
        Page<HelpVO> page = new Page<HelpVO>(requestModel.getCurrentPage(), requestModel.getPageSize());

        //业务处理
        IPage<HelpVO> pageList = helpMapper.searchKeyWord(page, serarchConditionVO);

        //返回结果
        return ResponseModel.success(pageList);
    }

    @Override
    public ResponseModel querytitleByCategory(RequestModel<HelpVO> requestModel) {
        //参数校验
        HelpVO helpVO = requestModel.getData();
        if(helpVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        if (StringUtils.isEmpty(helpVO.getHelpCategory())) {
            return RepCodeEnum.NULL_ERROR.parseError("helpCategory");
        }

        //业务处理
        helpVO.setEnableFlag(EnableFlagEnum.ENABLE.getCodeValue());
        List<HelpVO> helpVOS = helpMapper.queryByCondition(helpVO);

        //返回结果
        return ResponseModel.success(helpVOS);
    }

}
