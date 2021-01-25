package com.github.anji.plus.gaea.annotation.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * 订阅扩展组件
 * @author lr
 * @since 2021-01-12
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Conditional(OnGaeaComponentCondition.class)
public @interface ConditionalOnGaeaComponent {

    /**
     * 激活组件
     * @return
     */
    String value();

}
