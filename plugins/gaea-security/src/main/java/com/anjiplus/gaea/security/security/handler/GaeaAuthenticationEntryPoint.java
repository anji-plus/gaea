package com.anjiplus.gaea.security.security.handler;


import com.alibaba.fastjson.JSONObject;

import com.anjiplus.gaea.security.code.UserResponseCode;
import com.github.anji.plus.gaea.bean.ResponseBean;
import com.github.anji.plus.gaea.constant.GaeaConstant;
import com.github.anji.plus.gaea.i18.MessageSourceHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 认证不通过
 * @author lirui
 * @since 2021-01-26
 */
public class GaeaAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private Logger logger = LoggerFactory.getLogger(GaeaAuthenticationEntryPoint.class);

    @Autowired
    private MessageSourceHolder messageSourceHolder;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding(GaeaConstant.CHARSET_UTF8);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        String code = UserResponseCode.USER_TOKEN_EXPIRED;
        ResponseBean responseBean = ResponseBean.builder().code(code).build();
        responseBean.setMessage(messageSourceHolder.getMessage(code));
        response.getWriter().print( JSONObject.toJSONString(responseBean));
    }
}
