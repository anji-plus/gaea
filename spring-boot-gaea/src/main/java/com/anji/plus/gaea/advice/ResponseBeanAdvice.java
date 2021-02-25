package com.anji.plus.gaea.advice;

import com.anji.plus.gaea.bean.ResponseBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 返回结果统一封装
 * @author lr
 * @since 2021-01-02
 */
@RestControllerAdvice
public class ResponseBeanAdvice implements ResponseBodyAdvice<ResponseBean> {

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getParameterType() == ResponseBean.class;
    }

    @Override
    public ResponseBean beforeBodyWrite(ResponseBean responseBean, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (StringUtils.isNotBlank(responseBean.getMessage())) {
            return responseBean;
        }
        String code = responseBean.getCode();
        if (StringUtils.isNotBlank(code)) {
            String message = messageSource.getMessage(code, responseBean.getArgs(), LocaleContextHolder.getLocale());
            responseBean.setMessage(message);
        }
        return responseBean;
    }
}
