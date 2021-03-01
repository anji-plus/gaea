package com.anjiplus.gaea.security.security.url;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 校验Url权限
 * @author lr
 * @since 2021-02-25
 */
public class UrlAccessDecisionManager implements AccessDecisionManager {

    /**
     * @param authentication: 当前用户登录成功后的信息
     * @param object: 请求url信息
     * @param collection: `UrlFilterInvocationSecurityMetadataSource`中的getAttributes方法传来的，表示当前请求需要的角色（可能有多个）
     * @return: void
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> collection) throws AccessDeniedException, AuthenticationException {

        //当前路由没有配置角色时，直接放过
        if (CollectionUtils.isEmpty(collection)) {
            return;
        }
        // 当前用户所具有的角色
        List<String> authorities = authentication.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.toList());
        // 遍历当前Url需要的角色
        for (ConfigAttribute ca : collection) {
            // ① 当前url请求需要的权限
            String needRole = ca.getAttribute();
            if (authorities.contains(needRole)) {
                return;
            }
        }

        //没有权限
        throw new AccessDeniedException("Access is denied");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
