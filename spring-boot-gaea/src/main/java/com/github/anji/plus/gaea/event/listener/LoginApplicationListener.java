package com.github.anji.plus.gaea.event.listener;


import com.github.anji.plus.gaea.event.LoginEvent;
import org.springframework.context.ApplicationListener;

/**
 * 登录监听
 * @author lr
 * @since 2020-12-23
 */
public class LoginApplicationListener implements ApplicationListener<LoginEvent<String>> {

    @Override
    public void onApplicationEvent(LoginEvent<String> event) {

        //用户名
        String username = event.getT();

        //具体登录逻辑处理
    }
}
