package com.github.anji.plus.modules;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @author lr
 * @since 2021-02-03
 */
public class Demo {

    public static void main(String[] args) {
        Locale locale = LocaleContextHolder.getLocale();

        System.out.println(locale.getLanguage());
        System.out.println(locale.getCountry());
        System.out.println(locale.toLanguageTag());
    }

}
