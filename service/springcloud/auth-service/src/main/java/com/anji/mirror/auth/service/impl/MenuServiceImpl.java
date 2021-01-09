package com.anji.mirror.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.anji.mirror.auth.service.OrgService;
import com.anji.mirror.auth.domain.po.ActionPO;
import com.anji.mirror.auth.domain.po.MenuActionPO;
import com.anji.mirror.auth.domain.po.MenuPO;
import com.anji.mirror.auth.domain.po.RoleMenuActionPO;
import com.anji.mirror.auth.domain.vo.MenuVO;
import com.anji.mirror.auth.domain.vo.UserRoleOrgVO;
import com.anji.mirror.common.enums.DeleteFlagEnum;
import com.anji.mirror.common.enums.EnableFlagEnum;
import com.anji.mirror.auth.mapper.MenuActionMapper;
import com.anji.mirror.auth.mapper.MenuMapper;
import com.anji.mirror.auth.mapper.RoleMenuActionMapper;
import com.anji.mirror.auth.service.ActionService;
import com.anji.mirror.auth.service.MenuService;
import com.anji.mirror.common.enums.RepCodeEnum;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.model.TreeVO;
import com.anji.mirror.common.security.Constant;
import com.anji.mirror.common.utils.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuPO> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private OrgService orgService;

    @Autowired
    private ActionService actionService;

    @Autowired
    private MenuActionMapper menuActionMapper;

    @Autowired
    private RoleMenuActionMapper roleMenuActionMapper;

    /** 根据数据库必填项，校验是否为空，不校验主键
     * @param menuVO
     * @return
     */
    private ResponseModel validateCreateFieldNotNull(MenuVO menuVO){
        if(menuVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        if(StringUtils.isBlank(menuVO.getMenuCode())){
            return RepCodeEnum.NULL_ERROR.parseError("菜单代码");
        }
        if(StringUtils.isBlank(menuVO.getMenuName())){
            return RepCodeEnum.NULL_ERROR.parseError("菜单名称");
        }
        /* 该片段由生成器产生，请根据实际情况修改
        if(menuVO.getMenuId() == null){
            return RepCodeEnum.NULL_ERROR.parseError("menuId");
        }
        if(StringUtils.isBlank(menuVO.getMenuCode())){
            return RepCodeEnum.NULL_ERROR.parseError("menuCode");
        }
        if(StringUtils.isBlank(menuVO.getMenuName())){
            return RepCodeEnum.NULL_ERROR.parseError("menuName");
        }
        if(StringUtils.isBlank(menuVO.getSysCode())){
            return RepCodeEnum.NULL_ERROR.parseError("sysCode");
        }
        if(menuVO.getParentId() == null){
            return RepCodeEnum.NULL_ERROR.parseError("parentId");
        }
        if(StringUtils.isBlank(menuVO.getMenuUrl())){
            return RepCodeEnum.NULL_ERROR.parseError("menuUrl");
        }
        if(StringUtils.isBlank(menuVO.getMenuIcon())){
            return RepCodeEnum.NULL_ERROR.parseError("menuIcon");
        }
        if(menuVO.getSort() == null){
            return RepCodeEnum.NULL_ERROR.parseError("sort");
        }
        if(menuVO.getEnableFlag() == null){
            return RepCodeEnum.NULL_ERROR.parseError("enableFlag");
        }
        if(menuVO.getDeleteFlag() == null){
            return RepCodeEnum.NULL_ERROR.parseError("deleteFlag");
        }
        if(StringUtils.isBlank(menuVO.getCreatedBy())){
            return RepCodeEnum.NULL_ERROR.parseError("createdBy");
        }
        if(menuVO.getCreatedTime() == null){
            return RepCodeEnum.NULL_ERROR.parseError("createdTime");
        }
        if(StringUtils.isBlank(menuVO.getUpdatedBy())){
            return RepCodeEnum.NULL_ERROR.parseError("updatedBy");
        }
        if(menuVO.getUpdatedTime() == null){
            return RepCodeEnum.NULL_ERROR.parseError("updatedTime");
        }
        */
        return ResponseModel.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel create(RequestModel<MenuVO> requestModel) {
        //参数校验
        MenuVO menuVO = requestModel.getData();
        ResponseModel valid = validateCreateFieldNotNull(menuVO);
        if(valid.isError()){
            return valid;
        }
        //业务校验
        //...todo

        //业务处理
        String operator = requestModel.getOpUserName();
        if(menuVO.getEnableFlag()==null){
            menuVO.setEnableFlag(EnableFlagEnum.ENABLE.getCodeValue());
        }
        menuVO.setDeleteFlag(DeleteFlagEnum.UNDELETED.getCodeValue());
        menuVO.setCreatedBy(operator);
        menuVO.setCreatedTime(LocalDateTime.now());
        menuVO.setUpdatedBy(operator);
        menuVO.setUpdatedTime(LocalDateTime.now());


        MenuPO menuPO = new MenuPO();
        BeanUtils.copyProperties(menuVO, menuPO);
        MenuPO menu = getById(menuPO);
        QueryWrapper<MenuPO> selectMapper = new QueryWrapper<>();
        selectMapper.eq("menu_code", menuPO.getMenuCode());
        List<MenuPO> poList = menuMapper.selectList(selectMapper);
        if (poList.size()>0){
            return RepCodeEnum.EXIST_ERROR.parseError("菜单代码");
        }
        boolean flag = save(menuPO);
        //返回结果
        if(flag){
            return ResponseModel.success(menuPO);
        }else{
            return ResponseModel.fail(RepCodeEnum.FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel updateById(RequestModel<MenuVO> requestModel) {
        //参数校验
        MenuVO menuVO = requestModel.getData();
        if(menuVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long menuId = menuVO.getMenuId();
        if(menuId == null){
            return RepCodeEnum.NULL_ERROR.parseError("menuId");
        }
        //业务校验
        //...todo

        //业务处理
        MenuPO menuPO = getById(menuId);
        if(menuPO == null){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("menuId="+menuId.longValue());
        }
        menuVO.setUpdatedBy(requestModel.getOpUserName());
        menuVO.setUpdatedTime(LocalDateTime.now());
        BeanUtils.copyProperties(menuVO, menuPO, true);
        boolean flag = updateById(menuPO);

        //返回结果
        if(flag){
            return ResponseModel.success(menuPO);
        }else{
            return ResponseModel.fail("修改失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel deleteById(RequestModel<MenuVO> requestModel) {
        //参数校验
        MenuVO menuVO = requestModel.getData();
        if(menuVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long menuId = menuVO.getMenuId();
        if(menuId == null){
            return RepCodeEnum.NULL_ERROR.parseError("menuId");
        }

        //业务处理
        MenuPO menuPO = getById(menuId);
        if(menuPO == null){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("menuId="+menuId.longValue());
        }
        boolean flag = removeById(menuId);
        //返回结果
        if(flag){
            return ResponseModel.success("删除成功");
        }else{
            return ResponseModel.fail("删除失败");
        }
    }

    @Override
    public ResponseModel queryById(RequestModel<MenuVO> requestModel) {
        //参数校验
        MenuVO menuVO = requestModel.getData();
        if(menuVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long menuId = menuVO.getMenuId();
        if(menuId == null){
            return RepCodeEnum.NULL_ERROR.parseError("menuId");
        }

        //业务处理
        MenuPO menuPO = getById(menuId);
        if(menuPO == null){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("menuId="+menuId.longValue());
        }

        //返回结果
        BeanUtils.copyProperties(menuPO, menuVO);
        return ResponseModel.success(menuVO);
    }

    @Override
    public ResponseModel queryByPage(RequestModel<MenuVO> requestModel) {
        //参数校验
        MenuVO menuVO = requestModel.getData();
        if(menuVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        //...todo

        //分页参数
        Page<MenuVO> page = new Page<MenuVO>(requestModel.getCurrentPage(), requestModel.getPageSize());
        page.addOrder(OrderItem.asc("parent_id"));
        page.addOrder(OrderItem.asc("sort"));

        menuVO.setDeleteFlag(DeleteFlagEnum.UNDELETED.getCodeValue());

        //业务处理
        IPage<MenuVO> pageList = menuMapper.queryByPage(page, menuVO);

        //返回结果
        return ResponseModel.success(pageList);
    }

    @Override
    /*
     * {
     *     "accessWithOrgIds": Map<String, List<Long>>,
     *     "accessWithOrgCodes": Map<String, List<String>>{
     *         "authManage:find": [1,2,3]
     *     }
     * }
     */
    public Map<String, Map> queryMenuActionCodeByUserId(Long userId) {
        Map<String, Map> grantedAuthorities = new HashMap<String, Map>();
        Map<String,List<Long>> grantedAuthoritiesWithOrgIds = new HashMap<String,List<Long>>();
        Map<String,List<String>> grantedAuthoritiesWithOrgCodes = new HashMap<String,List<String>>();

        grantedAuthorities.put("accessWithOrgIds", grantedAuthoritiesWithOrgIds);
        grantedAuthorities.put("accessWithOrgCodes", grantedAuthoritiesWithOrgCodes);

        if(userId == null){
            return grantedAuthorities;
        }

        //如果是超级管理员，所有用户默认全部权限
        if(userId.longValue() == Constant.ADMIN_USER_ID){
            List<Long> orgIdList=new ArrayList<Long>();
            List<String> orgCodeList=new ArrayList<String>();

            List<MenuVO> menuActionList = menuMapper.queryAllMenuActionCode();
            orgService.list().stream().forEach(orgPO -> {
                orgIdList.add(orgPO.getOrgId());
                orgCodeList.add(orgPO.getOrgCode());
            });

            menuActionList.stream().forEach(menuVO -> {
                String menuCode = menuVO.getMenuCode();
                String actionCode = menuVO.getActionCode();
                String menuActionCode = String.format("%s:%s",menuCode, actionCode);
                if(StringUtils.isBlank(menuActionCode)){
                    return;
                }
                grantedAuthoritiesWithOrgIds.put(menuActionCode, orgIdList);
                grantedAuthoritiesWithOrgCodes.put(menuActionCode, orgCodeList);
            });
            grantedAuthorities.put("accessWithOrgIds", grantedAuthoritiesWithOrgIds);
            grantedAuthorities.put("accessWithOrgCodes", grantedAuthoritiesWithOrgCodes);
            return grantedAuthorities;
        }

        //如果是普通用户
        List<UserRoleOrgVO> userRoleOrgVOList = menuMapper.queryMenuActionCodeByUserId(userId);
        if(userRoleOrgVOList == null){
            return grantedAuthorities;
        }
        QueryWrapper<MenuPO> menuWrapper = new QueryWrapper<MenuPO>();
        menuWrapper.eq("delete_flag", DeleteFlagEnum.UNDELETED.getCodeValue());
        List<MenuPO> menuPOS = menuMapper.selectList(menuWrapper);
        Map<Long, MenuPO> collect = menuPOS.stream().collect(Collectors.toMap(MenuPO::getMenuId, menuPO -> menuPO));
        userRoleOrgVOList.forEach(userRoleOrgVO -> {
            if (null != userRoleOrgVO.getParentId() && userRoleOrgVO.getParentId() > 0) {
                Long parentId = userRoleOrgVO.getParentId();
                //将父级展示给前端,控制左侧菜单
                String find = String.format("%s:%s", collect.get(parentId).getMenuCode(), "find");
                if (!grantedAuthoritiesWithOrgIds.containsKey(find)) {
                    grantedAuthoritiesWithOrgIds.put(find, new ArrayList<Long>());
                    grantedAuthoritiesWithOrgCodes.put(find, new ArrayList<String>());
                }
            }
            String permissionString = String.format("%s:%s", userRoleOrgVO.getMenuCode(), userRoleOrgVO.getActionCode());
            if(grantedAuthoritiesWithOrgIds.containsKey(permissionString)) {
                List<Long> longs = grantedAuthoritiesWithOrgIds.get(permissionString);
                if (!longs.contains(userRoleOrgVO.getOrgId())) {
                    longs.add(userRoleOrgVO.getOrgId());
                }
                List<String> list = grantedAuthoritiesWithOrgCodes.get(permissionString);
                if (!list.contains(userRoleOrgVO.getOrgCode())) {
                    list.add(userRoleOrgVO.getOrgCode());
                }
            } else{
                List<Long> orgIdList=new ArrayList<Long>();
                orgIdList.add(userRoleOrgVO.getOrgId());
                grantedAuthoritiesWithOrgIds.put(permissionString, orgIdList);

                List<String> orgCodeList=new ArrayList<String>();
                orgCodeList.add(userRoleOrgVO.getOrgCode());
                grantedAuthoritiesWithOrgCodes.put(permissionString, orgCodeList);
            }
        });
        grantedAuthorities.put("accessWithOrgIds", grantedAuthoritiesWithOrgIds);
        grantedAuthorities.put("accessWithOrgCodes", grantedAuthoritiesWithOrgCodes);
        return grantedAuthorities;
    }

    @Override
    public ResponseModel queryActionTreeForMenu(RequestModel<MenuVO> requestModel) {
        List<TreeVO> tree=new ArrayList<TreeVO>();

        Long menuId = requestModel.getData().getMenuId();
        if(menuId == null){
            return RepCodeEnum.NULL_ERROR.parseError("menuId");
        }
        //该菜单已经关联的按钮
        QueryWrapper<MenuActionPO> menuQueryWrapper = new QueryWrapper<MenuActionPO>();
        menuQueryWrapper.select("action_id")
                .eq("menu_id", menuId);

        List<MenuActionPO> menuActionList = menuActionMapper.selectList(menuQueryWrapper);
        List<Long> actionIdsOfMenu = new ArrayList<>();
        menuActionList.stream().forEach(menuAction -> {
            actionIdsOfMenu.add(menuAction.getActionId());
        });

        //所有的按钮
        QueryWrapper<ActionPO> queryWrapper = new QueryWrapper<ActionPO>();
        queryWrapper.select("action_id","action_name")
                .eq("delete_flag", DeleteFlagEnum.UNDELETED.getCodeValue())
                .and(Wrapper  -> Wrapper.eq("enable_flag", EnableFlagEnum.ENABLE.getCodeValue()));
        List<ActionPO> actionList = actionService.list(queryWrapper);

        actionList.stream().forEach(action ->{
            TreeVO treeVO = new TreeVO();
            treeVO.setId(action.getActionId());
            treeVO.setLabel(action.getActionName());
            tree.add(treeVO);
        });

        JSONObject repData = new JSONObject();
        repData.put("treeData",tree);
        repData.put("checkedIds",actionIdsOfMenu);

        return ResponseModel.success(repData);
    }

    @Override
    @Transactional
    public ResponseModel saveActionTreeForMenu(RequestModel<MenuVO> requestModel) {
        //参数校验
        Long menuId = requestModel.getData().getMenuId();
        List<Long> actionIds = requestModel.getData().getActionIds();
        if(menuId == null){
            return RepCodeEnum.NULL_ERROR.parseError("menuId");
        }
        if(actionIds == null){
            return RepCodeEnum.NULL_ERROR.parseError("actionIds");
        }

        //清除菜单的旧关联按钮
        QueryWrapper<MenuActionPO> menuQueryWrapper = new QueryWrapper<MenuActionPO>();
        menuQueryWrapper.eq("menu_id", menuId);
        menuActionMapper.delete(menuQueryWrapper);

        //保存先的关联
        actionIds.stream().forEach(actionId -> {
            MenuActionPO menuActionPO = new MenuActionPO();
            menuActionPO.setMenuId(menuId);
            menuActionPO.setActionId(actionId);
            menuActionMapper.insert(menuActionPO);
        });

        //根据menuId清除t_role_menu_action的已勾选的actionIds
        QueryWrapper<RoleMenuActionPO> roleMenuActionQueryWrapper = new QueryWrapper<>();
        roleMenuActionQueryWrapper.eq("menu_id", menuId);
        if (actionIds.size() > 0) {
            roleMenuActionQueryWrapper.notIn("action_id", actionIds);
        }
        roleMenuActionMapper.delete(roleMenuActionQueryWrapper);
        return updateById(requestModel);
    }
}
