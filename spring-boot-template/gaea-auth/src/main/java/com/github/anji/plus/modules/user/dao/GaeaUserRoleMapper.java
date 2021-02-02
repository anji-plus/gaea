package com.github.anji.plus.modules.user.dao;

import com.github.anji.plus.modules.user.dao.entity.GaeaUserRole;
import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色对应关系(GaeaUserRole)Mapper
 *
 * @author lirui
 * @since 2021-02-02 14:59:43
 */
@Mapper
public interface GaeaUserRoleMapper extends GaeaBaseMapper<GaeaUserRole> {


}
