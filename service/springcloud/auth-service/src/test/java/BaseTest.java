import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.*;

import static com.alibaba.fastjson.serializer.SerializerFeature.*;

public class BaseTest {

    public JSONObject post(String api, String params, String token){
        //封装成网关的请求报文
        JSONObject gateRequest = buildGatewayRequest(params, token);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Object> request = new HttpEntity<Object>(gateRequest.toJSONString(), headers);

        RestTemplate restTemplate = new RestTemplate();
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig c = new FastJsonConfig();
        c.setSerializerFeatures(WriteMapNullValue,WriteNullNumberAsZero,
                WriteNullListAsEmpty,WriteNullStringAsEmpty,WriteNullBooleanAsFalse
        );
        converter.setFastJsonConfig(c);
        restTemplate.getMessageConverters().clear();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(converter);
        ResponseEntity<JSONObject> ret = restTemplate.postForEntity("http://10.108.26.151:8080"+api, request, JSONObject.class);
        JSONObject res = (ret.getBody());
        return res;
    }

    public JSONObject buildGatewayRequest(String paramsStr, String token){
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        long time = nowTime.getTime();
        JSONObject params = JSONObject.parseObject(paramsStr);

        String signTemp = signTemp(params, new String[]{"sign","currentPage","pageSize","orderBy"});
        String insertString = String.format("time=%s", time);
        String afterString = String.format("&token=%s", token);
        signTemp = insertString + signTemp + afterString;
        String sign = DigestUtils.md5Hex(signTemp);

        JSONObject gatewayRequest = new JSONObject();
        gatewayRequest.put("isFrom", "PC");
        gatewayRequest.put("data", params);
        gatewayRequest.put("time", time);
        gatewayRequest.put("token", token);
        gatewayRequest.put("sign", sign);

        return gatewayRequest;
    }

    public String signTemp(JSONObject params, String[] ignoreKeys){
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
            String value = "";
            if(paramValue instanceof String){
                value = (String)paramValue;
            }
            if(paramValue instanceof Number){
                value = String.valueOf(paramValue);
            }
            if(paramValue instanceof Boolean){
                if((Boolean)paramValue == null){
                    value = null;
                }
                if(((Boolean)paramValue).booleanValue()){
                    value = "true";
                }
                if(((Boolean)paramValue).booleanValue() == false){
                    value = "false";
                }
            }
            if (paramValue instanceof JSONObject) {
                value = ((JSONObject) paramValue).toJSONString();
            }
            if (paramValue instanceof Map) {
                value = (new JSONObject((Map) paramValue)).toJSONString();
            }

            if (paramValue instanceof JSONArray) {
                value = ((JSONArray) paramValue).toJSONString();
            }
            if (paramValue instanceof List) {
                value = (new JSONArray((List) paramValue)).toJSONString();
            }

            if (value.trim().length() > 0) {
                sb.append("&").append(paramKey).append("=").append(paramValue);
            }
        }
        String result = sb.toString();
        return result;
    }
}
