package com.github.anji.plus.modules.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.anji.captcha.model.common.RepCodeEnum;
import com.anji.captcha.model.common.ResponseModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.anji.plus.common.MagicValueConstants;
import com.github.anji.plus.gaea.bean.TreeNode;
import com.github.anji.plus.gaea.constant.Enabled;
import com.github.anji.plus.gaea.holder.UserContentHolder;
import com.github.anji.plus.modules.menu.controller.dto.TreeDTO;
import com.github.anji.plus.modules.org.dao.GaeaOrgMapper;
import com.github.anji.plus.modules.org.dao.entity.GaeaOrg;
import com.github.anji.plus.modules.role.controller.dto.RoleOrgDto;
import com.github.anji.plus.modules.role.dao.GaeaRoleOrgMapper;
import com.github.anji.plus.modules.user.controller.param.UserRoleOrgReqParam;
import com.github.anji.plus.modules.user.dao.GaeaUserRoleOrgMapper;
import com.github.anji.plus.modules.user.dao.entity.GaeaUser;
import com.github.anji.plus.modules.user.dao.GaeaUserMapper;
import com.github.anji.plus.modules.user.dao.entity.GaeaUserRole;
import com.github.anji.plus.modules.user.dao.entity.GaeaUserRoleOrg;
import com.github.anji.plus.modules.user.service.GaeaUserService;
import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import org.bouncycastle.jcajce.provider.symmetric.AES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(GaeaUser)ServiceImpl
 *
 * @author lirui
 * @since 2021-02-02 13:38:12
 */
@Service
public class GaeaUserServiceImpl implements GaeaUserService {
    @Autowired
    private GaeaUserMapper gaeaUserMapper;
    @Autowired
    private GaeaUserRoleOrgMapper gaeaUserRoleOrgMapper;
    @Autowired
    private GaeaRoleOrgMapper gaeaRoleOrgMapper;
    @Autowired
    private GaeaOrgMapper gaeaOrgMapper;


    @Override
    public GaeaBaseMapper<GaeaUser> getMapper() {
        return gaeaUserMapper;
    }

    @Override
    public GaeaUser getUserByUsername(String username) {
        LambdaQueryWrapper<GaeaUser> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(GaeaUser::getUsername, username);
        return gaeaUserMapper.selectOne(queryWrapper);
    }

    @Override
    public TreeDTO queryRoleTree(String username) {
        //该用户已经关联的组织角色
        LambdaQueryWrapper<GaeaUserRoleOrg> userRoleOrgWrapper = Wrappers.lambdaQuery();
        userRoleOrgWrapper.select(GaeaUserRoleOrg::getRoleCode, GaeaUserRoleOrg::getOrgCode).eq(GaeaUserRoleOrg::getUsername, username);
        //组装出选中的id
        List<GaeaUserRoleOrg> userRoleOrgList = gaeaUserRoleOrgMapper.selectList(userRoleOrgWrapper);
        List<String> checkedIds = userRoleOrgList.stream()
                .map(userRoleOrgPO -> String.format("%s_%s", userRoleOrgPO.getOrgCode(), userRoleOrgPO.getRoleCode()))
                .collect(Collectors.toList());
        //所有的角色组织
        List<RoleOrgDto> roleOrgDtos = gaeaRoleOrgMapper.queryAllRoleOrg();
        //所有的组织
        LambdaQueryWrapper<GaeaOrg> queryOrgWrapper = Wrappers.lambdaQuery();
        queryOrgWrapper.select(GaeaOrg::getId, GaeaOrg::getOrgCode, GaeaOrg::getOrgName, GaeaOrg::getOrgParentCode)
                .eq(GaeaOrg::getDeleteFlag, Enabled.NO.getValue())
                .and(e -> e.eq(GaeaOrg::getEnableFlag, Enabled.YES.getValue()));
        List<GaeaOrg> orgList = gaeaOrgMapper.selectList(queryOrgWrapper);
        List<TreeNode> tree = buildOrgRoleTree(orgList, roleOrgDtos, "0");
        TreeDTO resultData = new TreeDTO();
        resultData.setTreeDatas(tree);
        resultData.setCheckedCodes(checkedIds);
        return resultData;
    }


    @Override
    @Transactional
    public Boolean saveRoleTree(UserRoleOrgReqParam reqParam) {
        String username = reqParam.getUsername();
        List<String> orgRoleCodes = reqParam.getRoleOrgCodes();
        //该用户已经关联的组织角色
        LambdaQueryWrapper<GaeaUserRoleOrg> userRoleOrgWrapper= Wrappers.lambdaQuery();
        userRoleOrgWrapper.select(GaeaUserRoleOrg::getOrgCode,GaeaUserRoleOrg::getRoleCode)
                .eq(GaeaUserRoleOrg::getUsername,username);
        gaeaUserRoleOrgMapper.delete(userRoleOrgWrapper);
        List<GaeaUserRoleOrg> list=new ArrayList<>();
        orgRoleCodes.forEach(codesStr->{
            GaeaUserRoleOrg userRoleOrg=new GaeaUserRoleOrg();
            String[] info= codesStr.split("_");
            userRoleOrg.setOrgCode(info[0]);
            userRoleOrg.setRoleCode(info[1]);
            userRoleOrg.setUsername(username);
            list.add(userRoleOrg);
        });
        gaeaUserRoleOrgMapper.insertBatch(list);
        return true;
    }


    /**
     * 组织角色树
     *
     * @param orgList
     * @param pid
     * @return
     */
    private static List<TreeNode> buildOrgRoleTree(List<GaeaOrg> orgList, List<RoleOrgDto> roleOrgVOS, Object pid) {
        List<TreeNode> childList = new ArrayList<>();
        orgList.forEach(orgPO -> {
            if (orgPO.getOrgParentCode().equals(pid)) {
                TreeNode treeVO = new TreeNode();
                treeVO.setId(orgPO.getOrgCode());
                treeVO.setLabel(orgPO.getOrgName());
                childList.add(treeVO);
            }
        });
        childList.forEach(treeVO -> {
            List<TreeNode> treeList = buildOrgRoleTree(orgList, roleOrgVOS, treeVO.getId());
            List<TreeNode> collect = roleOrgVOS.stream().filter(roleOrgVO -> roleOrgVO.getOrgCode().equals(Long.valueOf(treeVO.getId())))
                    .map(roleOrgVO -> {
                        TreeNode treeEntity = new TreeNode();
                        String roleOrgTreeId = String.format("%s_%s", treeVO.getId(), roleOrgVO.getRoleCode());
                        treeEntity.setId(roleOrgTreeId);
                        treeEntity.setLabel(roleOrgVO.getRoleName());
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
