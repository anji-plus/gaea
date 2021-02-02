package com.github.anji.plus.modules.user.service;

import com.github.anji.plus.modules.user.dao.entity.GaeaUser;
import com.github.anji.plus.modules.user.controller.param.GaeaUserParam;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;

/**
 * 用户表(GaeaUser)Service
 *
 * @author lirui
 * @since 2021-02-02 13:38:12
 */
public interface GaeaUserService extends GaeaBaseService<GaeaUserParam, GaeaUser> {


    /**
     * 通过用户名获取用户信息
     * @param username
     * @return
     */
    GaeaUser getUserByUsername(String username);

}
