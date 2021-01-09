package com.anji.mirror.auth.mapper;

import com.anji.mirror.auth.domain.po.MenuActionPO;
import com.anji.mirror.auth.domain.vo.MenuActionVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 运营角色权限表 Mapper 接口
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-09-07
 */
public interface MenuActionMapper extends BaseMapper<MenuActionPO> {

    /** XML 自定义分页
     * @param page
     * @param menuActionVO
     * @return
     */
    IPage<MenuActionVO> queryByPage(Page<?> page, @Param("menuActionVO")MenuActionVO menuActionVO);

    /**查询所有的菜单按钮
     * @return
     */
    List<MenuActionVO> queryAllMenuAction();
}
