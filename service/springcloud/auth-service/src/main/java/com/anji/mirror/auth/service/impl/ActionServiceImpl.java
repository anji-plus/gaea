package com.anji.mirror.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.anji.mirror.auth.domain.po.ActionPO;
import com.anji.mirror.auth.domain.po.MenuActionPO;
import com.anji.mirror.auth.domain.po.RoleMenuActionPO;
import com.anji.mirror.auth.domain.vo.ActionVO;
import com.anji.mirror.auth.domain.vo.RoleMenuActionVO;
import com.anji.mirror.common.enums.DeleteFlagEnum;
import com.anji.mirror.common.enums.EnableFlagEnum;
import com.anji.mirror.auth.mapper.ActionMapper;
import com.anji.mirror.auth.mapper.MenuActionMapper;
import com.anji.mirror.auth.mapper.RoleMenuActionMapper;
import com.anji.mirror.auth.service.ActionService;
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
import java.util.Objects;

/**
 * <p>
 * 按钮表 服务实现类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
@Service
public class ActionServiceImpl extends ServiceImpl<ActionMapper, ActionPO> implements ActionService {

    @Autowired
    private ActionMapper actionMapper;

    @Autowired
    private MenuActionMapper menuActionMapper;

    @Autowired
    private RoleMenuActionMapper roleMenuActionMapper;

    /** 根据数据库必填项，校验是否为空，不校验主键
     * @param actionVO
     * @return
     */
    private ResponseModel validateCreateFieldNotNull(ActionVO actionVO){
        if(actionVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        /* 该片段由生成器产生，请根据实际情况修改
        if(actionVO.getActionId() == null){
            return RepCodeEnum.NULL_ERROR.parseError("actionId");
        }
        if(StringUtils.isBlank(actionVO.getActionCode())){
            return RepCodeEnum.NULL_ERROR.parseError("actionCode");
        }
        if(StringUtils.isBlank(actionVO.getActionName())){
            return RepCodeEnum.NULL_ERROR.parseError("actionName");
        }
        if(actionVO.getSort() == null){
            return RepCodeEnum.NULL_ERROR.parseError("sort");
        }
        if(actionVO.getEnableFlag() == null){
            return RepCodeEnum.NULL_ERROR.parseError("enableFlag");
        }
        if(actionVO.getDeleteFlag() == null){
            return RepCodeEnum.NULL_ERROR.parseError("deleteFlag");
        }
        if(StringUtils.isBlank(actionVO.getCreatedBy())){
            return RepCodeEnum.NULL_ERROR.parseError("createdBy");
        }
        if(actionVO.getCreatedTime() == null){
            return RepCodeEnum.NULL_ERROR.parseError("createdTime");
        }
        if(StringUtils.isBlank(actionVO.getUpdatedBy())){
            return RepCodeEnum.NULL_ERROR.parseError("updatedBy");
        }
        if(actionVO.getUpdatedTime() == null){
            return RepCodeEnum.NULL_ERROR.parseError("updatedTime");
        }
        */
        return ResponseModel.success();
    }

    public ActionVO queryByVerify(String name){
        if(StringUtils.isBlank(name)){
            return null;
        }
        //根据参数名的查询
        QueryWrapper<ActionPO> wrapper = Wrappers.query();
        wrapper.eq("action_code", name);
        ActionPO po = actionMapper.selectOne(wrapper);
        if(po == null){
            return null;
        }
        //返回结果
        ActionVO vo = new ActionVO();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel create(RequestModel<ActionVO> requestModel) {
        //参数校验
        ActionVO actionVO = requestModel.getData();
        ResponseModel valid = validateCreateFieldNotNull(actionVO);
        if(valid.isError()){
            return valid;
        }
        //业务校验
        if (null != queryByVerify(actionVO.getActionCode()) ){//存在
            return RepCodeEnum.EXIST_ERROR.parseError("按钮代码");
        }

        //业务处理
        String operator = requestModel.getOpUserName();
        if(actionVO.getEnableFlag()==null){
            actionVO.setEnableFlag(EnableFlagEnum.ENABLE.getCodeValue());
        }
        actionVO.setDeleteFlag(DeleteFlagEnum.UNDELETED.getCodeValue());
        actionVO.setCreatedBy(operator);
        actionVO.setCreatedTime(LocalDateTime.now());
        actionVO.setUpdatedBy(operator);
        actionVO.setUpdatedTime(LocalDateTime.now());

        ActionPO actionPO = new ActionPO();
        BeanUtils.copyProperties(actionVO, actionPO);
        boolean flag = save(actionPO);

        //返回结果
        if(flag){
            return ResponseModel.success(actionPO);
        }else{
            return ResponseModel.fail(RepCodeEnum.FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel updateById(RequestModel<ActionVO> requestModel) {
        //参数校验
        ActionVO actionVO = requestModel.getData();
        if(actionVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long actionId = actionVO.getActionId();
        if(actionId == null){
            return RepCodeEnum.NULL_ERROR.parseError("actionId");
        }
        //业务校验
        //...todo

        //业务处理
        ActionPO actionPO = getById(actionId);
        if(actionPO == null){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("actionId="+actionId.longValue());
        }
        actionVO.setUpdatedBy(requestModel.getOpUserName());
        actionVO.setUpdatedTime(LocalDateTime.now());
        BeanUtils.copyProperties(actionVO, actionPO, true);
        boolean flag = updateById(actionPO);

        //返回结果
        if(flag){
            if (actionPO.getEnableFlag() == EnableFlagEnum.DISABLE.getCodeValue()){
                RoleMenuActionVO roleMenuActionVO = new RoleMenuActionVO();
                roleMenuActionVO.setActionId(actionPO.getActionId());
                deleteCorrelation(roleMenuActionVO);
            }
            return ResponseModel.success(actionPO);
        }else{
            return ResponseModel.fail("修改失败");
        }
    }

    //correlation 删除关联关系
    public ResponseModel deleteCorrelation(RoleMenuActionVO roleMenuActionVO){
        if (Objects.isNull(roleMenuActionVO)){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("数据");
        }
        //清除菜单的旧关联按钮
        QueryWrapper<MenuActionPO> menuQueryWrapper = new QueryWrapper<MenuActionPO>();
        if (Objects.nonNull(roleMenuActionVO.getMenuId()) && roleMenuActionVO.getMenuId()>0){
            menuQueryWrapper.eq("menu_id", roleMenuActionVO.getMenuId());
        }
        if (Objects.nonNull(roleMenuActionVO.getActionId()) && roleMenuActionVO.getActionId()>0){
            menuQueryWrapper.eq("action_id", roleMenuActionVO.getActionId());
        }
        int delete = menuActionMapper.delete(menuQueryWrapper);
        if (delete <1){
            return ResponseModel.fail("按钮菜单关联关系修改失败");
        }

        //根据menuId清除t_role_menu_action的已勾选的actionIds
        QueryWrapper<RoleMenuActionPO> roleMenuActionQueryWrapper = new QueryWrapper<>();
        if (Objects.nonNull(roleMenuActionVO.getMenuId()) && roleMenuActionVO.getMenuId()>0){
            roleMenuActionQueryWrapper.eq("menu_id", roleMenuActionVO.getMenuId());
        }
        if (Objects.nonNull(roleMenuActionVO.getActionId()) && roleMenuActionVO.getActionId()>0){
            roleMenuActionQueryWrapper.eq("action_id", roleMenuActionVO.getActionId());
        }
        if (Objects.nonNull(roleMenuActionVO.getRoleId()) && roleMenuActionVO.getRoleId()>0){
            roleMenuActionQueryWrapper.eq("role_id", roleMenuActionVO.getRoleId());
        }
        int delete1 = roleMenuActionMapper.delete(roleMenuActionQueryWrapper);
        if (delete1 <1){
            return ResponseModel.fail("按钮菜单角色关联关系修改失败");
        }

        return ResponseModel.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel deleteById(RequestModel<ActionVO> requestModel) {
        //参数校验
        ActionVO actionVO = requestModel.getData();
        if(actionVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long actionId = actionVO.getActionId();
        if(actionId == null){
            return RepCodeEnum.NULL_ERROR.parseError("actionId");
        }

        //业务处理
        ActionPO actionPO = getById(actionId);
        if(actionPO == null){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("actionId="+actionId.longValue());
        }
        boolean flag = removeById(actionId);

        //返回结果
        if(flag){
            RoleMenuActionVO roleMenuActionVO = new RoleMenuActionVO();
            roleMenuActionVO.setActionId(actionPO.getActionId());
            deleteCorrelation(roleMenuActionVO);
            return ResponseModel.success("删除成功");
        }else{
            return ResponseModel.fail("删除失败");
        }
    }


    @Override
    public ResponseModel queryById(RequestModel<ActionVO> requestModel) {
        //参数校验
        ActionVO actionVO = requestModel.getData();
        if(actionVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long actionId = actionVO.getActionId();
        if(actionId == null){
            return RepCodeEnum.NULL_ERROR.parseError("actionId");
        }

        //业务处理
        ActionPO actionPO = getById(actionId);
        if(actionPO == null){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("actionId="+actionId.longValue());
        }

        //返回结果
        BeanUtils.copyProperties(actionPO, actionVO);
        return ResponseModel.success(actionVO);
    }

    @Override
    public ResponseModel queryByPage(RequestModel<ActionVO> requestModel) {
        //参数校验
        ActionVO actionVO = requestModel.getData();
        if(actionVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        //...todo

        //分页参数
        Page<ActionVO> page = new Page<ActionVO>(requestModel.getCurrentPage(), requestModel.getPageSize());
        actionVO.setDeleteFlag(DeleteFlagEnum.UNDELETED.getCodeValue());

        //业务处理
        IPage<ActionVO> pageList = actionMapper.queryByPage(page, actionVO);

        //返回结果
        return ResponseModel.success(pageList);
    }
}
