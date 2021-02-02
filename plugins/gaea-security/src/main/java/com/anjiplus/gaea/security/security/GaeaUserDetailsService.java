package com.anjiplus.gaea.security.security;


import com.anjiplus.gaea.security.security.extension.UserDetailsServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义security的UserDetailsService,从数据库获取用户信息
 *
 * @author lirui
 * @since 2021-01-25
 */
public class GaeaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDetailsServiceHelper userDetailsServiceHelper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户
        UserDetails user = userDetailsServiceHelper.findByUsername(username);
        //获取当前用户所拥有的角色
        List<String> userRoles = userDetailsServiceHelper.getUserRoles(username);
        //获取当前用户的权限
        List<GrantedAuthority> authorities = userDetailsServiceHelper.getUserAuthorities(userRoles).stream()
                .map(roleCode -> {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roleCode);
                    return grantedAuthority;
                }).collect(Collectors.toList());

        return User.builder().username(username)
                .password(user.getPassword())
                .roles(userRoles.toArray(new String[0]))
                .authorities(authorities)
                .disabled(!user.isEnabled())
                .accountLocked(user.isAccountNonLocked())
                .accountExpired(!user.isAccountNonExpired())
                .accountLocked(!user.isAccountNonLocked())
                .credentialsExpired(!user.isCredentialsNonExpired()).build();
    }
}
