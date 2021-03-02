package com.anji.plus.gaea.annotation.resolver;

import com.anji.plus.gaea.utils.GaeaMaskUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.anji.plus.gaea.annotation.GaeaMask;

import java.io.IOException;

/**
 * 数据脱敏注解解析
 * @author lr
 * @since 2021-02-05
 * @see JsonSerialize#using()
 */
public class GaeaMaskJsonSerialize extends JsonSerializer<String> implements ContextualSerializer {

    /**
     * 脱敏注解
     */
    private GaeaMask gaeaMask;

    public GaeaMaskJsonSerialize(){}

    public GaeaMaskJsonSerialize(GaeaMask gaeaMask) {
        this.gaeaMask = gaeaMask;
    }

    /**
     * Jackson序列化时会调用该方法，自定义属性值
     * @param value
     * @param gen
     * @param serializers
     * @throws IOException
     */
    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        String result = value;
        //脱敏注解
        if (gaeaMask != null) {
            MaskEnum type = gaeaMask.type();

            //当不是通用类型时，忽略GaeaMask注解其他字段
            if (type != MaskEnum.COMMON) {
                result = GaeaMaskUtils.mask(value, type.getPattern(), type.getReplacement());
            } else {
                String pattern = type.getPattern();
                int left = gaeaMask.left();
                int right = gaeaMask.right();

                //当设置的左右长度大于对应值的长度时，直接采用默认默认脱敏规则
                String patternFormat;
                if (left + right >= value.length()) {
                    patternFormat = GaeaMaskUtils.defaultPattern;
                } else {
                    patternFormat = String.format(pattern, gaeaMask.left(), gaeaMask.right());
                }
                result = GaeaMaskUtils.mask(value, patternFormat, type.getReplacement());
            }

        }
        gen.writeString(result);
    }

    /**
     * Jackson序列化时会调用该方法创建JsonSerializer
     * @param prov
     * @param property
     * @return
     * @throws JsonMappingException
     */
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {

        if (property != null) {
            gaeaMask = property.getAnnotation(GaeaMask.class);
            if (gaeaMask != null) {
                return new GaeaMaskJsonSerialize(gaeaMask);
            }
            return prov.findValueSerializer(property.getType(), property);
        }

        return NullSerializer.instance;
    }
}
