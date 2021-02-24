package com.anjiplus.gaea.export;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKeyBuild;
import com.alibaba.excel.converters.DefaultConverterLoader;
import com.alibaba.excel.converters.bigdecimal.BigDecimalBooleanConverter;
import com.alibaba.excel.converters.bigdecimal.BigDecimalNumberConverter;
import com.alibaba.excel.converters.bigdecimal.BigDecimalStringConverter;
import com.alibaba.excel.converters.booleanconverter.BooleanBooleanConverter;
import com.alibaba.excel.converters.booleanconverter.BooleanNumberConverter;
import com.alibaba.excel.converters.booleanconverter.BooleanStringConverter;
import com.alibaba.excel.converters.bytearray.BoxingByteArrayImageConverter;
import com.alibaba.excel.converters.bytearray.ByteArrayImageConverter;
import com.alibaba.excel.converters.byteconverter.ByteBooleanConverter;
import com.alibaba.excel.converters.byteconverter.ByteNumberConverter;
import com.alibaba.excel.converters.byteconverter.ByteStringConverter;
import com.alibaba.excel.converters.date.DateNumberConverter;
import com.alibaba.excel.converters.date.DateStringConverter;
import com.alibaba.excel.converters.doubleconverter.DoubleBooleanConverter;
import com.alibaba.excel.converters.doubleconverter.DoubleNumberConverter;
import com.alibaba.excel.converters.doubleconverter.DoubleStringConverter;
import com.alibaba.excel.converters.file.FileImageConverter;
import com.alibaba.excel.converters.floatconverter.FloatBooleanConverter;
import com.alibaba.excel.converters.floatconverter.FloatNumberConverter;
import com.alibaba.excel.converters.floatconverter.FloatStringConverter;
import com.alibaba.excel.converters.inputstream.InputStreamImageConverter;
import com.alibaba.excel.converters.integer.IntegerBooleanConverter;
import com.alibaba.excel.converters.integer.IntegerNumberConverter;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.alibaba.excel.converters.longconverter.LongBooleanConverter;
import com.alibaba.excel.converters.longconverter.LongNumberConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.converters.shortconverter.ShortBooleanConverter;
import com.alibaba.excel.converters.shortconverter.ShortNumberConverter;
import com.alibaba.excel.converters.shortconverter.ShortStringConverter;
import com.alibaba.excel.converters.string.StringBooleanConverter;
import com.alibaba.excel.converters.string.StringErrorConverter;
import com.alibaba.excel.converters.string.StringNumberConverter;
import com.alibaba.excel.converters.string.StringStringConverter;
import com.alibaba.excel.converters.url.UrlImageConverter;
import com.anjiplus.gaea.export.config.GaeaExportProperties;
import com.anjiplus.gaea.export.utils.LocalDateTimeConverter;
import com.github.anji.plus.gaea.annotation.condition.ConditionalOnGaeaComponent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(GaeaExportProperties.class)
@ConditionalOnGaeaComponent(GaeaExportProperties.COMPONENT_NAME)
public class GaeaExportAutoConfiguration {

    /**
     * 自定义类型转化器
     */
    @Configuration
    public class CustomerDefaultConverterLoader {
        //存放写时用到的converter
        private static final String ALL_CONVERTER = "allConverter";
        //存放所有的converter
        private static final String WRITE_CONVERTER = "defaultWriteConverter";

        @Bean
        public DefaultConverterLoader init() throws IllegalAccessException {
            DefaultConverterLoader converters = new DefaultConverterLoader();
            Field[] fields = converters.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getType() == Map.class) {
                    Map<String, Converter> oldMap = (Map<String, Converter>) field.get(converters);
                    if (oldMap != null && !oldMap.isEmpty()) {
                        if (WRITE_CONVERTER.equalsIgnoreCase(field.getName())) {
                            putWriteConverter(oldMap, new LocalDateTimeConverter());
                        } else if (ALL_CONVERTER.equalsIgnoreCase(field.getName())) {
                            putAllConverter(oldMap, new LocalDateTimeConverter());
                        }
                        field.set(converters, oldMap);
                    } else {
                        setConverter(converters, field);
                    }
                }
            }
            return converters;
        }

        private void setConverter(DefaultConverterLoader converters, Field field) throws IllegalAccessException {
            if (WRITE_CONVERTER.equalsIgnoreCase(field.getName())) {
                Map<String, Converter> map = new HashMap<>(32);
                //我的LocalDateTimeConverter
                putWriteConverter(map, new LocalDateTimeConverter());
                //以下jar包自带的Converter
                putWriteConverter(map, new BigDecimalNumberConverter());
                putWriteConverter(map, new BooleanBooleanConverter());
                putWriteConverter(map, new ByteNumberConverter());
                putWriteConverter(map, new DateStringConverter());
                putWriteConverter(map, new DoubleNumberConverter());
                putWriteConverter(map, new FloatNumberConverter());
                putWriteConverter(map, new IntegerNumberConverter());
                putWriteConverter(map, new LongNumberConverter());
                putWriteConverter(map, new ShortNumberConverter());
                putWriteConverter(map, new StringStringConverter());
                putWriteConverter(map, new FileImageConverter());
                putWriteConverter(map, new InputStreamImageConverter());
                putWriteConverter(map, new ByteArrayImageConverter());
                putWriteConverter(map, new BoxingByteArrayImageConverter());
                putWriteConverter(map, new UrlImageConverter());
                field.set(converters, map);
            } else if (ALL_CONVERTER.equalsIgnoreCase(field.getName())) {
                Map<String, Converter> map = new HashMap<>(64);
                //我的LocalDateTimeConverter
                putAllConverter(map, new LocalDateTimeConverter());
                //以下jar包自带的Converter
                putAllConverter(map, new BigDecimalBooleanConverter());
                putAllConverter(map, new BigDecimalNumberConverter());
                putAllConverter(map, new BigDecimalStringConverter());

                putAllConverter(map, new BooleanBooleanConverter());
                putAllConverter(map, new BooleanNumberConverter());
                putAllConverter(map, new BooleanStringConverter());

                putAllConverter(map, new ByteBooleanConverter());
                putAllConverter(map, new ByteNumberConverter());
                putAllConverter(map, new ByteStringConverter());

                putAllConverter(map, new DateNumberConverter());
                putAllConverter(map, new DateStringConverter());

                putAllConverter(map, new DoubleBooleanConverter());
                putAllConverter(map, new DoubleNumberConverter());
                putAllConverter(map, new DoubleStringConverter());

                putAllConverter(map, new FloatBooleanConverter());
                putAllConverter(map, new FloatNumberConverter());
                putAllConverter(map, new FloatStringConverter());

                putAllConverter(map, new IntegerBooleanConverter());
                putAllConverter(map, new IntegerNumberConverter());
                putAllConverter(map, new IntegerStringConverter());

                putAllConverter(map, new LongBooleanConverter());
                putAllConverter(map, new LongNumberConverter());
                putAllConverter(map, new LongStringConverter());

                putAllConverter(map, new ShortBooleanConverter());
                putAllConverter(map, new ShortNumberConverter());
                putAllConverter(map, new ShortStringConverter());

                putAllConverter(map, new StringBooleanConverter());
                putAllConverter(map, new StringNumberConverter());
                putAllConverter(map, new StringStringConverter());
                putAllConverter(map, new StringErrorConverter());
                field.set(converters, map);
            }
        }

        private void putWriteConverter(Map<String, Converter> map, Converter converter) {
            map.put(ConverterKeyBuild.buildKey(converter.supportJavaTypeKey()), converter);
        }

        private void putAllConverter(Map<String, Converter> map, Converter converter) {
            map.put(ConverterKeyBuild.buildKey(converter.supportJavaTypeKey(), converter.supportExcelTypeKey()), converter);
        }
    }

}

