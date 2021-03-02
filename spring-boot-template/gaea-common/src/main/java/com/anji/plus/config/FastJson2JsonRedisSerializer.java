package com.anji.plus.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * <pre>
 * FastJson2JsonRedisSerializer
 * </pre>
 *
 * @author peiyanni
 * @version FastJson2JsonRedisSerializer.java
 */
public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {

    private static final String UTF_8 = "UTF-8";
    private static final String BASE_PACKAGE = "com.anji.plus";

    // 解决高版本fastJson autoType is not support错误
    static {
        ParserConfig.getGlobalInstance().addAccept(BASE_PACKAGE);
    }

    public static final Charset DEFAULT_CHARSET = Charset.forName(UTF_8);

    private Class<T> clazz;

    public FastJson2JsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);

        return JSON.parseObject(str, clazz);
    }

}
