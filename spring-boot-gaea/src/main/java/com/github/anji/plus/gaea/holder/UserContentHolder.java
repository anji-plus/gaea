package com.github.anji.plus.gaea.holder;

import org.springframework.util.Assert;

/**
 * 用户上下文操作
 * @author lirui
 * @since 2020-11-23
 */
public class UserContentHolder {

    private static final ThreadLocal<UserContext> userContextThreadLocal = new InheritableThreadLocal<>();

    /**
     * 清空
     */
    public static void clearContext() {
        userContextThreadLocal.remove();
    }

    /**
     * 获取用户信息
     * @return
     */
    public static  UserContext getContext() {
        UserContext ctx = userContextThreadLocal.get();

        if (ctx == null) {
            ctx = createEmptyContext();
            userContextThreadLocal.set(ctx);
        }

        return ctx;
    }

    /**
     * 设置用户信息
     * @param context
     */
    public static  void setContext(UserContext context) {
        Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
        userContextThreadLocal.set(context);
    }

    /**
     * 创建空的用户信息
     * @return
     */
    public static  UserContext createEmptyContext() {
        return new UserContext();
    }
}
