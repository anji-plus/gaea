package com.github.anji.plus.modules.role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.anji.plus.modules.role.dao.entity.GaeaRole;
import com.github.anji.plus.modules.role.dao.GaeaRoleMapper;
import com.github.anji.plus.modules.role.service.GaeaRoleService;
import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.github.anji.plus.modules.user.dao.GaeaUserRoleMapper;
import com.github.anji.plus.modules.user.dao.entity.GaeaUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色(GaeaRole)ServiceImpl
 *
 * @author lirui
 * @since 2021-02-02 13:37:54
 */
@Service
public class GaeaRoleServiceImpl implements GaeaRoleService {

    @Autowired
    private GaeaRoleMapper  gaeaRoleMapper;

    @Autowired
    private GaeaUserRoleMapper gaeaUserRoleMapper;

    @Override
    public GaeaBaseMapper<GaeaRole> getMapper() {
        return  gaeaRoleMapper;
    }

    @Override
    public List<String> getUserRoleCodes(String username) {

        LambdaQueryWrapper<GaeaUserRole> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GaeaUserRole::getUsername, username);

        List<GaeaUserRole> gaeaUserRoles = gaeaUserRoleMapper.selectList(wrapper);
        return gaeaUserRoles.stream().map(GaeaUserRole::getRoleCode).collect(Collectors.toList());
    }
}
