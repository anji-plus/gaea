package com.anji.plus.gaea.auth.modules.role.dao;

import com.anji.plus.gaea.auth.modules.role.dao.entity.GaeaRole;
import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色(GaeaRole)Mapper
 *
 * @author lr
 * @since 2021-02-02 13:37:54
 */
@Mapper
public interface GaeaRoleMapper extends GaeaBaseMapper<GaeaRole> {


}
