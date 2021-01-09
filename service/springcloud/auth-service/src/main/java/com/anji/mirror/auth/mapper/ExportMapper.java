package com.anji.mirror.auth.mapper;

import com.anji.mirror.auth.domain.po.ExportPO;
import com.anji.mirror.common.model.ExportVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 导出中心 Mapper 接口
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-11-23
 */
public interface ExportMapper extends BaseMapper<ExportPO> {

    /** XML 自定义分页
     * @param page
     * @param exportVO
     * @return
     */
    IPage<ExportVO> queryByPage(Page<?> page, @Param("exportVO")ExportVO exportVO);
}
