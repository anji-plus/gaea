package com.anjiplus.template.auth.modules.user.dao;

import com.anjiplus.template.auth.modules.user.dao.entity.GaeaUser;
import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表(GaeaUser)Mapper
 *
 * @author lr
 * @since 2021-02-02 13:38:12
 */
@Mapper
public interface GaeaUserMapper extends GaeaBaseMapper<GaeaUser> {


}
