package com.anjiplus.gaea.security.bean;


import com.github.anji.plus.gaea.bean.ResponseBean;
import com.github.anji.plus.gaea.code.ResponseCode;
import com.github.anji.plus.gaea.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author lirui
 * @since 2021-01-16
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    /**
     * 查询用户异常
     * @param e
     * @return
     */
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    private ResponseBean internalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        ResponseBean.Builder builder = ResponseBean.builder();
        String message = "系统异常";
        Throwable cause = e.getCause();
        //判断是不是业务异常
        if(cause instanceof BusinessException) {
            BusinessException businessException = (BusinessException) cause;
            String code = businessException.getCode();
            builder.code(code);
            message = messageSource.getMessage(code, businessException.getArgs(), LocaleContextHolder.getLocale());
        }else {
            builder.code(ResponseCode.FAIL_CODE);
        }
        ResponseBean responseBean = builder.build();
        responseBean.setMessage(message);
        return responseBean;
    }


    /**
     * 业务处理异常
     * @param ex
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public ResponseBean businessHandler(Exception ex) {
        ResponseBean responseBean;
        BusinessException be = (BusinessException) ex;
        responseBean = ResponseBean.builder().code(be.getCode()).build();
        responseBean.setMessage(messageSource.getMessage(be.getCode(),be.getArgs(), LocaleContextHolder.getLocale()));
        return responseBean;
    }
}
