package com.anji.plus.modules.user;

import com.anji.plus.gaea.init.InitRequestUrlMappings;
import com.anjiplus.gaea.security.security.extension.UserDetailsServiceHelper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.anji.plus.gaea.constant.Enabled;
import com.anji.plus.modules.user.dao.entity.GaeaUser;
import com.anji.plus.modules.user.service.GaeaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 实现security的抽象类
 * @author lr
 * @since 2021-02-01
 */
@Component
public class AppUserDetailsServiceHelper extends UserDetailsServiceHelper {

    @Autowired
    private GaeaUserService gaeaUserService;

    @Autowired
    private InitRequestUrlMappings initRequestUrlMappings;

    @Override
    public UserDetails findByUsername(String username) {
        LambdaQueryWrapper<GaeaUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GaeaUser::getUsername, username);
        GaeaUser gaeaUser = gaeaUserService.getMapper().selectOne(wrapper);
        if (gaeaUser == null) {
            return null;
        }

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
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        return roles;
    }

    @Override
    public Set<String> getUserAuthorities(List<String> roles) {
        Set<String> authorities = new HashSet<>();
        authorities.add("admin");
        authorities.add("demo");
        return authorities;
    }

    /**
     * 实现url对应的角色
     * @return
     */
    @Override
    public Map<String, Set<String>> getUrlRoleMappings() {
        return super.getUrlRoleMappings();
    }
}
