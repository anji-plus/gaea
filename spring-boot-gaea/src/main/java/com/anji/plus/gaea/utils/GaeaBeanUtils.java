package com.anji.plus.gaea.utils;


import com.anji.plus.gaea.annotation.DtoSkip;
import com.anji.plus.gaea.annotation.Formatter;
import com.anji.plus.gaea.constant.Enabled;
import com.anji.plus.gaea.cache.CacheHelper;
import com.anji.plus.gaea.cache.RedisKeyEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 字段翻译
 *
 * @author lr
 * @since 2021-01-12
 */
public abstract class GaeaBeanUtils {



    /**
     * 字段类型转换
     *
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T copyAndFormatter(Object source, T target) {
        //获取目标类并翻译
        Field[] declaredFields = target.getClass().getDeclaredFields();
        List<Field> fields = new ArrayList<>(declaredFields.length);

        List<String> skipFields = new ArrayList<>();

        //过滤掉DtoSkip注解的字段
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(DtoSkip.class)) {
                skipFields.add(field.getName());
                continue;
            }
            fields.add(field);
        }

        //entity翻译成DTO,跳过忽略的字段
        BeanUtils.copyProperties(source, target, skipFields.toArray(new String[0]));

        Field[] superDeclaredFields = target.getClass().getSuperclass().getDeclaredFields();
        fields.addAll(Arrays.asList(superDeclaredFields));

        //遍历字段，找出 Formatter注解注释的字段,并翻译
        formatterHandler(target, fields);

        //脱敏

        return target;
    }

    /**
     * 翻译被Formatter注解的字段
     * @param target
     * @param fields
     * @param <T>
     */
    private static <T> void formatterHandler(T target, List<Field> fields) {
        //遍历字段，找出 Formatter注解注释的字段
        fields.stream().parallel().filter(field -> field.isAnnotationPresent(Formatter.class)).forEach(field -> {
            try {
                //判断是否有注解Formatter
                PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), target.getClass());
                Method readMethod = descriptor.getReadMethod();
                //读取属性值
                Object result = readMethod.invoke(target);
                if (result instanceof Boolean) {
                    result = (Boolean) result ? Enabled.YES.getValue() : Enabled.NO.getValue();
                }

                //非空判断
                if (result != null) {
                    Formatter annotation = field.getAnnotation(Formatter.class);

                    CacheHelper cacheHelper = ApplicationContextUtils.getBean(CacheHelper.class);
                    String key;
                    if (StringUtils.isBlank(annotation.key())) {
                        String dictCode = annotation.dictCode();
                        //获取对应字典中的值
                        key = RedisKeyEnum.DICT_PREFIX.getKey() + dictCode;
                    } else {
                        key = annotation.key();
                    }
                    //TODO 只需第一次从Redis获取并缓存
                    String dictValue = cacheHelper.hashGetString(key, result.toString());

                    if (StringUtils.isNotBlank(dictValue)) {
                        PropertyDescriptor descriptorTarget = new PropertyDescriptor(field.getName(), target.getClass());
                        if (StringUtils.isBlank(annotation.targetField())) {
                            Method writeMethod = descriptorTarget.getWriteMethod();
                            writeMethod.invoke(target, dictValue);
                        } else {
                            descriptorTarget = new PropertyDescriptor(annotation.targetField(), target.getClass());
                            if (descriptorTarget != null) {
                                descriptorTarget.getWriteMethod().invoke(target, dictValue);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
