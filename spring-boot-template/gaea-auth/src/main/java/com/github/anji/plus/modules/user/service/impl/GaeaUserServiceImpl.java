package com.github.anji.plus.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.anji.plus.modules.user.dao.entity.GaeaUser;
import com.github.anji.plus.modules.user.dao.GaeaUserMapper;
import com.github.anji.plus.modules.user.service.GaeaUserService;
import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 用户表(GaeaUser)ServiceImpl
 *
 * @author lirui
 * @since 2021-02-02 13:38:12
 */
@Service
public class GaeaUserServiceImpl implements GaeaUserService {
    @Autowired
    private GaeaUserMapper  gaeaUserMapper;

    @Override
    public GaeaBaseMapper<GaeaUser> getMapper() {
        return  gaeaUserMapper;
    }

    @Override
    public GaeaUser getUserByUsername(String username) {
        LambdaQueryWrapper<GaeaUser> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(GaeaUser::getUsername, username);
        return gaeaUserMapper.selectOne(queryWrapper);
    }
}
