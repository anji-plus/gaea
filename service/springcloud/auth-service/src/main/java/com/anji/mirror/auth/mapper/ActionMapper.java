package com.anji.mirror.auth.mapper;

import com.anji.mirror.auth.domain.po.ActionPO;
import com.anji.mirror.auth.domain.vo.ActionVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 按钮表 Mapper 接口
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
public interface ActionMapper extends BaseMapper<ActionPO> {

    /** XML 自定义分页
     * @param page
     * @param actionVO
     * @return
     */
    IPage<ActionVO> queryByPage(Page<?> page, @Param("actionVO")ActionVO actionVO);
}
