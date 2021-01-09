package com.anji.mirror.auth.mapper;

import com.anji.mirror.auth.domain.po.DictPO;
import com.anji.mirror.auth.domain.vo.DictVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 数据字典表 Mapper 接口
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
public interface DictMapper extends BaseMapper<DictPO> {

    /** XML 自定义分页
     * @param page
     * @param dictVO
     * @return
     */
    IPage<DictVO> queryByPage(Page<?> page, @Param("dictVO")DictVO dictVO);
}
