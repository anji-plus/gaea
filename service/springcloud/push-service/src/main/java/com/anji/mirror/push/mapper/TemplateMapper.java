package com.anji.mirror.push.mapper;

import com.anji.mirror.push.domain.po.TemplatePO;
import com.anji.mirror.push.domain.vo.TemplateVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-10-16
 */
public interface TemplateMapper extends BaseMapper<TemplatePO> {

    /**
     * XML 自定义分页
     *
     * @param page
     * @param templateVO
     * @return
     */
    IPage<TemplateVO> queryByPage(Page<?> page, @Param("templateVO") TemplateVO templateVO);
}
