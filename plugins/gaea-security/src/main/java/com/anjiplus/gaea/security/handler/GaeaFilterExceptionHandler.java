package com.anjiplus.gaea.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.anjiplus.gaea.security.cache.CacheKeyEnum;
import com.anjiplus.gaea.security.code.UserResponseCode;
import com.anjiplus.gaea.security.i18.GaeaMessageSourceAccessor;
import com.anjiplus.gaea.security.i18.GaeaSecurityMessageSource;
import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.github.anji.plus.gaea.bean.ResponseBean;
import com.github.anji.plus.gaea.cache.CacheHelper;
import com.github.anji.plus.gaea.code.ResponseCode;
import com.github.anji.plus.gaea.constant.Enabled;
import com.github.anji.plus.gaea.constant.GaeaConstant;
import com.github.anji.plus.gaea.exception.BusinessException;
import com.github.anji.plus.gaea.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 过滤器异常处理
 * @author lr
 * @since 2021-01-23
 */
public class GaeaFilterExceptionHandler {

    private GaeaMessageSourceAccessor messages = GaeaSecurityMessageSource.getAccessor();

    private Logger logger = LoggerFactory.getLogger(GaeaFilterExceptionHandler.class);

    @Autowired
    private CacheHelper cacheHelper;

    /**
     * 异常处理
     * @param request
     * @param response
     * @param e
     * @throws IOException
     */
    public void handler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        response.setCharacterEncoding(GaeaConstant.CHARSET_UTF8);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        ResponseBean.Builder builder = ResponseBean.builder();
        ResponseBean responseBean;
        String code = ResponseCode.FAIL_CODE;
        if(e instanceof TokenExpiredException || e instanceof SignatureGenerationException || e instanceof SignatureVerificationException) {
            code = UserResponseCode.USER_TOKEN_EXPIRED;
            String token = request.getHeader(JwtUtils.Authorization);
            //删除缓存中的私钥，让登录失效
            cacheHelper.stringSetExpire(CacheKeyEnum.TOKEN_JWT_EXPIRE.getKey() + token, Enabled.YES.getValue().toString(), 2, TimeUnit.HOURS);
        }else if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            code = businessException.getCode();
        } else if (e instanceof Exception) {
            //登录凭证失效
            logger.error("", e);
            code = ResponseCode.FAIL_CODE;
        }

        responseBean = builder.code(code).build();
        responseBean.setMessage(messages.getMessage(code,code));
        try {
            response.getWriter().print(JSONObject.toJSONString(responseBean));
        } catch (IOException io) {

        }
    }
}
