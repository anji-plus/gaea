package com.anjiplus.gaea.security.security;

import com.anjiplus.gaea.security.cache.CacheKeyEnum;
import com.anjiplus.gaea.security.handler.GaeaFilterExceptionHandler;
import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.github.anji.plus.gaea.cache.CacheHelper;
import com.github.anji.plus.gaea.holder.UserContentHolder;
import com.github.anji.plus.gaea.holder.UserContext;
import com.github.anji.plus.gaea.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * 分布式环境中构造本地Security Authentication
 *
 * @author lirui
 * @since 2021-01-26
 */
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    /**
     * 缓存帮助类
     */
    @Autowired
    private CacheHelper cacheHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private GaeaFilterExceptionHandler gaeaFilterExceptionHandler;

    protected AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            //当Security中存在登录标识时，直接跳过
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                filterChain.doFilter(request, response);
                return;
            }

            //获取请求头中token
            String token = request.getHeader(JwtUtils.Authorization);

            //当token为空或过期时，未登录
            if (StringUtils.isBlank(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            //判断token是否注销过
            if (cacheHelper.exist(CacheKeyEnum.TOKEN_JWT_EXPIRE.getKey() + token)) {
                throw new TokenExpiredException(String.format("The Token has expired on %s.", new Date()));
            }

            //从token中解析出用户名
            String username = JwtUtils.getUsername(token);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(createSuccessfulAuthentication(request, userDetails));
            SecurityContextHolder.setContext(context);

            UserContext userContext = new UserContext();
            userContext.setUsername(username);
            userContext.setAuthorities(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
            UserContentHolder.setContext(userContext);
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException | SignatureGenerationException | SignatureVerificationException exception) {
            gaeaFilterExceptionHandler.handler(request, response, exception);
            return;
        } catch (Exception e) {
            gaeaFilterExceptionHandler.handler(request, response, e);
            return;
        } finally {
            UserContentHolder.clearContext();
        }

    }

    /**
     * 构建成功的AuthenticationToken
     *
     * @param request
     * @param user
     * @return
     */
    private Authentication createSuccessfulAuthentication(HttpServletRequest request,
                                                          UserDetails user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());

        authenticationToken.setDetails(this.authenticationDetailsSource.buildDetails(request));

        return authenticationToken;
    }

}
