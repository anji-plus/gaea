package com.github.anji.plus.modules.role.dao;

import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.github.anji.plus.modules.role.controller.dto.RoleOrgDto;
import com.github.anji.plus.modules.role.dao.entity.GaeaRoleOrg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色与组织(GaeaRoleOrg)Mapper
 *
 * @author peiyanni
 * @since 2021-02-03 16:44:09
 */
@Mapper
public interface GaeaRoleOrgMapper extends GaeaBaseMapper<GaeaRoleOrg> {

    /**
     * 查询所有机构下的角色信息
     * @return
     */
    List<RoleOrgDto> queryAllRoleOrg();

}