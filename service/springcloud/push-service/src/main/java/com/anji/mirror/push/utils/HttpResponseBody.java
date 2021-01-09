package com.anji.mirror.push.utils;

import java.util.List;
import java.util.Map;

/**
 * Http 响应 实体
 */
public class HttpResponseBody {

    /**
     * Http请求是否成功
     */
    private boolean success;

    /**
     * Http请求失败代码
     */
    private String errorCode;

    /**
     * Http请求失败信息
     */
    private String errorMsg;

    /**
     * Http响应Headers
     */
    private Map<String, String> headers;

    /**
     * Http响应Cookies
     */
    private List<String> cookies;

    /**
     * Http响应内容
     */
    private String resultString;

    /**
     * Http响应码
     */
    private int code;

    private HttpResponseBody(Map<String, String> headers, List<String> cookies, String resultString, int code, boolean success, String errorCode,
                             String errorMsg) {
        this.headers = headers;
        this.cookies = cookies;
        this.resultString = resultString;
        this.code = code;
        this.success = success;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public static HttpResponseBody error(String errorCode, String errorMsg) {
        return new HttpResponseBody(null, null, null, -1, false, errorCode, errorMsg);
    }

    public static HttpResponseBody ok(Map<String, String> headers, List<String> cookies, String resultString, int code) {
        return new HttpResponseBody(headers, cookies, resultString, code, true, null, null);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public List<String> getCookies() {
        return cookies;
    }

    public String getResultString() {
        return resultString;
    }

    public int getCode() {
        return code;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}