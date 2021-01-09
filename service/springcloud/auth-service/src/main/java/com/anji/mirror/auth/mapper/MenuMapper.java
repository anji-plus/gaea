package com.anji.mirror.auth.mapper;

import com.anji.mirror.auth.domain.po.MenuPO;
import com.anji.mirror.auth.domain.vo.MenuVO;
import com.anji.mirror.auth.domain.vo.UserRoleOrgVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
public interface MenuMapper extends BaseMapper<MenuPO> {

    /** XML 自定义分页
     * @param page
     * @param menuVO
     * @return
     */
    IPage<MenuVO> queryByPage(Page<?> page, @Param("menuVO")MenuVO menuVO);

    /** 查询所有菜单权限码
     * @return
     */
    List<MenuVO> queryAllMenuActionCode();

    /** 查询用户userId，针对每个组织的有哪些操作权限码
     * @param userId
     * @return
     */
    List<UserRoleOrgVO> queryMenuActionCodeByUserId(@Param("userId")Long userId);
}
