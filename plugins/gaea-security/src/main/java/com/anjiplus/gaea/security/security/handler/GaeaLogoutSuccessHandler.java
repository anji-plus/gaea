package com.anjiplus.gaea.security.security.handler;


import com.alibaba.fastjson.JSONObject;
import com.anjiplus.gaea.security.cache.CacheKeyEnum;
import com.anjiplus.gaea.security.code.UserResponseCode;
import com.anjiplus.gaea.security.i18.GaeaMessageSourceAccessor;
import com.anjiplus.gaea.security.i18.GaeaSecurityMessageSource;
import com.github.anji.plus.gaea.bean.ResponseBean;
import com.github.anji.plus.gaea.cache.CacheHelper;
import com.github.anji.plus.gaea.constant.Enabled;
import com.github.anji.plus.gaea.constant.GaeaConstant;
import com.github.anji.plus.gaea.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 登出成功
 * @author lirui
 * @since 2021-01-27
 */
public class GaeaLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private CacheHelper cacheHelper;

    private GaeaMessageSourceAccessor messages = GaeaSecurityMessageSource.getAccessor();

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setCharacterEncoding(GaeaConstant.CHARSET_UTF8);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        String token = request.getHeader(JwtUtils.Authorization);
        if(StringUtils.isNotBlank(token)) {
            //删除缓存中的私钥，让登录失效(过期时间为2小时)
            cacheHelper.stringSetExpire(CacheKeyEnum.TOKEN_JWT_EXPIRE.getKey() + token, Enabled.YES.getValue().toString(), 2, TimeUnit.HOURS);
        }

        String code = UserResponseCode.USER_LOGOUT_SUCCESS;
        ResponseBean responseBean = ResponseBean.builder().build();
        responseBean.setMessage(messages.getMessage(code,code));
        response.getWriter().print(JSONObject.toJSONString(responseBean));
    }
}
