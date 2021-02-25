package com.anji.plus.gaea.event.listener;


import com.anji.plus.gaea.event.LoginEvent;
import org.springframework.context.ApplicationListener;

/**
 * 登录监听
 * @author lr
 * @since 2021-01-25
 */
public class LoginApplicationListener implements ApplicationListener<LoginEvent<String>> {

    @Override
    public void onApplicationEvent(LoginEvent<String> event) {

        //用户名
        String username = event.getT();

        //具体登录逻辑处理
    }
}
