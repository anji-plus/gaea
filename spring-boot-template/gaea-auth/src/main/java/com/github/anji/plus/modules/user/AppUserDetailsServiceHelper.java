package com.github.anji.plus.modules.user;

import com.anjiplus.gaea.security.security.extension.UserDetailsServiceHelper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.anji.plus.gaea.constant.Enabled;
import com.github.anji.plus.modules.user.dao.entity.GaeaUser;
import com.github.anji.plus.modules.user.service.GaeaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 实现security的抽象类
 * @author lr
 * @since 2021-02-01
 */
@Component
public class AppUserDetailsServiceHelper extends UserDetailsServiceHelper {

    @Autowired
    private GaeaUserService gaeaUserService;

    @Override
    public UserDetails findByUsername(String username) {
        LambdaQueryWrapper<GaeaUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GaeaUser::getUsername, username);
        GaeaUser gaeaUser = gaeaUserService.getMapper().selectOne(wrapper);

        //构建security的用户对象
        //账号是否可用
        boolean enabled = Enabled.YES.getValue().equals(gaeaUser.getEnabled());
        //账号是否未锁定
        boolean accountNonLocked = Enabled.NO.getValue().equals(gaeaUser.getAccountLocked());
        //账号是否过期
        boolean accountNonExpired = Enabled.YES.getValue().equals(gaeaUser.getAccountNonExpired());
        //密码是否过期
        boolean credentialsNonExpired = Enabled.YES.getValue().equals(gaeaUser.getCredentialsNonExpired());

        return User.builder().username(username)
                .password(gaeaUser.getPassword())
                .disabled(!enabled)
                .accountLocked(!accountNonLocked)
                .accountExpired(!accountNonExpired)
                .authorities(new String[0])
                .credentialsExpired(!credentialsNonExpired).build();
    }

    @Override
    public List<String> getUserRoles(String username) {
        return new ArrayList<>();
    }

    @Override
    public Set<String> getUserAuthorities(List<String> roles) {
        return new HashSet<>();
    }
}
