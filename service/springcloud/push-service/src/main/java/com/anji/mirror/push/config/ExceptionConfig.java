package com.anji.mirror.push.config;

import com.anji.mirror.common.model.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * mvc异常公共处理
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
@RestControllerAdvice
public class ExceptionConfig {

    private static Logger logger = LoggerFactory.getLogger(ExceptionConfig.class);

    @ExceptionHandler(Exception.class)
    public ResponseModel apiExceptionHandler(Exception ex) {
        logger.error("异常：", ex);
        return ResponseModel.error(ex.getMessage());
    }

    /**
     * 数据库unique唯一性异常
     * @param ex
     * @return
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseModel duplicateKeyExceptionHandler(DuplicateKeyException ex) {
        logger.error("异常：", ex);
        try {
            return ResponseModel.fail("数据已存在");
        } catch (Exception e) {
            return ResponseModel.error(ex.getMessage());
        }
    }

}
