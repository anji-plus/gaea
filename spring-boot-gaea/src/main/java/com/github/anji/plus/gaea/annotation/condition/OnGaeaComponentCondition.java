package com.github.anji.plus.gaea.annotation.condition;

import com.github.anji.plus.gaea.constant.GaeaConstant;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

/**
 * 订阅条件注解
 * @author lr
 * @since 2021-01-13
 */
public class OnGaeaComponentCondition extends SpringBootCondition {

    /**
     * 订阅组件的前缀
     */
    private String prefix = GaeaConstant.COMPONENT_PREFIX;

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {

        Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(ConditionalOnGaeaComponent.class.getName());

        //组件名称
        String componentName = String.valueOf(annotationAttributes.get("value"));

        ConditionOutcome endpointOutcome = getEndpointOutcome(context, componentName);

        if (endpointOutcome != null) {
            return endpointOutcome;
        }

//        Binder binder = Binder.get(context.getEnvironment());
//
//        List<String> subscribes = binder.bind(this.prefix, Bindable.listOf(String.class)).get();
//
//        for (String subscribe: subscribes) {
//            if (StringUtils.equals(value, subscribe)) {
//                return ConditionOutcome.match(ConditionMessage.forCondition(ConditionalOnGaeaComponent.class, "metadata").because("matched"));
//            }
//        }
//
        return ConditionOutcome.noMatch(ConditionMessage.of("gaea，not load "));
    }

    protected ConditionOutcome getEndpointOutcome(ConditionContext context, String componentName) {
        Environment environment = context.getEnvironment();
        String enabledProperty = this.prefix + componentName + ".enabled";
        if (environment.containsProperty(enabledProperty)) {
            boolean match = environment.getProperty(enabledProperty, Boolean.class, true);
            return new ConditionOutcome(match, ConditionMessage.forCondition(ConditionalOnGaeaComponent.class)
                    .because(this.prefix + componentName + ".enabled is " + match));
        }
        return null;
    }
}
