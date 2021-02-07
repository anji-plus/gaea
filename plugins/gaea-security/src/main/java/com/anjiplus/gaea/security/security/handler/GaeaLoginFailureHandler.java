package com.anjiplus.gaea.security.security.handler;


import com.alibaba.fastjson.JSONObject;
import com.anjiplus.gaea.security.GaeaSecurityProperties;
import com.anjiplus.gaea.security.cache.CacheKeyEnum;
import com.anjiplus.gaea.security.code.UserResponseCode;
import com.anjiplus.gaea.security.security.event.UserLockedApplicationEvent;
import com.github.anji.plus.gaea.bean.ResponseBean;
import com.github.anji.plus.gaea.cache.CacheHelper;
import com.github.anji.plus.gaea.constant.GaeaConstant;
import com.github.anji.plus.gaea.holder.UserContentHolder;
import com.github.anji.plus.gaea.i18.MessageSourceHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 登录成功
 *
 * @author lirui
 * @since 2021-01-27
 */
public class GaeaLoginFailureHandler implements AuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(GaeaLoginFailureHandler.class);


    @Autowired
    private GaeaSecurityProperties gaeaSecurityProperties;

    /**
     * 最大错误次数
     */
    private Integer errorMax = 5;

    /**
     * 是否显示验证码
     */
    private static final String VIEW_CAPTCHA = "captcha";

    /**
     * 超时时间
     */
    private Long TIME_OUT = 2L;

    private final static String ERROR_TIMES = "times";

    @Autowired
    private CacheHelper cacheHelper;
    @Autowired
    private MessageSourceHolder messageSourceHolder;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setCharacterEncoding(GaeaConstant.CHARSET_UTF8);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        String code = UserResponseCode.USER_TOKEN_EXPIRED;
        //获取上下文中的用户名
        String username = UserContentHolder.getContext().getUsername();

        ResponseBean.Builder builder = ResponseBean.builder();


        //用户名密码错误异常
        if (exception instanceof BadCredentialsException) {
            code = UserResponseCode.USER_PASSWORD_ERROR;
            String key = CacheKeyEnum.USER_PASSWORD_ERROR_NUMBER.getKey() + username;
            //增加登录错误次数
            Long increment = cacheHelper.increment(key);

            //两个小时过期
            cacheHelper.expire(key, TimeUnit.HOURS, TIME_OUT);

            //返回错误次数
            Map<String, Long> result = new HashMap<>(2);
            result.put(ERROR_TIMES, increment);

            //错误大于5次，账户锁定,发出锁定事件
            Integer errorTimes = Integer.parseInt(cacheHelper.stringGet(key));

            //大于指定次数默认3次，显示验证码
            if (errorTimes > gaeaSecurityProperties.getCaptchaTimes()) {
                result.put(VIEW_CAPTCHA, 1L);
            }

            //大于指定次数默认5次，锁定账户
            if (errorTimes >= gaeaSecurityProperties.getLockTimes()) {
                logger.warn("username or password error times >= {}，lock user", errorMax);
                //发出锁定账号事件，由应用自己处理
                applicationContext.publishEvent(new UserLockedApplicationEvent(username, errorTimes));
            }
            builder.data(result);
        }

        //账户锁定
        if (exception instanceof LockedException) {
            code = UserResponseCode.USER_LOCKED;
        }

        //用户不可用，注销
        if (exception instanceof DisabledException) {
            code = UserResponseCode.USER_DISABLED_ERROR;
        }


        ResponseBean responseBean = builder.code(code).build();
        responseBean.setMessage(messageSourceHolder.getMessage(code));
        response.getWriter().print(JSONObject.toJSONString(responseBean));
    }
}
