package com.anji.mirror.auth.mapper;

import com.anji.mirror.auth.domain.po.OrgPO;
import com.anji.mirror.auth.domain.vo.OrgVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 组织机构表 Mapper 接口
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
public interface OrgMapper extends BaseMapper<OrgPO> {

    /** XML 自定义分页
     * @param page
     * @param orgVO
     * @return
     */
    IPage<OrgVO> queryByPage(Page<?> page, @Param("orgVO")OrgVO orgVO);
}
