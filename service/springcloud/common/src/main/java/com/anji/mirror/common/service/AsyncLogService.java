package com.anji.mirror.common.service;

import com.anji.mirror.common.model.LogVO;

/**
 * 异步调用日志服务
 * Created by raodeming on 2020/9/3.
 */
public interface AsyncLogService {


    default void saveSysLog(LogVO logVO){}

    /**
     *
     * @param userId 用户ID
     * @param userName 用户名称
     * @param requestUrl 请求路径
     * @param pageTitle 页面或按钮标题
     * @param requestParam 请求参数
     * @param responseParam 响应参数
     * @param sourceIp 来源IP
     */
    default void saveTransferLog(long userId, String userName, String requestUrl,
                                String pageTitle, String requestParam, String responseParam, String sourceIp) {
    }


    /**
     *
     * @param userId 用户ID
     * @param userName 用户名称
     * @param requestUrl 请求路径
     * @param pageTitle 页面或按钮标题
     * @param requestParam 请求参数
     * @param responseParam 响应参数
     */
    default void saveTransferLog(long userId, String userName, String requestUrl,
                                String pageTitle, String requestParam, String responseParam) {
    }
}
