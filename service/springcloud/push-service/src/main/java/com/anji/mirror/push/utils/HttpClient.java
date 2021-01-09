package com.anji.mirror.push.utils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * HTTP客户端
 */
public class HttpClient {

    public static enum HttpMethod {

        GET, POST

    }

    /**
     * 连接超时时限，0无限制,默认30s,单位Seconds
     */
    private int httpConnectionTimeOut = 30;

    /**
     * 响应超时时限，0无限制,默认30s,单位Seconds
     */
    private int httpReadTimeOut = 30;

    /**
     * 是否打印日志
     */
    private boolean debug = false;

    public HttpClient(int httpConnectionTimeOut, int httpReadTimeOut, boolean debug) {
        this.httpConnectionTimeOut = httpConnectionTimeOut;
        this.httpReadTimeOut = httpReadTimeOut;
        this.debug = debug;
    }

    public HttpClient() {

    }

    /**
     * 发送请求
     *
     * @param body
     * @return
     */
    public HttpResponseBody service(HttpRequestBody body) {
        HttpResponseBody result = null;
        Exception ex = null;
        HttpURLConnection conn = null;
        try {
            conn = this.createConnection(body);
            this.fillConnection(conn, body);
            this.request(conn, body);
            result = this.handleResponse(conn, body);
        } catch (IOException e) {
            ex = e;
            result = HttpResponseBody.error("E001", "client timeout");
        } catch (Exception e) {
            ex = e;
            result = HttpResponseBody.error("E002", "client other error");
        } finally {
            this.closeCoonection(conn);
        }

        if (debug && ex != null) {
            ex.printStackTrace();
        }

        return result;
    }

    private HttpURLConnection createConnection(HttpRequestBody body) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        URL console = new URL(body.getUrl());
        HttpURLConnection conn;
        if (body.isHttps()) {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
            HttpsURLConnection sconn = (HttpsURLConnection) console.openConnection();
            sconn.setSSLSocketFactory(sc.getSocketFactory());
            sconn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn = sconn;
        } else {
            conn = (HttpURLConnection) console.openConnection();
        }
        return conn;
    }

    /**
     * 加载Http请求参数信息
     *
     * @param conn
     * @param body
     * @throws ProtocolException
     */
    private void fillConnection(HttpURLConnection conn, HttpRequestBody body) throws ProtocolException {
        this.fillTimeout(conn);
        this.filleMethod(conn, body);
        this.fillHeaders(conn, body);
        this.fillCookies(conn, body);
    }

    /**
     * 加载连接超时参数
     *
     * @param conn
     */
    private void fillTimeout(HttpURLConnection conn) {

        if (httpConnectionTimeOut != 0) {
            conn.setConnectTimeout(httpConnectionTimeOut * 1000);
        }

        if (httpReadTimeOut != 0) {
            conn.setReadTimeout(httpReadTimeOut * 1000);
        }

    }

    /**
     * 加载Http方法参数
     *
     * @param conn
     * @param body
     * @throws ProtocolException
     */
    private void filleMethod(HttpURLConnection conn, HttpRequestBody body) throws ProtocolException {
        conn.setRequestMethod(body.getMethod().toString().toUpperCase());
    }

    /**
     * 加载Http头信息
     *
     * @param conn
     * @param body
     */
    private void fillHeaders(HttpURLConnection conn, HttpRequestBody body) {
        if (!this.isEmpty(body.getHeaders())) {
            for (Entry<String, String> entry : body.getHeaders().entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 加载Http Cookie
     *
     * @param conn
     * @param body
     */
    private void fillCookies(HttpURLConnection conn, HttpRequestBody body) {
        if (!this.isEmpty(body.getCookies())) {
            conn.setRequestProperty("Cookie", body.getCookies());
        }
    }

    private void request(HttpURLConnection conn, HttpRequestBody body) throws IOException {
        conn.setDoOutput(true);
        // conn.connect();
        if (body.getQueryString() != null && body.getQueryString().length() > 0) {
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.write(body.getQueryString().getBytes(body.getCharSet()));
            out.flush();
            out.close();
        }
    }

    private HttpResponseBody handleResponse(HttpURLConnection conn, HttpRequestBody body) throws IOException {
        // 返回头
        Map<String, String> resultHeaders = this.getHeaders(conn, body);
        // Cookies
        List<String> cookies = this.getCookies(conn, body);
        // 返回bosy实体
        String resultBody = this.getResultBody(conn, body);
        // http 状态码
        int code = conn.getResponseCode();
        return HttpResponseBody.ok(resultHeaders, cookies, resultBody, code);
    }

    private Map<String, String> getHeaders(HttpURLConnection conn, HttpRequestBody body) throws UnsupportedEncodingException {
        Map<String, String> resultHeaders = new HashMap<String, String>();
        Map<String, List<String>> header = conn.getHeaderFields();
        if (!this.isEmpty(header)) {
            for (Entry<String, List<String>> entry : header.entrySet()) {
                if (!"Set-Cookie".equalsIgnoreCase(entry.getKey())) {
                    String valuer = "";
                    if (entry.getValue() != null && entry.getValue().size() > 0) {
                        for (String value : entry.getValue()) {
                            valuer += new String(value.getBytes("ISO-8859-1"), body.getCharSet()) + ",";
                        }
                        valuer = valuer.substring(0, valuer.length() - 1);
                    }
                    resultHeaders.put(entry.getKey(), valuer);
                }
            }
        }
        return resultHeaders;
    }

    private List<String> getCookies(HttpURLConnection conn, HttpRequestBody body) throws UnsupportedEncodingException {
        List<String> resultC = new ArrayList<String>();
        List<String> cookies = null;
        Map<String, List<String>> header = conn.getHeaderFields();
        if (!this.isEmpty(header)) {
            cookies = header.get("Set-Cookie");
        }
        if (cookies != null) {
            for (String cookie : cookies) {
                resultC.add(new String(cookie.getBytes("ISO-8859-1"), body.getCharSet()));
            }
        }
        return cookies;
    }

    private String getResultBody(HttpURLConnection conn, HttpRequestBody body) throws IOException {
        String resultBody = null;
        InputStream is = conn.getInputStream();
        if (is != null) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            is.close();
            byte[] resultBytes = outStream.toByteArray();
            resultBody = new String(resultBytes, body.getCharSet());
        }
        return resultBody;
    }

    private void closeCoonection(HttpURLConnection conn) {
        if (conn != null)
            conn.disconnect();
    }

    private boolean isEmpty(Object obj) {

        if (obj == null) {
            return true;
        }

        if (obj instanceof String) {
            return "".equals(((String) obj).trim());
        }

        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).size() == 0;
        }

        return true;
    }

    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

}
