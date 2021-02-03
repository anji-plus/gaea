package com.github.anji.plus.gaea.utils;


import com.alibaba.fastjson.JSONObject;
import com.github.anji.plus.gaea.bean.KeyValue;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.anji.plus.gaea.constant.GaeaConstant.DATE_PATTERN;


/**
 * 工具类
 * @author lr
 * @since 2021-01-12
 */
public abstract class GaeaUtils {

    /**
     * 盐值
     */
    private static String SLAT = "354816d26912441ab280f08831c38453";


    /**
     * 驼峰转下划线
     * @param source
     * @return
     */
    public static String camelToUnderline(String source) {
        return com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(source);
    }

    /**
     * 获取passkey.
     * @param id
     * @return 密码
     */
    public static String getPassKey( long id) {
        String tokenTmp = DigestUtils.md5DigestAsHex((SLAT + "_" + id).getBytes(Charset.forName("UTF-8")));
        return tokenTmp;
    }


    /**
     * 格式化日期
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return new SimpleDateFormat(DATE_PATTERN).format(date);
    }


    /**
     * 将对象转换为Json字符串
     * @param object
     * @return
     */
    public static String toJSONString(Object object) {
        return JSONObject.toJSONString(object);

    }

    /**
     * map转换KeyValue
     * @param map
     * @return
     */
    public static List<KeyValue> formatKeyValue(Map<? extends Object,String> map) {
        return map.entrySet().
                stream()
                .map(entry -> new KeyValue(entry.getKey(),entry.getValue()))
                .collect(Collectors.toList());
    }
}
