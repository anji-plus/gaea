package com.github.anji.plus.gaea.utils;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.github.anji.plus.gaea.constant.GaeaConstant.DATE_PATTERN;


/**
 * 工具类
 * @author lr
 * @since 2020-11-23
 */
public class GaeaUtils {

    private static Logger logger = LoggerFactory.getLogger(GaeaUtils.class);

    /**
     * 标识
     */
    private final static String SIGN = "h";

    /**
     * 大于
     */
    private final static String GT = ">";

    /**
     * 大于等于
     */
    private final static String GE = ">=";

    /**
     * 小于
     */
    private final static String LT = "<";

    /**
     * 小于等于
     */
    private final static String LE = "<=";

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
     *
     * 判断大于等于等表达式是否true,false
     * 0<x<10
     * 10<=x<100
     * 100<=x<∞
     * @param value
     * @param match
     * @return
     */
    public static boolean match(String value, String match) {
        try{
            //大于：h>60
            if (match.contains(GT) && !match.contains(GE)) {
                String[] split = match.split(GT);
                //第一位是标识位h且是h>60形式的
                if (StringUtils.equals(SIGN, split[0]) && split.length == 2) {
                    return Maths.gt(value, split[1]);
                }
            }

            //大于等于：h>=60
            if (match.contains(GE)) {
                String[] split = match.split(GE);
                //第一位是标识位h且是h>=60形式的
                if (StringUtils.equals(SIGN, split[0]) && split.length == 2) {
                    return Maths.ge(value, split[1]);
                }
            }

            //小于：h<60
            if (match.contains(LT) && !match.contains(LE)) {
                String[] split = match.split(LT);
                //第一位是标识位h且是h<60形式的
                if (StringUtils.equals(SIGN, split[0]) && split.length == 2) {
                    return Maths.gt(split[1], value);
                }
            }

            //小于等于：h<=60
            if (!match.contains(LT) && match.contains(LE)) {
                String[] split = match.split(LE);
                //第一位是标识位h且是h>=60形式的
                if (StringUtils.equals(SIGN, split[0]) && split.length == 2) {
                    return Maths.ge(split[1], value);
                }
            }

            //大于等于60>h>0
            if (match.contains(GT) && !match.contains(GE)) {
                String[] split = match.split(GE);
                return Maths.gt(split[0], value) && Maths.gt(value, split[2]);
            }

            //大于等于60>=h>=0
            if (match.contains(GE) && !match.contains(GT)) {
                String[] split = match.split(LE);
                return Maths.ge(split[0], value) && Maths.ge(value, split[2]);
            }

            //多个小于:0<h<60
            if (match.contains(LT) && !match.contains(LE)) {
                String[] split = match.split(LT);
                //多个小于 0<h<60
                return Maths.gt(value, split[0]) && Maths.gt(split[2], value);

            }
            //多个小于等于0<=h<=60
            if (match.contains(LE) && !match.contains(LT)) {
                String[] split = match.split(LE);
                return Maths.ge(value, split[0]) && Maths.ge(split[2], value);
            }

            //小于等于 0<=h<60 或 0<h<=60
            if (match.contains(LE) && match.contains(LT)) {
                //判断<=的位置
                int ltIndex = match.indexOf(LT);
                //判断<的位置
                int leIndex = match.indexOf(LE);

                String[] split = match.split("<=|<");
                //0<h<=60
                if (ltIndex < leIndex) {
                    return Maths.gt(value, split[0]) && Maths.ge(split[2], value);
                }
                //0<=h<60
                if (ltIndex > leIndex) {
                    return Maths.ge(value, split[0]) && Maths.gt(split[2], value);
                }
            }

            //大于等于 60>h>=0 或 60>=h>0
            if (match.contains(GT) && match.contains(GE)) {
                //判断<=的位置
                int gtIndex = match.indexOf(GT);
                //判断<的位置
                int geIndex = match.indexOf(GE);

                String[] split = match.split(">=|>");
                //60>h>=0
                if (gtIndex < geIndex) {
                    return Maths.gt(split[0], value) && Maths.ge(value, split[2]);
                }
                //60>=h>0
                if (gtIndex > geIndex) {
                    return Maths.ge(split[0], value) && Maths.gt(value, split[2]);
                }
            }

        }catch (Exception e) {
            logger.error("滚装海运匹配高低仓时，工具类异常", e);
            return false;
        }

        return false;
    }
}
