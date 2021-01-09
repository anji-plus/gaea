package com.anji.mirror.auth.mapper;

import com.anji.mirror.auth.domain.po.LogPO;
import com.anji.mirror.common.model.LogVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户操作日志表 Mapper 接口
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
public interface LogMapper extends BaseMapper<LogPO> {

    /** XML 自定义分页
     * @param page
     * @param logVO
     * @return
     */
    IPage<LogVO> queryByPage(Page<?> page, @Param("logVO")LogVO logVO);
}
