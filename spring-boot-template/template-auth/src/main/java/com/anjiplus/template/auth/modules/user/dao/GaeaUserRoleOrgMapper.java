package com.anjiplus.template.auth.modules.user.dao;

import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.anjiplus.template.auth.modules.org.dao.entity.GaeaOrg;
import com.anjiplus.template.auth.modules.user.dao.entity.GaeaUserRoleOrg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (GaeaUserRoleOrg)Mapper
 *
 * @author makejava
 * @since 2021-02-03 18:01:05
 */
@Mapper
public interface GaeaUserRoleOrgMapper extends GaeaBaseMapper<GaeaUserRoleOrg> {
    /**
     * 根据用户查询所属的机构信息
     * @param username
     * @return
     */
    List<GaeaOrg> getOrgInfoByUsername(@Param("username") String username);

    /**
     * 根据用户和机构查询角色信息
     * @param username
     * @param orgCode
     * @return
     */
    List<String> getRoleCodeByUser(@Param("username")String username,@Param("orgCode")String orgCode);
}
