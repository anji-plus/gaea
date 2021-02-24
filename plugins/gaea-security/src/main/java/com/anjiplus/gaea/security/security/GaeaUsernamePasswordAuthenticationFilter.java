package com.anjiplus.gaea.security.security;


import com.alibaba.fastjson.JSONObject;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.anjiplus.gaea.security.handler.GaeaFilterExceptionHandler;
import com.anjiplus.gaea.security.code.UserResponseCode;
import com.anjiplus.gaea.security.security.handler.GaeaLoginFailureHandler;
import com.anjiplus.gaea.security.security.handler.GaeaLoginSuccessHandler;
import com.anjiplus.gaea.security.utils.AuthUtils;
import com.anjiplus.gaea.security.utils.CryptoUtils;
import com.github.anji.plus.gaea.exception.BusinessExceptionBuilder;
import com.github.anji.plus.gaea.holder.UserContentHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 重写UsernamePasswordAuthenticationFilter，增加验证码并对用户名密码进行解密
 * 所有信息从请求体中取
 * @author lr
 * @since 2021-01-27
 */
public class GaeaUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(GaeaUsernamePasswordAuthenticationFilter.class);

    /**
     * 开启行为验证码二次校验
     */
    @Value("${aj.captcha.login.second.check:true}")
    private boolean captchaSecondCheck;

    /**
     * 验证码属性key
     */
    public static final String GAEA_SECURITY_CAPTCHA = "captchaVerification";

    /**
     * 需要验证码
     */
    public static final String GAEA_NEED_CAPTCHA = "captcha";


    @Autowired
    private CaptchaService captchaService;

    /**
     * 过滤器错误处理
     */
    @Autowired
    private GaeaFilterExceptionHandler gaeaFilterExceptionHandler;


    public GaeaUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                    AuthenticationSuccessHandler successHandler,
                                                    AuthenticationFailureHandler failureHandler) {
        super.setAuthenticationManager(authenticationManager);

        super.setAuthenticationSuccessHandler(successHandler);

        super.setAuthenticationFailureHandler(failureHandler);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        super.doFilter(req, res, chain);

        //清空
        UserContentHolder.clearContext();

        //清空登录信息
        AuthUtils.loginInfoThreadLocal.remove();
    }

    /**
     * 添加验证码逻辑
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //验证行为验证码
        if (captchaSecondCheck && StringUtils.isNotBlank(request.getHeader(GAEA_NEED_CAPTCHA))) {
            JSONObject requestBody = AuthUtils.getRequestBody(request);
            //请求体判断
            if (requestBody == null) {
                gaeaFilterExceptionHandler.handler(request,response,BusinessExceptionBuilder.build(UserResponseCode.REQUEST_BODY_ERROR));
            }
            //判断是否有验证码
            if (StringUtils.isBlank(requestBody.getString(GAEA_SECURITY_CAPTCHA))) {
                gaeaFilterExceptionHandler.handler(request,response,BusinessExceptionBuilder.build(UserResponseCode.CAPTCHA_ERROR));
            }
            //验证码
            String captchaVerification = requestBody.getString(GAEA_SECURITY_CAPTCHA);
            CaptchaVO captchaVO = new CaptchaVO();
            captchaVO.setCaptchaVerification(captchaVerification);
            com.anji.captcha.model.common.ResponseModel verification = captchaService.verification(captchaVO);

            //验证失败
            if (!verification.isSuccess()) {
                gaeaFilterExceptionHandler.handler(request,response,BusinessExceptionBuilder.build(UserResponseCode.CAPTCHA_ERROR));
            }
        }

        //验证用户名密码
        // AbstractAuthenticationProcessingFilter.continueChainBeforeSuccessfulAuthentication = false,
        // 验证后就return，不会继续走过滤器
        return super.attemptAuthentication(request, response);
    }

    /**
     * 重写获取用户，解密
     * @param request
     * @return
     */
    @Override
    protected String obtainUsername(HttpServletRequest request) {
        JSONObject requestBody = AuthUtils.getRequestBody(request);
        if (requestBody == null) {
            return null;
        }
        String username = requestBody.getString(SPRING_SECURITY_FORM_USERNAME_KEY);
//        try {
//            username = CryptoUtils.desEncrypt(username);
//        } catch (Exception e) {
//            logger.error("用户名解密失败", e);
//            return null;
//        }
//        username = username.trim();

        //放入到上下文中
        UserContentHolder.getContext().setUsername(username);
        return username;
    }

    /**
     * 重写获取密码，解密
     * @param request
     * @return
     */
    @Override
    protected String obtainPassword(HttpServletRequest request) {

        JSONObject requestBody = AuthUtils.getRequestBody(request);
        if (requestBody == null) {
            return null;
        }
        String password = requestBody.getString(SPRING_SECURITY_FORM_PASSWORD_KEY);
//        try {
//            password = CryptoUtils.desEncrypt(password);
//        } catch (Exception e) {
//            logger.error("密码解密失败", e);
//            return null;
//        }
//        password = password.trim();
        return password;
    }
}
