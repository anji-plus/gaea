package com.anji.mirror.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.anji.mirror.auth.domain.po.*;
import com.anji.mirror.auth.domain.vo.MenuActionVO;
import com.anji.mirror.auth.domain.vo.RoleVO;
import com.anji.mirror.auth.domain.vo.UserRoleOrgVO;
import com.anji.mirror.auth.mapper.*;
import com.anji.mirror.auth.service.RoleService;
import com.anji.mirror.auth.service.UserService;
import com.anji.mirror.auth.utils.NumToIntUtil;
import com.anji.mirror.common.enums.DeleteFlagEnum;
import com.anji.mirror.common.enums.EnableFlagEnum;
import com.anji.mirror.common.enums.RepCodeEnum;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.model.TreeVO;
import com.anji.mirror.common.security.Constant;
import com.anji.mirror.common.utils.BeanUtils;
import com.anji.mirror.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
@Service
@Slf4j
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RolePO> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuActionMapper roleMenuActionMapper;

    @Autowired
    private MenuActionMapper menuActionMapper;

    @Autowired
    private RoleOrgMapper roleOrgMapper;

    @Autowired
    private OrgMapper orgMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleOrgMapper userRoleOrgMapper;

    /**
     * 根据数据库必填项，校验是否为空，不校验主键
     *
     * @param roleVO
     * @return
     */
    private ResponseModel validateCreateFieldNotNull(RoleVO roleVO) {
        if (roleVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        if (StringUtils.isBlank(roleVO.getRoleName())) {
            return RepCodeEnum.NULL_ERROR.parseError("角色名称");
        }
        /* 该片段由生成器产生，请根据实际情况修改
        if(roleVO.getRoleId() == null){
            return RepCodeEnum.NULL_ERROR.parseError("roleId");
        }
        if(StringUtils.isBlank(roleVO.getRoleName())){
            return RepCodeEnum.NULL_ERROR.parseError("roleName");
        }
        if(StringUtils.isBlank(roleVO.getRoleDesc())){
            return RepCodeEnum.NULL_ERROR.parseError("roleDesc");
        }
        if(roleVO.getEnableFlag() == null){
            return RepCodeEnum.NULL_ERROR.parseError("enableFlag");
        }
        if(roleVO.getDeleteFlag() == null){
            return RepCodeEnum.NULL_ERROR.parseError("deleteFlag");
        }
        if(StringUtils.isBlank(roleVO.getCreatedBy())){
            return RepCodeEnum.NULL_ERROR.parseError("createdBy");
        }
        if(roleVO.getCreatedTime() == null){
            return RepCodeEnum.NULL_ERROR.parseError("createdTime");
        }
        if(StringUtils.isBlank(roleVO.getUpdatedBy())){
            return RepCodeEnum.NULL_ERROR.parseError("updatedBy");
        }
        if(roleVO.getUpdatedTime() == null){
            return RepCodeEnum.NULL_ERROR.parseError("updatedTime");
        }
        */
        return ResponseModel.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel create(RequestModel<RoleVO> requestModel) {
        //参数校验
        RoleVO roleVO = requestModel.getData();
        ResponseModel valid = validateCreateFieldNotNull(roleVO);
        if (valid.isError()) {
            return valid;
        }
        //业务校验
        //...todo

        //业务处理
        String operator = requestModel.getOpUserName();
        if (roleVO.getEnableFlag() == null) {
            roleVO.setEnableFlag(EnableFlagEnum.ENABLE.getCodeValue());
        }
        roleVO.setDeleteFlag(DeleteFlagEnum.UNDELETED.getCodeValue());
        roleVO.setCreatedBy(operator);
        roleVO.setCreatedTime(LocalDateTime.now());
        roleVO.setUpdatedBy(operator);
        roleVO.setUpdatedTime(LocalDateTime.now());

        RolePO rolePO = new RolePO();
        BeanUtils.copyProperties(roleVO, rolePO);

        QueryWrapper<RolePO> selectMapper = new QueryWrapper<>();
        selectMapper.eq("role_name", rolePO.getRoleName());
        List<RolePO> poList = roleMapper.selectList(selectMapper);
        if (poList.size() > 0) {
            return RepCodeEnum.EXIST_ERROR.parseError("角色名称");
        }

        boolean flag = save(rolePO);

        //返回结果
        if (flag) {
            return ResponseModel.success(rolePO);
        } else {
            return ResponseModel.fail(RepCodeEnum.FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel updateById(RequestModel<RoleVO> requestModel) {
        //参数校验
        RoleVO roleVO = requestModel.getData();
        if (roleVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long roleId = roleVO.getRoleId();
        if (roleId == null) {
            return RepCodeEnum.NULL_ERROR.parseError("roleId");
        }
        //业务校验
        //...todo

        //业务处理
        RolePO rolePO = getById(roleId);
        if (rolePO == null) {
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("roleId=" + roleId.longValue());
        }

        QueryWrapper<RolePO> roleListWrapper = new QueryWrapper<>();
        roleListWrapper.eq("role_name", roleVO.getRoleName());
        roleListWrapper.notIn("role_id", roleVO.getRoleId());
        List<RolePO> rolePOS = roleMapper.selectList(roleListWrapper);
        if (rolePOS.size() > 0){
            return RepCodeEnum.EXIST_ERROR.parseError("角色名称" + roleVO.getRoleName());
        }


        roleVO.setUpdatedBy(requestModel.getOpUserName());
        roleVO.setUpdatedTime(LocalDateTime.now());
        BeanUtils.copyProperties(roleVO, rolePO, true);
        boolean flag = updateById(rolePO);

        //返回结果
        if (flag) {
            return ResponseModel.success(rolePO);
        } else {
            return ResponseModel.fail("修改失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel deleteById(RequestModel<RoleVO> requestModel) {
        //参数校验
        RoleVO roleVO = requestModel.getData();
        if (roleVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long roleId = roleVO.getRoleId();
        if (roleId == null) {
            return RepCodeEnum.NULL_ERROR.parseError("roleId");
        }

        //业务处理
        RolePO rolePO = getById(roleId);
        if (rolePO == null) {
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("roleId=" + roleId.longValue());
        }
        boolean flag = removeById(roleId);

        //返回结果
        if (flag) {
            //刷新redis的用户缓存数据
            QueryWrapper<UserRoleOrgPO> userRoleOrgQueryWrapper = new QueryWrapper<>();
            userRoleOrgQueryWrapper.eq("role_id", roleId);
            List<UserRoleOrgPO> userRoleOrgPOS = userRoleOrgMapper.selectList(userRoleOrgQueryWrapper);
            Set<Long> collect = userRoleOrgPOS.stream().map(UserRoleOrgPO::getUserId).collect(Collectors.toSet());
            collect.forEach(aLong -> {
                userService.refreshUserCache(aLong);
            });
            return ResponseModel.success("删除成功");
        } else {
            return ResponseModel.fail("删除失败");
        }
    }

    @Override
    public ResponseModel queryById(RequestModel<RoleVO> requestModel) {
        //参数校验
        RoleVO roleVO = requestModel.getData();
        if (roleVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long roleId = roleVO.getRoleId();
        if (roleId == null) {
            return RepCodeEnum.NULL_ERROR.parseError("roleId");
        }

        //业务处理
        RolePO rolePO = getById(roleId);
        if (rolePO == null) {
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("roleId=" + roleId.longValue());
        }

        //返回结果
        BeanUtils.copyProperties(rolePO, roleVO);
        return ResponseModel.success(roleVO);
    }

    @Override
    public ResponseModel queryByPage(RequestModel<RoleVO> requestModel) {
        //参数校验
        RoleVO roleVO = requestModel.getData();
        if (roleVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        //...todo

        //分页参数
        Page<RoleVO> page = new Page<RoleVO>(requestModel.getCurrentPage(), requestModel.getPageSize());
        roleVO.setDeleteFlag(DeleteFlagEnum.UNDELETED.getCodeValue());

        //业务处理
        IPage<RoleVO> pageList = roleMapper.queryByPage(page, roleVO);

        //返回结果
        return ResponseModel.success(pageList);
    }

    @Override
    public ResponseModel queryMenuActionTreeForRole(RequestModel<RoleVO> requestModel) {
        Long roleId = requestModel.getData().getRoleId();
        if (roleId == null) {
            return RepCodeEnum.NULL_ERROR.parseError("roleId");
        }

        //该菜单已经关联的按钮
        QueryWrapper<RoleMenuActionPO> menuQueryWrapper = new QueryWrapper<RoleMenuActionPO>();
        menuQueryWrapper.select("menu_id, action_id").eq("role_id", roleId);
        //组装出选中的id
        List<RoleMenuActionPO> menuActionList = roleMenuActionMapper.selectList(menuQueryWrapper);
        List<String> checkedIds = menuActionList.stream()
                .map(roleMenuActionPO -> String.format("%s_%s", roleMenuActionPO.getMenuId(), roleMenuActionPO.getActionId()))
                .collect(Collectors.toList());

        //所有的菜单及按钮
        List<MenuActionVO> list = menuActionMapper.queryAllMenuAction();
        QueryWrapper<MenuPO> menuWrapper = new QueryWrapper<MenuPO>();
        menuWrapper.eq("enable_flag", EnableFlagEnum.ENABLE.getCodeValue());
        //所有菜单
        List<MenuPO> menuList = menuMapper.selectList(menuWrapper);
        List<TreeVO> tree = buildMenuActionTree(menuList, list, null);
        //返回结果
        JSONObject repData = new JSONObject();
        repData.put("treeData", tree);
        repData.put("checkedIds", checkedIds);
        return ResponseModel.success(repData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel saveMenuActionTreeForRole(RequestModel<RoleVO> requestModel) {
        //参数校验
        Long roleId = requestModel.getData().getRoleId();
        List<String> checkedIds = requestModel.getData().getMenuActionIds();
        if (roleId == null) {
            return RepCodeEnum.NULL_ERROR.parseError("roleId");
        }
        if (checkedIds == null) {
            return RepCodeEnum.NULL_ERROR.parseError("menuActionIds");
        }

        //清除菜单的旧关联按钮
        QueryWrapper<RoleMenuActionPO> menuQueryWrapper = new QueryWrapper<RoleMenuActionPO>();
        menuQueryWrapper.eq("role_id", roleId);
        roleMenuActionMapper.delete(menuQueryWrapper);

        //menuActionIds 多
        //checkedIds 少
        //保存先的关联
        checkedIds.forEach(s -> {
            if (s.contains("_")) {
                int menuId = NumToIntUtil.strToInt(s.split("_")[0]);
                int actionId = NumToIntUtil.strToInt(s.split("_")[1]);
                if (menuId > 0 && actionId > 0) {
                    RoleMenuActionPO roleMenuActionPO = new RoleMenuActionPO();
                    roleMenuActionPO.setRoleId(roleId);
                    roleMenuActionPO.setMenuId((long) menuId);
                    roleMenuActionPO.setActionId((long) actionId);
                    roleMenuActionMapper.insert(roleMenuActionPO);
                }
            }

        });

        //刷新redis的用户缓存数据
        QueryWrapper<UserRoleOrgPO> userRoleOrgQueryWrapper = new QueryWrapper<>();
        userRoleOrgQueryWrapper.eq("role_id", roleId);
        List<UserRoleOrgPO> userRoleOrgPOS = userRoleOrgMapper.selectList(userRoleOrgQueryWrapper);
        Set<Long> collect = userRoleOrgPOS.stream().map(UserRoleOrgPO::getUserId).collect(Collectors.toSet());
        collect.forEach(aLong -> {
            userService.refreshUserCache(aLong);
        });
        return updateById(requestModel);
    }

    @Override
    public ResponseModel queryOrgTreeForRole(RequestModel<RoleVO> requestModel) {
        return queryTree(requestModel, 0);
    }

    @Override
    public ResponseModel queryUserOrgTreeForRole(RequestModel<RoleVO> requestModel) {
        return queryTree(requestModel, 1);
    }

    /**
     * @param requestModel
     * @param type         0 ： 查询角色关联的组织机构
     * @return
     */
    private ResponseModel queryTree(RequestModel<RoleVO> requestModel, int type) {
        Long roleId = requestModel.getData().getRoleId();
        if (roleId == null) {
            return RepCodeEnum.NULL_ERROR.parseError("roleId");
        }

        //该菜单已经关联的按钮
        QueryWrapper<RoleOrgPO> roleOrgQueryWrapper = new QueryWrapper<RoleOrgPO>();
        roleOrgQueryWrapper.select("org_id").eq("role_id", roleId);
        //组装出选中的id
        List<RoleOrgPO> roleOrgList = roleOrgMapper.selectList(roleOrgQueryWrapper);
        List<Long> checkedIds = roleOrgList.stream().map(RoleOrgPO::getOrgId).collect(Collectors.toList());

        //所有的组织
        QueryWrapper<OrgPO> queryWrapper = new QueryWrapper<OrgPO>();
        queryWrapper.select("org_id", "org_name", "org_code", "org_parent_code")
                .eq("delete_flag", DeleteFlagEnum.UNDELETED.getCodeValue())
                .and(Wrapper -> Wrapper.eq("enable_flag", EnableFlagEnum.ENABLE.getCodeValue()));
        List<OrgPO> orgList = orgMapper.selectList(queryWrapper);

        /// 查询对应role_id的用户 角色 组织关联关系
        UserRoleOrgVO userRoleOrgVO = new UserRoleOrgVO();
        userRoleOrgVO.setRoleId(roleId);
        List<UserRoleOrgVO> userRoleOrgVOS = userRoleOrgMapper.queryUserRoleOrgByRoleId(userRoleOrgVO);
        List<TreeVO> tree;
        if (type == 1) {
            List<Long> opOrgIdList = requestModel.getOpOrgIdList();
            if (requestModel.getOpUserId().equals(Constant.ADMIN_USER_ID)) {
                opOrgIdList = null;
            }
            tree = buildOrgUserTree(orgList, userRoleOrgVOS, opOrgIdList, "0");
        } else {
            tree = buildOrgTree(orgList, "0");
        }
        //返回结果
        JSONObject repData = new JSONObject();
        repData.put("treeData", tree);
        repData.put("checkedIds", checkedIds);

        return ResponseModel.success(repData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel saveOrgTreeForRole(RequestModel<RoleVO> requestModel) {
        //参数校验
        Long roleId = requestModel.getData().getRoleId();
        List<Long> checkedIds = requestModel.getData().getOrgIds();
        if (roleId == null) {
            return RepCodeEnum.NULL_ERROR.parseError("roleId");
        }
        if (checkedIds == null) {
            return RepCodeEnum.NULL_ERROR.parseError("orgIds");
        }

        //清除菜单的旧关联按钮
        QueryWrapper<RoleOrgPO> menuQueryWrapper = new QueryWrapper<RoleOrgPO>();
        menuQueryWrapper.eq("role_id", roleId);
        roleOrgMapper.delete(menuQueryWrapper);

        //保存先的关联
        checkedIds.stream().forEach(orgId -> {
            RoleOrgPO roleOrgPO = new RoleOrgPO();
            roleOrgPO.setRoleId(roleId);
            roleOrgPO.setOrgId(orgId);
            roleOrgMapper.insert(roleOrgPO);
        });

        //根据roleId清除t_user_role_org的已勾选的orgIds
        QueryWrapper<UserRoleOrgPO> userRoleOrgQueryWrapper = new QueryWrapper<>();
        userRoleOrgQueryWrapper.eq("role_id", roleId);
        if (checkedIds.size() > 0) {
            userRoleOrgQueryWrapper.notIn("org_id", checkedIds);
        }
        userRoleOrgMapper.delete(userRoleOrgQueryWrapper);

        //刷新redis的用户缓存数据
        userRoleOrgQueryWrapper.clear();
        userRoleOrgQueryWrapper.eq("role_id", roleId);
        List<UserRoleOrgPO> userRoleOrgPOS = userRoleOrgMapper.selectList(userRoleOrgQueryWrapper);
        Set<Long> collect = userRoleOrgPOS.stream().map(UserRoleOrgPO::getUserId).collect(Collectors.toSet());
        collect.forEach(aLong -> {
            userService.refreshUserCache(aLong);
        });
        return updateById(requestModel);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel saveOrgTreeForUserRole(RequestModel<RoleVO> requestModel) {
        //参数校验
        Long roleId = requestModel.getData().getRoleId();
        List<String> userIdOrgIds = requestModel.getData().getUserIdOrgIds();
        if (roleId == null) {
            return RepCodeEnum.NULL_ERROR.parseError("roleId");
        }
        if (userIdOrgIds == null) {
            return RepCodeEnum.NULL_ERROR.parseError("userIdOrgIds");
        }
        //根据  userRoleOrgId 清除t_user_role_org的已勾选的 userIdOrgIds
        List<Long> userRoleOrgIds = new ArrayList<>();
//        List<Long> orgIdIds = new ArrayList<>();

        if (!userIdOrgIds.isEmpty() && userIdOrgIds.size() > 0) {
            userRoleOrgIds = userIdOrgIds.stream().filter(userIdOrgId -> {
                        String[] arr = userIdOrgId.split("_");
                        return arr.length == 3;
                    }
            ).map(userIdOrgId -> {
                //id_userId_orgId
                String[] arr = userIdOrgId.split("_");
//                orgIdIds.add(Long.parseLong(arr[2]));
                return Long.parseLong(arr[0]);
            }).collect(Collectors.toList());


//            List<Long> orgIds = userIdOrgIds.stream().filter(userIdOrgId -> {
//                        String[] arr = userIdOrgId.split("_");
//                        return arr.length == 3;
//                    }
//            ).map(userIdOrgId -> {
//                String[] arr = userIdOrgId.split("_");
//                return Long.parseLong(arr[2]);
//            }).distinct().collect(Collectors.toList());
//            List<Long> opOrgIdList = requestModel.getOpOrgIdList();
//
//            /// 查询对应role_id的用户 角色 组织关联关系
//            UserRoleOrgVO userRoleOrgVO = new UserRoleOrgVO();
//            userRoleOrgVO.setRoleId(roleId);
//            List<UserRoleOrgVO> userRoleOrgVOS = userRoleOrgMapper.queryUserRoleOrgByRoleId(userRoleOrgVO);
//            List<Long> checkOrgIds = userRoleOrgVOS.stream().map(UserRoleOrgVO::getOrgId).distinct().collect(Collectors.toList());
//            checkOrgIds.removeAll(orgIds);
//            if (!checkOrgIds.containsAll(opOrgIdList)) {
//                return ResponseModel.fail(RepCodeEnum.GATEWAY_AUTH_ERROR);
//            }

        }


        QueryWrapper<UserRoleOrgPO>  deleteWrapper= new QueryWrapper<>();
        deleteWrapper.eq("role_id", roleId);
        if (userRoleOrgIds.size() > 0) {
            deleteWrapper.notIn("id", userRoleOrgIds);
        }
        userRoleOrgMapper.delete(deleteWrapper);

        //刷新redis的用户缓存数据
        QueryWrapper<UserRoleOrgPO> userRoleOrgQueryWrapper = new QueryWrapper<>();
        userRoleOrgQueryWrapper.eq("role_id", roleId);
        List<UserRoleOrgPO> userRoleOrgPOS = userRoleOrgMapper.selectList(userRoleOrgQueryWrapper);
        Set<Long> collect = userRoleOrgPOS.stream().map(UserRoleOrgPO::getUserId).collect(Collectors.toSet());
        collect.forEach(aLong -> {
            userService.refreshUserCache(aLong);
        });
        return updateById(requestModel);
    }

    /**
     * 菜单按钮树
     *
     * @param menuList
     * @param list
     * @param pid
     * @return
     */
    private static List<TreeVO> buildMenuActionTree(List<MenuPO> menuList, List<MenuActionVO> list, Object pid) {
        List<TreeVO> childList = new ArrayList<>();
        menuList.forEach(menuPO -> {
            //ParentId为空且pid为0   或  parentId为0或
            //如果pid 为空 && parentId为空或parentId为0
            if ((null == pid && (null == menuPO.getParentId() || menuPO.getParentId().equals(0L)))
//                    ||(null == pid && null == menuPO.getParentId())
                    || (null != menuPO.getParentId() && menuPO.getParentId().equals(pid))) {
                TreeVO treeVO = new TreeVO();
                String roleTreeId = String.format("RP_%s", menuPO.getMenuId());
                treeVO.setId(roleTreeId); //id存在冲突父节点用前缀区分
                treeVO.setLabel(menuPO.getMenuName());
                childList.add(treeVO);
            }
        });

        childList.forEach(treeVO -> {
            String rp = treeVO.getId().toString().replace("RP_", "");
            Long menuId = Long.valueOf(rp);
            List<TreeVO> treeList = buildMenuActionTree(menuList, list, menuId);
            if (treeList.isEmpty()) {
                List<TreeVO> collect = list.stream().filter(menuActionVO -> menuActionVO.getMenuId().equals(menuId))
                        .map(menuActionVO -> {
                            TreeVO treeEntity = new TreeVO();
                            String format = String.format("%s_%s", menuActionVO.getMenuId(), menuActionVO.getActionId());
                            treeEntity.setId(format);
                            treeEntity.setLabel(menuActionVO.getActionName());
                            return treeEntity;
                        }).collect(Collectors.toList());
                treeVO.setChildren(collect);
            } else {
                treeVO.setChildren(treeList);
            }
        });
        //过滤数组没有Children数据
        return childList.stream().filter(s -> !s.getChildren().isEmpty()).collect(Collectors.toList());
    }


    /**
     * 组织树
     *
     * @param orgList
     * @param pid
     * @return
     */
    private static List<TreeVO> buildOrgTree(List<OrgPO> orgList, Object pid) {
        List<TreeVO> childList = new ArrayList<>();
        orgList.forEach(orgPO -> {
            if (orgPO.getOrgParentCode().equals(pid)) {
                TreeVO treeVO = new TreeVO();
                String roleTreeId = String.format("%s_%s", orgPO.getOrgId(), orgPO.getOrgCode());
                treeVO.setId(roleTreeId);
                treeVO.setLabel(orgPO.getOrgName());
                childList.add(treeVO);
            }
        });

        childList.forEach(treeVO -> {
            String orgId = treeVO.getId().toString().split("_")[0];
            String orgCode = treeVO.getId().toString().split("_")[1];
            treeVO.setId(Long.valueOf(orgId));
            List<TreeVO> treeList = buildOrgTree(orgList, orgCode);
            if (!treeList.isEmpty()) {
                treeVO.setChildren(treeList);
            }
        });
        return childList;
    }

    /**
     * 查询角色- 组织 -用户 Tree
     *
     * @param orgList
     * @param pid
     * @return
     */
    private static List<TreeVO> buildOrgUserTree(List<OrgPO> orgList, List<UserRoleOrgVO> userRoleOrgVOS, List<Long> opOrgIdList, Object pid) {
        List<TreeVO> childList = new ArrayList<>();
        orgList.forEach(orgPO -> {
            if (orgPO.getOrgParentCode().equals(pid)) {
                TreeVO treeVO = new TreeVO();
                String roleTreeId = String.format("%s_%s", orgPO.getOrgId(), orgPO.getOrgCode());
                treeVO.setId(roleTreeId);
                treeVO.setLabel(orgPO.getOrgName());
                if (null != opOrgIdList && !opOrgIdList.contains(orgPO.getOrgId())) {
                    treeVO.setDisabled(true);
                }
                childList.add(treeVO);
            }
        });

        childList.forEach(treeVO -> {
            String orgId = treeVO.getId().toString().split("_")[0];
            String orgCode = treeVO.getId().toString().split("_")[1];
            treeVO.setId(Long.valueOf(orgId));
            List<TreeVO> treeList = buildOrgUserTree(orgList, userRoleOrgVOS, opOrgIdList, orgCode);
            List<TreeVO> collect = userRoleOrgVOS.stream().filter(userRoleOrgPO -> userRoleOrgPO.getOrgId().equals(Long.valueOf(orgId)))
                    .map(userRoleOrgPO -> {
                        TreeVO treeEntity = new TreeVO();
                        //id_userId_orgId
                        String roleOrgTreeId = String.format("%s_%s_%s", userRoleOrgPO.getId(), userRoleOrgPO.getUserId(), orgId);
                        treeEntity.setId(roleOrgTreeId);
                        String label = userRoleOrgPO.getLoginName() + "(" + userRoleOrgPO.getRealName() + ")";
                        treeEntity.setLabel(label);
                        if (null != opOrgIdList && !opOrgIdList.contains(Long.parseLong(orgId))) {
                            treeEntity.setDisabled(true);
                        }
                        return treeEntity;
                    }).collect(Collectors.toList());
            if (!treeList.isEmpty()) {
                collect.addAll(treeList);
            }
            treeVO.setChildren(collect);



        });
        return childList;
    }
}
