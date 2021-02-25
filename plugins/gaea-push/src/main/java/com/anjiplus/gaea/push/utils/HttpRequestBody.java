package com.anjiplus.gaea.push.utils;

import org.springframework.http.HttpMethod;

import java.util.Map;


/**
 * HTTP请求实体
 */
public class HttpRequestBody {

    /**
     * 是否HTTPS请求，默认为否
     */
    private boolean isHttps = false;

    /**
     * 编码，默认UTF-8
     */
    private String charSet = "UTF-8";

    /**
     * http请求方法，默认GET
     */
    private HttpMethod method = HttpMethod.GET;

    /**
     * http请求URL，不可为空
     */
    private String url;

    /**
     * http请求头
     */
    private Map<String, String> headers;

    /**
     * http请求Cookies 按照Cookies拼接格式拼接
     */
    private String cookies;

    /**
     * 参数字符串
     */
    private String queryString;

    public HttpRequestBody(String url, String charSet, HttpMethod method, Map<String, String> headers, String cookies, Map<String, String> params) {
        this(url, charSet, method, headers, cookies, params, null);
    }

    public HttpRequestBody(String url, String charSet, String paramsString, HttpMethod method, Map<String, String> headers, String cookies) {
        this(url, charSet, method, headers, cookies, null, paramsString);
    }

    public String getUrl() {
        return url;
    }

    public boolean isHttps() {
        return isHttps;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getCookies() {
        return cookies;
    }

    public String getCharSet() {
        return charSet;
    }

    public String getQueryString() {
        return this.queryString;
    }

    private HttpRequestBody(String url, String charSet, HttpMethod method, Map<String, String> headers, String cookies, Map<String, String> params,
                            String paramsString) {
        if (url == null || url.trim().length() == 0) {
            throw new IllegalArgumentException("url is null");
        }
        if (charSet == null || charSet.trim().length() == 0) {
            this.charSet = charSet;
        }
        if (method != null) {
            this.method = method;
        }
        this.headers = headers;
        this.cookies = cookies;
        this.queryString = this.generateParamsString(params, paramsString);
        this.url = this.generateUrl(url);
        if (this.url.startsWith("https")) {
            this.isHttps = true;
        }
    }

    private String generateParamsString(Map<String, String> params, String paramsString) {

        String queryString = "";

        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                queryString += "&" + entry.getKey() + "=" + entry.getValue();
            }
            if (queryString.length() > 0) {
                queryString = queryString.substring(1);
            }
        } else if (paramsString != null && paramsString.trim().length() > 0) {
            queryString = paramsString;
        }

        return queryString;

    }

    private String generateUrl(String requestUrl) {

        if (!requestUrl.startsWith("http")) {
            requestUrl = "http://" + requestUrl;
        }

        if (this.method.equals(HttpMethod.POST)) {
            return requestUrl;
        }

        if (this.queryString.equals("")) {
            return requestUrl;
        }

        if (requestUrl.contains("?")) {
            requestUrl += "&" + this.queryString;
        } else {
            requestUrl += "?" + this.queryString;
        }

        return requestUrl;
    }

}
