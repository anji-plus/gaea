package com.anjiplus.gaea.security.security.url;

import com.anji.plus.gaea.constant.GaeaConstant;
import com.anjiplus.gaea.security.security.extension.UserDetailsServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 获取当前URL所需要的角色
 * @author lr
 * @since 2021-02-25
 */
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private UserDetailsServiceHelper userDetailsServiceHelper;

    /**
     * 路由匹配器
     */
    private static final  AntPathMatcher antPathMatcher = new AntPathMatcher();


    /***
     * 返回该url所需要的角色列表
     *
     * @param object: 请求相关信息
     * @return:
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        // 获取当前请求url
        String requestUrl = filterInvocation.getRequestUrl();
        String method = filterInvocation.getRequest().getMethod();
        String path = method + GaeaConstant.URL_SPLIT + requestUrl;

        //当前url需要的角色
        Set<String> needRoles = new HashSet<>();

        //请求方法+url路径与角色的对应关系
        Map<String, Set<String>> urlRoleMappings = userDetailsServiceHelper.getUrlRoleMappings();

        //精确匹配
        Set<String> exactMatchRoles = urlRoleMappings.get(path);

        //模糊匹配，类似/demo/**路由
        if (CollectionUtils.isEmpty(exactMatchRoles)) {
            Set<String> allUrls = urlRoleMappings.keySet();

            Optional<String> matchUrl = allUrls.stream().filter(url -> url.contains(GaeaConstant.URL_REPLACEMENT) && antPathMatcher.match(url, path)).findFirst();

            //如果有匹配的，则将结果放入needRoles
            matchUrl.ifPresent(match -> {
                Set<String> matchRoles = urlRoleMappings.get(match);
                if (!CollectionUtils.isEmpty(matchRoles)) {
                    needRoles.addAll(matchRoles);
                }
            });
        } else {
            //将精确匹配到的角色放入所需要的角色里
            needRoles.addAll(exactMatchRoles);
        }

        // 返回当前url需要的角色列表
        return SecurityConfig.createList(needRoles.toArray(new String[0]));
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
