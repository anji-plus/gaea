package com.anji.mirror.gateway.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Iterator;
import java.util.Set;

/**
 * @author anji gaea teams
 * @Date: 2020/11/3
 * @Description:
 */
public class JsonTrimUtil {

    /**
     * 去除json value前后空格
     * @param jsonStr
     * @return
     */
    public static JSONObject jsonStrTrim(String jsonStr) {
        JSONObject reagobj = JSONObject.parseObject(jsonStr);
        return jsonStrTrim(reagobj);
    }

    /**
     * 去除json value前后空格
     * @param json
     * @return
     */
    public static JSONObject jsonStrTrim(JSONObject json) {
        JSONObject jsonObject = json;
        // 取出 jsonObject 中的字段的值的空格
        Set<String> keySet = jsonObject.keySet();
        Iterator itt = keySet.iterator();
        while (itt.hasNext()) {
            String key = itt.next().toString();
            Object value = jsonObject.get(key);
            if (value == null) {
                continue;
            }
            if (value instanceof String) {
                String stringValue = (String) value;
                jsonObject.put(key, stringValue.trim());
            } else {
                if (value instanceof JSONArray) {
                    JSONArray jsonArray = (JSONArray) value;
                    jsonObject.put(key, jsonStrTrim(jsonArray));
                } else if (value instanceof JSONObject) {
                    JSONObject jsonO = (JSONObject) value;
                    jsonObject.put(key, jsonStrTrim(jsonO));
                } else {
                    jsonObject.put(key, value);
                }

            }
        }
        return jsonObject;
    }


    /**
     * 去除json value前后空格
     * @param arr
     * @return
     */
    public static JSONArray jsonStrTrim(JSONArray arr) {
        if (arr != null && arr.size() > 0) {
            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i) instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) arr.get(i);
                    arr.set(i, jsonStrTrim(jsonObject));
                } else if (arr.get(i) instanceof JSONArray) {
                    JSONArray arrayObj = (JSONArray) arr.get(i);
                    arr.set(i, jsonStrTrim(arrayObj));
                } else {
                    Object value = arr.get(i);
                    arr.set(i, value.toString().trim());
                }
            }
        }
        return arr;
    }

}
