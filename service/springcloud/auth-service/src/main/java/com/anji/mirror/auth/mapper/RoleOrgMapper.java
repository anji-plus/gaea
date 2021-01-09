package com.anji.mirror.auth.mapper;

import com.anji.mirror.auth.domain.po.RoleOrgPO;
import com.anji.mirror.auth.domain.vo.RoleOrgVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-09-07
 */
public interface RoleOrgMapper extends BaseMapper<RoleOrgPO> {

    /** XML 自定义分页
     * @param page
     * @param roleOrgVO
     * @return
     */
    IPage<RoleOrgVO> queryByPage(Page<?> page, @Param("roleOrgVO")RoleOrgVO roleOrgVO);

    /**查询所有的角色组织
     * @return
     */
    List<RoleOrgVO> queryAllRoleOrg();
}
