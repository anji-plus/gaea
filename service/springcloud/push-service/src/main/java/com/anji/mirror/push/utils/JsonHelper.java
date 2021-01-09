package com.anji.mirror.push.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * json util
 */
public class JsonHelper {

    private static GsonBuilder builder;

    private static Gson gson;

    static {
        builder = new GsonBuilder();
        gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss").disableHtmlEscaping().serializeNulls().create();
    }

    private JsonHelper() {

    }

    /**
     * 将对象转换为json串
     *
     * @param obj
     * @return
     */
    public static String toJsonString(Object obj) {
        if (obj == null) {
            return null;
        }
        String json = null;
        json = gson.toJson(obj);
        return json;
    }

    /**
     * 将对象转换为json串，自定义日期转换规则
     *
     * @param obj
     * @param datePattern
     * @return
     */
    public static String toJsonString(Object obj, String datePattern) {
        if (obj == null) {
            return null;
        }
        String json = null;
        json = builder.setDateFormat(datePattern).serializeNulls().create().toJson(obj);
        return json;
    }

    /**
     * 将json串转换为对象
     *
     * @param clazz
     * @param jsonString
     * @return
     */
    public static <T> T fromJson(Class<T> clazz, String jsonString) {
        if (jsonString == null) {
            return null;
        }
        T t = null;
        t = gson.fromJson(jsonString, clazz);
        return t;
    }

    /**
     * 将json串转换为对象
     *
     * @param clazz
     * @param jsonString
     * @return
     */
    public static <T> T fromJson(Class<T> clazz, String jsonString, String datePattern) {
        if (jsonString == null) {
            return null;
        }
        T t = null;
        t = builder.setDateFormat(datePattern).serializeNulls().create().fromJson(jsonString, clazz);
        return t;
    }

    public static Gson getGson() {
        return gson;
    }

    public static Gson getGson(String datePattern) {
        return builder.setDateFormat(datePattern).disableHtmlEscaping().serializeNulls().create();
    }

    public static GsonBuilder newGsonBuilder() {
        return new GsonBuilder();
    }

}
