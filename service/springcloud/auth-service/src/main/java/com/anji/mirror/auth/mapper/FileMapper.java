package com.anji.mirror.auth.mapper;

import com.anji.mirror.auth.domain.po.FilePO;
import com.anji.mirror.auth.domain.vo.FileVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-11-23
 */
public interface FileMapper extends BaseMapper<FilePO> {

    /** XML 自定义分页
     * @param page
     * @param fileVO
     * @return
     */
    IPage<FileVO> queryByPage(Page<?> page, @Param("fileVO")FileVO fileVO);
}
