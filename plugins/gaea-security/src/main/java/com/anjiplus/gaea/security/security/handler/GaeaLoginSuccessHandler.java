package com.anjiplus.gaea.security.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.anjiplus.gaea.security.cache.CacheKeyEnum;
import com.anjiplus.gaea.security.code.UserResponseCode;
import com.anjiplus.gaea.security.i18.GaeaMessageSourceAccessor;
import com.anjiplus.gaea.security.i18.GaeaSecurityMessageSource;
import com.anji.plus.gaea.bean.ResponseBean;
import com.anji.plus.gaea.cache.CacheHelper;
import com.anji.plus.gaea.constant.GaeaConstant;
import com.anji.plus.gaea.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * 登录成功
 * @author lr
 * @since 2021-01-27
 */
public class GaeaLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private CacheHelper cacheHelper;

    private GaeaMessageSourceAccessor messages = GaeaSecurityMessageSource.getAccessor();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setCharacterEncoding(GaeaConstant.CHARSET_UTF8);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String code = UserResponseCode.USER_LOGIN_SUCCESS;
        ResponseBean.Builder builder = ResponseBean.builder();
        String username = authentication.getName();

        //该用户独有的私钥
        String token = JwtUtils.createToken(username);

        //保存登录token
        cacheHelper.hashSet(CacheKeyEnum.TOKEN_JWT_USER.getKey(), username, token);

        //删除错误次数
        cacheHelper.delete(CacheKeyEnum.USER_PASSWORD_ERROR_NUMBER.getKey() + username);

        //保存权限并清空旧的权限
        String[] authorities =
                authentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
                        .toArray(new String[]{});

        cacheHelper.setAdd(CacheKeyEnum.USER_AUTHORITIES.getKey() + username, authorities, true);

        ResponseBean responseBean = builder.code(UserResponseCode.SUCCESS).data(token).build();
        responseBean.setMessage(messages.getMessage(code,code));
        response.getWriter().print( JSONObject.toJSONString(responseBean));
    }
}
