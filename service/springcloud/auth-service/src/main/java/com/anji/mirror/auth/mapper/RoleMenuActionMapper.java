package com.anji.mirror.auth.mapper;

import com.anji.mirror.auth.domain.po.RoleMenuActionPO;
import com.anji.mirror.auth.domain.vo.RoleMenuActionVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 运营角色权限表 Mapper 接口
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-09-07
 */
public interface RoleMenuActionMapper extends BaseMapper<RoleMenuActionPO> {

    /** XML 自定义分页
     * @param page
     * @param roleMenuActionVO
     * @return
     */
    IPage<RoleMenuActionVO> queryByPage(Page<?> page, @Param("roleMenuActionVO")RoleMenuActionVO roleMenuActionVO);
}
