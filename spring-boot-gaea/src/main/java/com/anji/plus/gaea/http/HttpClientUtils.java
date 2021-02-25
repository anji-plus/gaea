package com.anji.plus.gaea.http;

import com.alibaba.fastjson.JSONObject;
import com.anji.plus.gaea.http.ssl.SslSocketClient;
import okhttp3.*;

import java.io.IOException;
import java.time.Duration;

/**
 * 客户端工具类
 * @author lr
 * @since 2021-02-07
 */
public class HttpClientUtils {

    private final static OkHttpClient httpClient;

    /**
     * 媒体类型
     */
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    static  {
        httpClient = new OkHttpClient.Builder()
                .sslSocketFactory(SslSocketClient.getSSLSocketFactory(), SslSocketClient.trustManager())
                .hostnameVerifier(SslSocketClient.getHostnameVerifier())
                .connectTimeout(Duration.ofSeconds(90))
                .readTimeout(Duration.ofSeconds(90))
                .build();
    }

    /**
     * GET 同步发送
     * @param url
     * @param headers
     * @return
     * @throws IOException
     */
    public static Response get(String url, Headers headers) throws IOException {


        Request request = new Request.Builder().url(url).headers(headers).get().build();
        return httpClient.newCall(request).execute();
    }

    /**
     * GET 异步发送
     * @param url
     * @param headers
     * @param callback
     * @return
     */
    public static void getAsyn(String url, Headers headers, Callback callback) {
        Request request = new Request.Builder().url(url).headers(headers).get().build();
        httpClient.newCall(request).enqueue(callback);
    }


    /**
     * POST 同步发送
     * @param url
     * @param headers
     * @return
     * @throws IOException
     */
    public static Response post(String url, Headers headers, Object body) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE, JSONObject.toJSONString(body));
        Request request = new Request.Builder().url(url).headers(headers).post(requestBody).build();
        return httpClient.newCall(request).execute();
    }

    /**
     * POST 异步发送
     * @param url
     * @param headers
     * @param callback
     * @return
     */
    public static void postAsyn(String url, Headers headers, Object body, Callback callback) {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE, JSONObject.toJSONString(body));
        Request request = new Request.Builder().url(url).headers(headers).post(requestBody).build();
        httpClient.newCall(request).enqueue(callback);
    }


    /**
     * put 同步发送
     * @param url
     * @param headers
     * @return
     * @throws IOException
     */
    public static Response put(String url, Headers headers, Object body) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE, JSONObject.toJSONString(body));
        Request request = new Request.Builder().url(url).headers(headers).put(requestBody).build();
        return httpClient.newCall(request).execute();
    }

    /**
     * POST 异步发送
     * @param url
     * @param headers
     * @param callback
     * @return
     */
    public static void putAsyn(String url, Headers headers, Object body, Callback callback) {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE, JSONObject.toJSONString(body));
        Request request = new Request.Builder().url(url).headers(headers).put(requestBody).build();
        httpClient.newCall(request).enqueue(callback);
    }


    /**
     * DELETE 同步发送
     * @param url
     * @param headers
     * @return
     * @throws IOException
     */
    public static Response del(String url, Headers headers) throws IOException {

        Request request = new Request.Builder().url(url).headers(headers).delete().build();
        return httpClient.newCall(request).execute();
    }

    /**
     * DELETE 异步发送
     * @param url
     * @param headers
     * @param callback
     * @return
     */
    public static void delAsyn(String url, Headers headers, Callback callback) {
        Request request = new Request.Builder().url(url).headers(headers).delete().build();
        httpClient.newCall(request).enqueue(callback);
    }
}
