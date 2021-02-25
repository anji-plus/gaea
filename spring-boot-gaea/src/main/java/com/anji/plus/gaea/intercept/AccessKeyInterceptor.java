package com.anji.plus.gaea.intercept;

import com.anji.plus.gaea.annotation.AccessKey;
import com.anji.plus.gaea.code.ResponseCode;
import com.anji.plus.gaea.exception.BusinessExceptionBuilder;
import com.anji.plus.gaea.utils.GaeaUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * AccessKey拦截器
 * @author lir
 * @since 2019/7/9
 **/
public class AccessKeyInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(AccessKeyInterceptor.class);

    /**
     * 进入controller层之前拦截请求
     * @param request
     * @param httpServletResponse
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object handler) throws Exception {

        // 获取注解
        if( !(handler instanceof HandlerMethod)){
            return true ;
        }
        AccessKey accessKey = ((HandlerMethod) handler).getMethodAnnotation(AccessKey.class);
        if (accessKey == null) {
            return true;
        }
        String id= request.getParameter(accessKey.key());

        //如果id为空,再获取类似xx/{xx}
        if(StringUtils.isBlank(id)){
            Map<String, Object> pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            if(!CollectionUtils.isEmpty(pathVariables)){
                id = pathVariables.get(accessKey.key())+"";
            }
        }
        //如果参数ID值为空
        if(StringUtils.isBlank(id)){
            throw BusinessExceptionBuilder.build(ResponseCode.FAIL_CODE,"passkey校验失败，缺少参数ID");
        }

        String passKey= request.getParameter("accessKey");
        if(StringUtils.isBlank(passKey)){
            throw BusinessExceptionBuilder.build(ResponseCode.FAIL_CODE,"passkey校验失败，缺少参数passkey");
        }

        String realPassKey = GaeaUtils.getPassKey(Long.parseLong(id));
        if (!StringUtils.equals(passKey,realPassKey)) {
            throw BusinessExceptionBuilder.build(ResponseCode.FAIL_CODE,"passkey校验失败，传入的passkey参数值有误");
        }
        return true;
    }

}
