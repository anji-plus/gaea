package com.anji.mirror.push.mapper;

import com.anjiplus.gaea.push.domain.po.PushHistoryPO;
import com.anjiplus.gaea.push.domain.vo.PushHistoryVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-10-26
 */
public interface PushHistoryMapper extends BaseMapper<PushHistoryPO> {

    /**
     * XML 自定义分页
     *
     * @param page
     * @param pushHistoryVO
     * @return
     */
    IPage<PushHistoryVO> queryByPage(Page<?> page, @Param("pushHistoryVO") PushHistoryVO pushHistoryVO);

    /**
     * 根据发送时间查询条数
     *
     * @param sendTime
     * @return
     */
    int queryCountByDay(@Param("templateType") String templateType, @Param("sendTime") String sendTime, @Param("sendStatus") Integer sendStatus);


    /**
     * 创建表 根据时间
     * @param map
     */
    void createTable(Map<String, String> map);

    /**
     * copy 表
     * @param map
     */
    void copyArchiveData(Map<String, String> map);

    /**
     * 清除小于某个时间的表
     * @param map
     */
    void deleteArchiveData(Map<String, String> map);



}
