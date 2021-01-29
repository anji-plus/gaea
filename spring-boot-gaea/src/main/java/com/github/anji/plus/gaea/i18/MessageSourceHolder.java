package com.github.anji.plus.gaea.i18;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 获取国际化信息
 * @author lr
 * @since 2021-01-12
 */
public class MessageSourceHolder {

    @Autowired
    private MessageSource messageSource;

    /**
     * 根据编号获取
     * @param code
     * @return
     */
    public String getMessage(String code) {
        return messageSource.getMessage(code, null,LocaleContextHolder.getLocale());
    }


    /**
     * 根据编号获取，并解析占位符
     * @param code
     * @param args
     * @return
     */
    public String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args,LocaleContextHolder.getLocale());
    }

}
