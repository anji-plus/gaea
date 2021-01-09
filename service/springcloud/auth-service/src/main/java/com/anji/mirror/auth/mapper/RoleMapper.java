package com.anji.mirror.auth.mapper;

import com.anji.mirror.auth.domain.po.RolePO;
import com.anji.mirror.auth.domain.vo.RoleVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
public interface RoleMapper extends BaseMapper<RolePO> {

    /** XML 自定义分页
     * @param page
     * @param roleVO
     * @return
     */
    IPage<RoleVO> queryByPage(Page<?> page, @Param("roleVO")RoleVO roleVO);
}
