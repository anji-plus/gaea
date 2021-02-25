package com.anjiplus.template.auth.modules.user.dao;

import com.anjiplus.template.auth.modules.user.dao.entity.GaeaUserRole;
import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色对应关系(GaeaUserRole)Mapper
 *
 * @author lr
 * @since 2021-02-02 14:59:43
 */
@Mapper
public interface GaeaUserRoleMapper extends GaeaBaseMapper<GaeaUserRole> {


}
