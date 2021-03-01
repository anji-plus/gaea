package com.anji.plus.modules.authority.dao;

import com.anji.plus.modules.authority.dao.entity.GaeaRoleAuthority;
import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色与权限对应关系(GaeaRoleAuthority)Mapper
 *
 * @author lirui
 * @since 2021-03-01 15:23:23
 */
@Mapper
public interface GaeaRoleAuthorityMapper extends GaeaBaseMapper<GaeaRoleAuthority> {


}