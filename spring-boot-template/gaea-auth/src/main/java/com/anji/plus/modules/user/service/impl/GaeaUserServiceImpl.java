package com.anji.plus.modules.user.service.impl;

import com.anji.plus.common.MagicValueConstants;
import com.anji.plus.common.RespCommonCode;
import com.anji.plus.modules.menu.controller.dto.TreeDTO;
import com.anji.plus.modules.org.dao.GaeaOrgMapper;
import com.anji.plus.modules.org.dao.entity.GaeaOrg;
import com.anji.plus.modules.role.controller.dto.RoleOrgDto;
import com.anji.plus.modules.role.dao.GaeaRoleOrgMapper;
import com.anji.plus.modules.user.controller.dto.GaeaUserDTO;
import com.anji.plus.modules.user.controller.param.GaeaUserPasswordParam;
import com.anji.plus.modules.user.controller.param.UserRoleOrgReqParam;
import com.anji.plus.modules.user.dao.GaeaUserRoleOrgMapper;
import com.anji.plus.modules.user.dao.entity.GaeaUserRoleOrg;
import com.anji.plus.util.DecryptUtil;
import com.anji.plus.util.MD5Util;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.anji.plus.gaea.bean.TreeNode;
import com.anji.plus.gaea.constant.Enabled;
import com.anji.plus.gaea.exception.BusinessExceptionBuilder;
import com.anji.plus.gaea.holder.UserContentHolder;
import com.anji.plus.modules.user.dao.entity.GaeaUser;
import com.anji.plus.modules.user.dao.GaeaUserMapper;
import com.anji.plus.modules.user.service.GaeaUserService;
import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(GaeaUser)ServiceImpl
 *
 * @author lr
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
                .and(e -> e.eq(GaeaOrg::getEnabled, Enabled.YES.getValue()));
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
        LambdaQueryWrapper<GaeaUserRoleOrg> userRoleOrgWrapper = Wrappers.lambdaQuery();
        userRoleOrgWrapper.select(GaeaUserRoleOrg::getOrgCode, GaeaUserRoleOrg::getRoleCode)
                .eq(GaeaUserRoleOrg::getUsername, username);
        gaeaUserRoleOrgMapper.delete(userRoleOrgWrapper);
        List<GaeaUserRoleOrg> list = new ArrayList<>();
        orgRoleCodes.forEach(codesStr -> {
            GaeaUserRoleOrg userRoleOrg = new GaeaUserRoleOrg();
            String[] info = codesStr.split("_");
            userRoleOrg.setOrgCode(info[0]);
            userRoleOrg.setRoleCode(info[1]);
            userRoleOrg.setUsername(username);
            list.add(userRoleOrg);
        });
        gaeaUserRoleOrgMapper.insertBatch(list);
        return true;
    }

    @Override
    public Boolean updatePassword(GaeaUserPasswordParam requestParam) {
        //参数校验
        if (!requestParam.getConfirmPassword().equals(requestParam.getPassword())) {
            //密码和确认密码不一致
            throw BusinessExceptionBuilder.build(RespCommonCode.AUTH_PASSWORD_NOTSAME);
        }
        //新密码不能与老密码一样
        if (StringUtils.equals(requestParam.getOldPassword(), requestParam.getPassword())) {
            throw BusinessExceptionBuilder.build(RespCommonCode.USER_PASSWORD_CONFIG_PASSWORD_CANOT_EQUAL);
        }
        String password = DecryptUtil.decrypt(requestParam.getPassword());
        String oldPassword = DecryptUtil.decrypt(requestParam.getOldPassword());
        String username = UserContentHolder.getContext().getUsername();

        LambdaQueryWrapper<GaeaUser> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(GaeaUser::getId, GaeaUser::getPassword)
                .eq(GaeaUser::getUsername, username);
        GaeaUser gaeaUser = gaeaUserMapper.selectOne(queryWrapper);

        if (DecryptUtil.matches(gaeaUser.getPassword(), oldPassword)) {
            throw BusinessExceptionBuilder.build(RespCommonCode.OLD_PASSWORD_ERROR);
        }
        gaeaUser.setPassword(password);
        int flag = gaeaUserMapper.updateById(gaeaUser);
        //返回结果
        if (flag > 0) {
            return MagicValueConstants.TRUE;
        } else {
            return MagicValueConstants.FALSE;
        }
    }

    @Override
    public Boolean saveGaeaUser(GaeaUserDTO dto) {
        //设置默认密码
        String md5Pwd = MD5Util.encryptBySalt(MagicValueConstants.DEFAULT_PASSWORD);
        String defaultPwd = DecryptUtil.decrypt(md5Pwd);
        dto.setPassword(defaultPwd);
        GaeaUser gaeaUser = new GaeaUser();
        BeanUtils.copyProperties(dto, gaeaUser);
        gaeaUserMapper.insert(gaeaUser);
        return MagicValueConstants.TRUE;
    }

    @Override
    public List<GaeaOrg> getOrgByUsername(String username) {
        return gaeaUserRoleOrgMapper.getOrgInfoByUsername(username);
    }

    @Override
    public List<String> getRoleByUserOrg(String username, String orgCode) {
        return gaeaUserRoleOrgMapper.getRoleCodeByUser(username, orgCode);
    }

    @Override
    public Boolean setDefaultPwd(String username) {
        GaeaUser user = this.getUserByUsername(username);
        String md5Pwd = MD5Util.encryptBySalt(MagicValueConstants.DEFAULT_PASSWORD);
        String defaultPwd = DecryptUtil.decrypt(md5Pwd);
        user.setPassword(defaultPwd);
        int flag = gaeaUserMapper.updateById(user);
        return flag > 0 ? MagicValueConstants.TRUE : MagicValueConstants.FALSE;
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
            if (StringUtils.isNotEmpty(orgPO.getOrgParentCode())&&orgPO.getOrgParentCode().equals(pid)) {
                TreeNode treeVO = new TreeNode();
                treeVO.setId(orgPO.getOrgCode());
                treeVO.setLabel(orgPO.getOrgName());
                childList.add(treeVO);
            }
        });
        childList.forEach(treeVO -> {
            List<TreeNode> treeList = buildOrgRoleTree(orgList, roleOrgVOS, treeVO.getId());
            List<TreeNode> collect = roleOrgVOS.stream().filter(roleOrgVO -> roleOrgVO.getOrgCode().equals(treeVO.getId()))
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
