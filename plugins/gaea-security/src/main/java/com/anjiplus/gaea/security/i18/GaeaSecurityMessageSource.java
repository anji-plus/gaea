package com.anjiplus.gaea.security.i18;

import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * 盖亚资源文件添加国际化
 * @author lr
 * @since 2021-02-22
 */
public class GaeaSecurityMessageSource extends ResourceBundleMessageSource {

    public GaeaSecurityMessageSource() {
        setBasename("com.anjiplus.gaea.security.messages");
    }

    public static GaeaMessageSourceAccessor getAccessor() {
        return new GaeaMessageSourceAccessor(new GaeaSecurityMessageSource());
    }
}
