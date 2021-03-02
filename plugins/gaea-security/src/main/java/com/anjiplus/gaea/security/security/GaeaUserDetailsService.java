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
 * @author lr
 * @since 2021-01-25
 */
public class GaeaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDetailsServiceHelper userDetailsServiceHelper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户
        UserDetails userDetails;
        try {
            userDetails = userDetailsServiceHelper.findByUsername(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException(username);
        }

        //当用户不存在时
        if (userDetails == null) {
            throw new UsernameNotFoundException(username);
        }

        //获取当前用户所拥有的角色
        List<String> userRoles = userDetailsServiceHelper.getUserRoles(username);
        //获取当前用户的权限
        List<GrantedAuthority> authorities = userDetailsServiceHelper.getUserAuthorities(userRoles).stream()
                .map(roleCode -> {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roleCode);
                    return grantedAuthority;
                }).collect(Collectors.toList());

        return User.builder().username(username)
                .password(userDetails.getPassword())
                .roles(userRoles.toArray(new String[0]))
                .authorities(authorities)
                .disabled(!userDetails.isEnabled())
                .accountLocked(userDetails.isAccountNonLocked())
                .accountExpired(!userDetails.isAccountNonExpired())
                .accountLocked(!userDetails.isAccountNonLocked())
                .credentialsExpired(!userDetails.isCredentialsNonExpired()).build();
    }
}
