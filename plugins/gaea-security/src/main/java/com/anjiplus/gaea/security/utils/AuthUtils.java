package com.anjiplus.gaea.security.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.github.anji.plus.gaea.holder.UserContentHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 用户工具类
 * @author lirui
 * @since 2021-01-27
 */
public class AuthUtils {

    /**
     * 保存登录信息
     */
    public static final ThreadLocal<JSONObject> loginInfoThreadLocal = new ThreadLocal<>();


    /**
     * 获取请求体
     * @param request
     * @return
     */
    public static JSONObject getRequestBody(HttpServletRequest request) {
        if (loginInfoThreadLocal.get() != null) {
            return loginInfoThreadLocal.get();
        }
        try {
            //从请求体中获取数据
            JSONObject loginInfo = JSONObject.parseObject(request.getInputStream(), JSONObject.class, new Feature[0]);
            if (loginInfo != null) {
                loginInfoThreadLocal.set(loginInfo);
            }
            return loginInfo;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 获取密码
     * @param password
     * @return
     */
    public static String getPassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    public static void main(String[] args) {
        System.out.println(getPassword("d51e052f621c0e6e6a8a59ff2e0dd7b5"));
    }
}
