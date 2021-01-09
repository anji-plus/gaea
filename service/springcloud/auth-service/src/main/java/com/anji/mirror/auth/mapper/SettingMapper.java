package com.anji.mirror.auth.mapper;

import com.anji.mirror.auth.domain.po.SettingPO;
import com.anji.mirror.auth.domain.vo.SettingVO;
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
 * @since 2020-10-10
 */
public interface SettingMapper extends BaseMapper<SettingPO> {

    /** XML 自定义分页
     * @param page
     * @param settingVO
     * @return
     */
    IPage<SettingVO> queryByPage(Page<?> page, @Param("settingVO")SettingVO settingVO);
}
