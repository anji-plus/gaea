package com.anji.mirror.auth.service.impl;

import com.anji.mirror.auth.domain.po.OrgPO;
import com.anji.mirror.auth.domain.vo.OrgVO;
import com.anji.mirror.common.enums.DeleteFlagEnum;
import com.anji.mirror.common.enums.EnableFlagEnum;
import com.anji.mirror.auth.mapper.OrgMapper;
import com.anji.mirror.auth.service.OrgService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anji.mirror.common.enums.RepCodeEnum;
import com.anji.mirror.common.model.OptionVO;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 组织机构表 服务实现类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
@Service
public class OrgServiceImpl extends ServiceImpl<OrgMapper, OrgPO> implements OrgService {

    @Autowired
    private OrgMapper orgMapper;

    /** 根据数据库必填项，校验是否为空，不校验主键
     * @param orgVO
     * @return
     */
    private ResponseModel validateCreateFieldNotNull(OrgVO orgVO){
        if(orgVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        /* 该片段由生成器产生，请根据实际情况修改
        if(orgVO.getOrgId() == null){
            return RepCodeEnum.NULL_ERROR.parseError("orgId");
        }
        if(StringUtils.isBlank(orgVO.getOrgCode())){
            return RepCodeEnum.NULL_ERROR.parseError("orgCode");
        }
        if(StringUtils.isBlank(orgVO.getOrgName())){
            return RepCodeEnum.NULL_ERROR.parseError("orgName");
        }
        if(StringUtils.isBlank(orgVO.getOrgParentCode())){
            return RepCodeEnum.NULL_ERROR.parseError("orgParentCode");
        }
        if(StringUtils.isBlank(orgVO.getOrgParentName())){
            return RepCodeEnum.NULL_ERROR.parseError("orgParentName");
        }
        if(StringUtils.isBlank(orgVO.getOutOrgCode())){
            return RepCodeEnum.NULL_ERROR.parseError("outOrgCode");
        }
        if(StringUtils.isBlank(orgVO.getOutOrgParentCode())){
            return RepCodeEnum.NULL_ERROR.parseError("outOrgParentCode");
        }
        if(StringUtils.isBlank(orgVO.getOrgLevel())){
            return RepCodeEnum.NULL_ERROR.parseError("orgLevel");
        }
        if(StringUtils.isBlank(orgVO.getOrgType())){
            return RepCodeEnum.NULL_ERROR.parseError("orgType");
        }
        if(StringUtils.isBlank(orgVO.getLinkman())){
            return RepCodeEnum.NULL_ERROR.parseError("linkman");
        }
        if(StringUtils.isBlank(orgVO.getMobilePhone())){
            return RepCodeEnum.NULL_ERROR.parseError("mobilePhone");
        }
        if(StringUtils.isBlank(orgVO.getTelephone())){
            return RepCodeEnum.NULL_ERROR.parseError("telephone");
        }
        if(orgVO.getEnableFlag() == null){
            return RepCodeEnum.NULL_ERROR.parseError("enableFlag");
        }
        if(orgVO.getDeleteFlag() == null){
            return RepCodeEnum.NULL_ERROR.parseError("deleteFlag");
        }
        if(StringUtils.isBlank(orgVO.getRemark())){
            return RepCodeEnum.NULL_ERROR.parseError("remark");
        }
        if(StringUtils.isBlank(orgVO.getCreatedBy())){
            return RepCodeEnum.NULL_ERROR.parseError("createdBy");
        }
        if(orgVO.getCreatedTime() == null){
            return RepCodeEnum.NULL_ERROR.parseError("createdTime");
        }
        if(StringUtils.isBlank(orgVO.getUpdatedBy())){
            return RepCodeEnum.NULL_ERROR.parseError("updatedBy");
        }
        if(orgVO.getUpdatedTime() == null){
            return RepCodeEnum.NULL_ERROR.parseError("updatedTime");
        }
        */
        return ResponseModel.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel create(RequestModel<OrgVO> requestModel) {
        //参数校验
        OrgVO orgVO = requestModel.getData();
        ResponseModel valid = validateCreateFieldNotNull(orgVO);
        if(valid.isError()){
            return valid;
        }
        //业务校验
        //...todo

        //“机构代码”应全局判重
        QueryWrapper<OrgPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_code", orgVO.getOrgCode());
        queryWrapper.eq("delete_flag", DeleteFlagEnum.UNDELETED.getCodeValue());
        int count = count(queryWrapper);
        if (count > 0) {
            return ResponseModel.fail("机构代码已存在");
        }

        //业务处理
        String operator = requestModel.getOpUserName();
        if(orgVO.getEnableFlag()==null){
            orgVO.setEnableFlag(EnableFlagEnum.ENABLE.getCodeValue());
        }
        orgVO.setDeleteFlag(DeleteFlagEnum.UNDELETED.getCodeValue());
        orgVO.setCreatedBy(operator);
        orgVO.setCreatedTime(LocalDateTime.now());
        orgVO.setUpdatedBy(operator);
        orgVO.setUpdatedTime(LocalDateTime.now());

        OrgPO orgPO = new OrgPO();
        BeanUtils.copyProperties(orgVO, orgPO);
        boolean flag = save(orgPO);

        //返回结果
        if(flag){
            return ResponseModel.success(orgPO);
        }else{
            return ResponseModel.fail(RepCodeEnum.FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel updateById(RequestModel<OrgVO> requestModel) {
        //参数校验
        OrgVO orgVO = requestModel.getData();
        if(orgVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long orgId = orgVO.getOrgId();
        if(orgId == null){
            return RepCodeEnum.NULL_ERROR.parseError("orgId");
        }
        //业务校验
        //...todo

        //业务处理
        OrgPO orgPO = getById(orgId);
        if(orgPO == null){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("orgId="+orgId.longValue());
        }
        orgVO.setUpdatedBy(requestModel.getOpUserName());
        orgVO.setUpdatedTime(LocalDateTime.now());
        BeanUtils.copyProperties(orgVO, orgPO, true);
        boolean flag = updateById(orgPO);

        //返回结果
        if(flag){
            return ResponseModel.success(orgPO);
        }else{
            return ResponseModel.fail("修改失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel deleteById(RequestModel<OrgVO> requestModel) {
        //参数校验
        OrgVO orgVO = requestModel.getData();
        if(orgVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long orgId = orgVO.getOrgId();
        if(orgId == null){
            return RepCodeEnum.NULL_ERROR.parseError("orgId");
        }

        //业务处理
        OrgPO orgPO = getById(orgId);
        if(orgPO == null){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("orgId="+orgId.longValue());
        }
        boolean flag = removeById(orgId);

        //返回结果
        if(flag){
            return ResponseModel.success("删除成功");
        }else{
            return ResponseModel.fail("删除失败");
        }
    }

    @Override
    public ResponseModel queryById(RequestModel<OrgVO> requestModel) {
        //参数校验
        OrgVO orgVO = requestModel.getData();
        if(orgVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long orgId = orgVO.getOrgId();
        if(orgId == null){
            return RepCodeEnum.NULL_ERROR.parseError("orgId");
        }

        //业务处理
        OrgPO orgPO = getById(orgId);
        if(orgPO == null){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("orgId="+orgId.longValue());
        }

        //返回结果
        BeanUtils.copyProperties(orgPO, orgVO);
        return ResponseModel.success(orgVO);
    }

    @Override
    public ResponseModel queryByPage(RequestModel<OrgVO> requestModel) {
        //参数校验
        OrgVO orgVO = requestModel.getData();
        if(orgVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
//        List<Long> opOrgIdList = requestModel.getOpOrgIdList();
//        orgVO.setOrgIdList(opOrgIdList);

        //分页参数
        Page<OrgVO> page = new Page<OrgVO>(requestModel.getCurrentPage(), requestModel.getPageSize());
        orgVO.setDeleteFlag(DeleteFlagEnum.UNDELETED.getCodeValue());

        //业务处理
        IPage<OrgVO> pageList = orgMapper.queryByPage(page, orgVO);

        //返回结果
        return ResponseModel.success(pageList);
    }

    /**
     * 前端select选择器下拉数据
     *
     * @param requestModel
     * @return
     */
    @Override
    public ResponseModel selectOption(RequestModel<OrgVO> requestModel) {
        //参数校验
        OrgVO orgVO = requestModel.getData();
        if(orgVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }

        //分页参数
        Page<OrgVO> page = new Page<OrgVO>(requestModel.getCurrentPage(), requestModel.getPageSize());
        orgVO.setDeleteFlag(DeleteFlagEnum.UNDELETED.getCodeValue());

        //业务处理
        IPage<OrgVO> pageList = orgMapper.queryByPage(page, orgVO);

        List<OptionVO> collect = pageList.getRecords().stream().map(org -> {
            OptionVO optionVO = new OptionVO();
            optionVO.setLabel(org.getOrgName());
            optionVO.setValue(org.getOrgCode());
            return optionVO;
        }).collect(Collectors.toList());
        //返回结果
        return ResponseModel.success(collect);
    }
}
