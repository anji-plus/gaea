package com.anji.mirror.gateway.utils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SignUtil {

    private static final Logger logger = LoggerFactory.getLogger(SignUtil.class);

    public static String signTemp(Map<String,String> params, String insertString, String appendString){
        String signTemp = signTemp(params, new String[]{}, appendString);
        if(StringUtils.isNotBlank(insertString)){
            signTemp = insertString + signTemp;
        }
        return signTemp;
    }

    public static String signTemp(Map<String,String> params, String appendString){
        return signTemp(params, new String[]{"sign"}, appendString);
    }

    /** 将params，按key升序，拼装成key1=val1&key2=val2&key3
     * @param params
     * @param ignoreKeys
     * @param appendString
     * @return
     */
    public static String signTemp(Map<String,String> params, String[] ignoreKeys,  String appendString){
        List<String> ignoreKeyList = new ArrayList<>();
        if(ignoreKeys != null){
            ignoreKeyList = Arrays.asList(ignoreKeys);
        }
        List<String> paramKeys = new ArrayList<String>(params.keySet());
        Collections.sort(paramKeys);
        StringBuffer sb = new StringBuffer();
        for (String paramKey : paramKeys) {
            Object paramValue = params.get(paramKey);
            if (paramValue == null || ignoreKeyList.contains(paramKey)) {
                continue;
            }
            String value = paramValue.toString();
            if (value.trim().length() > 0) {
                sb.append(paramKey).append("=").append(paramValue).append("&");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        String result = sb.append(appendString).toString();
        logger.info("sign src string: {}", result);
        return result;
    }

    public static String signTemp(JSONObject params, List<String> ignoreKeyList,  String insertString, String appendString){
        String signTemp = signTemp(params, ignoreKeyList, appendString);
        if(StringUtils.isNotBlank(insertString)){
            signTemp = insertString + signTemp;
        }
        return signTemp;
    }

    public static String signTemp(JSONObject params, String insertString, String appendString){
        return signTemp(params, new ArrayList<>(), appendString);
    }
    /** 将params，按key升序，拼装成key1=val1&key2=val2&key3
     * @param params
     * @param ignoreKeyList
     * @param appendString
     * @return
     */
    public static String signTemp(JSONObject params, List<String> ignoreKeyList,  String appendString){
        List<String> paramKeys = new ArrayList<String>(params.keySet());
        Collections.sort(paramKeys);
        StringBuffer sb = new StringBuffer();
        for (String paramKey : paramKeys) {
            Object paramValue = params.get(paramKey);
            if (paramValue == null || ignoreKeyList.contains(paramKey)) {
                continue;
            }

            //如果paramValue是JsonObject或者JsonArray，对象中的key会进行升序，所有client端在提交前，也要进行升序
            String value = JSONObject.toJSONString(paramValue, SerializerFeature.MapSortField, SerializerFeature.WriteMapNullValue);

            if (value.trim().length() > 0) {
                sb.append("&").append(paramKey).append("=").append(value);
            }
        }
        String result = sb.append(appendString).toString();
        logger.info("sign src string: {}", result);
        return result;
    }

    /** 将params按key升序，a=1&b=2，在末尾拼上appendString，再求md5
     * @param params
     * @param appendString &key=ewrqowerusdfas412934342
     * @return
     */
    public static String md5Sign(Map<String,String> params, String appendString){
        return md5Sign(params, new String[]{"sign"}, appendString);
    }
    public static String md5Sign(Map<String,String> params, String[] ignoreKeys, String appendString) {
        String signTemp = signTemp(params, ignoreKeys, appendString);
        return DigestUtils.md5Hex(signTemp);
    }

    /** 将params按key升序，a=1&b=2，在开头拼上insertString，在末尾拼上appendString，再求md5
     * @param params
     * @param insertString
     * @param appendString
     * @return
     */
    public static String md5Sign(Map<String,String> params, String insertString, String appendString){
        String signTemp = signTemp(params, insertString, appendString);
        return DigestUtils.md5Hex(signTemp);
    }
    public static String md5Sign(JSONObject params, String insertString, String appendString){
        String signTemp = signTemp(params, Arrays.asList("sign","currentPage","pageSize","orderBy"), insertString, appendString);
        return DigestUtils.md5Hex(signTemp);
    }
    public static String md5Sign(JSONObject params, String insertString, String appendString,List<String> ignoreKeyList){
        List<String> ignoreKeyLists = new ArrayList<>(Arrays.asList("sign", "currentPage", "pageSize", "orderBy"));
        if (Objects.nonNull(ignoreKeyList)) {
            ignoreKeyLists.addAll(ignoreKeyList);
        }
        String signTemp = signTemp(params, ignoreKeyLists, insertString, appendString);
        return DigestUtils.md5Hex(signTemp);
    }

}
