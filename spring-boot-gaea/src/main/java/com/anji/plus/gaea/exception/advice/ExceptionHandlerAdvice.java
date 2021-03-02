package com.anji.plus.gaea.exception.advice;

import com.anji.plus.gaea.bean.ResponseBean;
import com.anji.plus.gaea.exception.BusinessException;
import com.anji.plus.gaea.i18.MessageSourceHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.anji.plus.gaea.code.ResponseCode.FAIL_CODE;


/**
 * 全局异常处理
 * @author lr
 * @since 2021-01-02
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @Autowired
    private MessageSourceHolder messageSourceHolder;

    /**
     * 业务异常
     * @param businessException
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseBean handleBusinessException(BusinessException businessException) {
        return ResponseBean.builder().code(businessException.getCode()).args(businessException.getArgs()).build();
    }

    /**
     * 参数校验异常
     *
     * @param methodArgumentNotValidException
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseBean methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        String code = methodArgumentNotValidException.getBindingResult().getFieldError().getDefaultMessage();
        String message;
        try {
            message = messageSourceHolder.getMessage(code, null);
        } catch (NoSuchMessageException exception) {
            message = code;
        }
        return ResponseBean.builder().code(FAIL_CODE).message(message).build();
    }

    /**
     * 业务异常
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseBean exception(Exception exception){
        //返回值构建
        exception.printStackTrace();
        ResponseBean.Builder builder = ResponseBean.builder();
        builder.code(FAIL_CODE);
        builder.message(exception.getMessage());
        return builder.build();
    }

}
