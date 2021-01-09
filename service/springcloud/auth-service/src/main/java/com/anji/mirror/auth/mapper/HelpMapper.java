package com.anji.mirror.auth.mapper;

import com.anji.mirror.auth.domain.po.HelpPO;
import com.anji.mirror.auth.domain.vo.HelpVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haitong.nla.auth.domain.vo.SerarchConditionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 帮助中心表 Mapper 接口
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-11-23
 */
public interface HelpMapper extends BaseMapper<HelpPO> {

    /** XML 自定义分页
     * @param page
     * @param helpVO
     * @return
     */
    IPage<HelpVO> queryByPage(Page<?> page, @Param("helpVO") HelpVO helpVO);

    /** XML 条件查询
     * @param helpVO
     * @return
     */
    List<HelpVO> queryByCondition(@Param("helpVO") HelpVO helpVO);

    /** XML 关键字搜索
     * @param serarchConditionVO
     * @return
     */
    IPage<HelpVO> searchKeyWord(Page<?> page, @Param("serarchConditionVO") SerarchConditionVO serarchConditionVO);


}
