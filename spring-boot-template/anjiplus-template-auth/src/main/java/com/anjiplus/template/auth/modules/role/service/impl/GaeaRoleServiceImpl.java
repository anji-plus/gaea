package com.anjiplus.template.auth.modules.role.service.impl;

import com.anjiplus.template.auth.modules.menu.controller.dto.TreeDTO;
import com.anjiplus.template.auth.modules.org.dao.GaeaOrgMapper;
import com.anjiplus.template.auth.modules.org.dao.entity.GaeaOrg;
import com.anjiplus.template.auth.modules.role.controller.param.RoleMenuActionReqParam;
import com.anjiplus.template.auth.modules.role.controller.param.RoleOrgReqParam;
import com.anjiplus.template.auth.modules.role.dao.GaeaRoleMapper;
import com.anjiplus.template.auth.modules.role.dao.GaeaRoleMenuActionMapper;
import com.anjiplus.template.auth.modules.role.dao.GaeaRoleOrgMapper;
import com.anjiplus.template.auth.modules.role.dao.entity.GaeaRoleMenuAction;
import com.anjiplus.template.auth.modules.role.dao.entity.GaeaRoleOrg;
import com.anjiplus.template.auth.modules.role.service.GaeaRoleService;
import com.anjiplus.template.auth.modules.user.dao.GaeaUserRoleMapper;
import com.anjiplus.template.auth.modules.user.dao.GaeaUserRoleOrgMapper;
import com.anjiplus.template.auth.modules.user.dao.entity.GaeaUserRole;
import com.anjiplus.template.auth.modules.user.dao.entity.GaeaUserRoleOrg;
import com.anjiplus.template.auth.modules.role.dao.entity.GaeaRole;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.anji.plus.gaea.bean.TreeNode;
import com.anji.plus.gaea.constant.Enabled;
import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色(GaeaRole)ServiceImpl
 *
 * @author lr
 * @since 2021-02-02 13:37:54
 */
@Service
public class GaeaRoleServiceImpl implements GaeaRoleService {

    @Autowired
    private GaeaRoleMapper gaeaRoleMapper;

    @Autowired
    private GaeaUserRoleMapper gaeaUserRoleMapper;
    @Autowired
    private GaeaRoleOrgMapper gaeaRoleOrgMapper;
    @Autowired
    private GaeaOrgMapper gaeaOrgMapper;
    @Autowired
    private GaeaUserRoleOrgMapper gaeaUserRoleOrgMapper;
    @Autowired
    private GaeaRoleMenuActionMapper gaeaRoleMenuActionMapper;

    @Override
    public GaeaBaseMapper<GaeaRole> getMapper() {
        return gaeaRoleMapper;
    }

    @Override
    public List<String> getUserRoleCodes(String username) {

        LambdaQueryWrapper<GaeaUserRole> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GaeaUserRole::getUsername, username);

        List<GaeaUserRole> gaeaUserRoles = gaeaUserRoleMapper.selectList(wrapper);
        return gaeaUserRoles.stream().map(GaeaUserRole::getRoleCode).collect(Collectors.toList());
    }

    @Override
    public TreeDTO queryRoleOrgTree(String roleCode) {
        //该角色已经关联的组织
        QueryWrapper<GaeaRoleOrg> roleOrgQueryWrapper = new QueryWrapper<GaeaRoleOrg>();
        roleOrgQueryWrapper.lambda().select(GaeaRoleOrg::getId, GaeaRoleOrg::getOrgCode).eq(GaeaRoleOrg::getRoleCode, roleCode);
        //组装出选中的id
        List<GaeaRoleOrg> roleOrgList = gaeaRoleOrgMapper.selectList(roleOrgQueryWrapper);
        List<String> checkedCodes = roleOrgList.stream().map(GaeaRoleOrg::getOrgCode).collect(Collectors.toList());

        //所有的组织
        QueryWrapper<GaeaOrg> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(GaeaOrg::getId, GaeaOrg::getOrgCode, GaeaOrg::getOrgName, GaeaOrg::getOrgParentCode)
                .eq(GaeaOrg::getDeleteFlag, Enabled.NO.getValue())
                .and(e -> e.eq(GaeaOrg::getEnabled, Enabled.YES.getValue()));
        List<GaeaOrg> orgList = gaeaOrgMapper.selectList(queryWrapper);

        List<TreeNode> treeList = buildOrgTree(orgList, "0");
        //返回结果
        TreeDTO result = new TreeDTO();
        result.setTreeDatas(treeList);
        result.setCheckedCodes(checkedCodes);
        return result;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOrgTreeForRole(RoleOrgReqParam requestModel) {

        //清除菜单的旧关联按钮
        QueryWrapper<GaeaRoleOrg> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.lambda().eq(GaeaRoleOrg::getRoleCode, requestModel.getRoleCode());
        gaeaRoleOrgMapper.delete(menuQueryWrapper);
        List<String> checkedCodes = requestModel.getOrgCodes();
        List<GaeaRoleOrg> roleOrgList = new ArrayList<>(checkedCodes.size());
        //保存新的
        checkedCodes.stream().forEach(e -> {
            GaeaRoleOrg roleOrgPO = new GaeaRoleOrg();
            roleOrgPO.setOrgCode(e);
            roleOrgPO.setRoleCode(requestModel.getRoleCode());
            roleOrgList.add(roleOrgPO);
        });
        gaeaRoleOrgMapper.insertBatch(roleOrgList);
        //根据roleId清除gaea_user_role_org的已勾选的orgIds
        QueryWrapper<GaeaUserRoleOrg> userRoleOrgQueryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<GaeaUserRoleOrg> lambdaQueryWrapper = userRoleOrgQueryWrapper.lambda();
        lambdaQueryWrapper.eq(GaeaUserRoleOrg::getRoleCode, requestModel.getRoleCode());
        if (checkedCodes.size() > 0) {
            lambdaQueryWrapper.notIn(GaeaUserRoleOrg::getOrgCode, checkedCodes);
        }
        gaeaUserRoleOrgMapper.delete(userRoleOrgQueryWrapper);
        return true;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveMenuActionTreeForRole(RoleMenuActionReqParam reqParam) {
        String roleCode = reqParam.getRoleCode();
        List<String> checkedCodsList = reqParam.getCodes();

        //清除菜单的旧关联按钮
        LambdaQueryWrapper<GaeaRoleMenuAction> queryWrapper=Wrappers.lambdaQuery();
        queryWrapper.select(GaeaRoleMenuAction::getId,GaeaRoleMenuAction::getRoleCode)
                .eq(GaeaRoleMenuAction::getRoleCode,roleCode);
        gaeaRoleMenuActionMapper.delete(queryWrapper);

        if(CollectionUtils.isNotEmpty(checkedCodsList)){
            List<GaeaRoleMenuAction> checkList=new ArrayList<>(checkedCodsList.size());
            //保存新的关联
            checkedCodsList.forEach(s -> {
                String[] codesArr= s.split(":");
                String menuCode=codesArr[0];
                String actionCode=codesArr[1];
                GaeaRoleMenuAction gaeaRoleMenuAction=new GaeaRoleMenuAction();
                gaeaRoleMenuAction.setRoleCode(roleCode);
                gaeaRoleMenuAction.setActionCode(actionCode);
                gaeaRoleMenuAction.setMenuCode(menuCode);
                checkList.add(gaeaRoleMenuAction);
            });
            gaeaRoleMenuActionMapper.insertBatch(checkList);
        }
        return true;
    }


    /**
     * 组织树
     *
     * @param orgList
     * @param pCode
     * @return
     */
    private List<TreeNode> buildOrgTree(List<GaeaOrg> orgList, String pCode) {
        List<TreeNode> childList = new ArrayList<>();
        orgList.forEach(orgPO -> {
            if (StringUtils.isNotEmpty(orgPO.getOrgParentCode())&&orgPO.getOrgParentCode().equals(pCode)) {
                TreeNode treeVO = new TreeNode();
                treeVO.setId(orgPO.getOrgCode());
                treeVO.setLabel(orgPO.getOrgName());
                childList.add(treeVO);
            }
        });
        childList.forEach(treeVO -> {
            List<TreeNode> treeList = buildOrgTree(orgList, treeVO.getId());
            if (!treeList.isEmpty()) {
                treeVO.setChildren(treeList);
            }
        });
        return childList;
    }

}
