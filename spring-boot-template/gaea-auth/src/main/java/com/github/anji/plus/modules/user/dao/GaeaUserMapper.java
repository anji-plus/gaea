package com.github.anji.plus.modules.user.dao;

import com.github.anji.plus.modules.user.dao.entity.GaeaUser;
import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表(GaeaUser)Mapper
 *
 * @author lirui
 * @since 2021-02-02 13:38:12
 */
@Mapper
public interface GaeaUserMapper extends GaeaBaseMapper<GaeaUser> {


}
