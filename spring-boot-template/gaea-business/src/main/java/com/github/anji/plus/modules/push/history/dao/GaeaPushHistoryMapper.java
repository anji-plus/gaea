package com.github.anji.plus.modules.push.history.dao;

import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.github.anji.plus.modules.push.history.dao.entity.GaeaPushHistory;
import com.github.anji.plus.modules.push.history.dao.entity.GaeaPushHistoryChart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (GaeaPushHistory)Mapper
 *
 * @author lr
 * @since 2021-02-08 09:36:10
 */
@Mapper
public interface GaeaPushHistoryMapper extends GaeaBaseMapper<GaeaPushHistory> {

    /**
     * 查询推送历史成功、失败统计
     * @param startDate
     * @param endDate
     * @return
     */
    @Select("SELECT template_type,DATE_FORMAT(send_time,'%Y-%m-%d') AS sendDate," +
            "SUM(CASE WHEN send_status = 1 THEN 1 ELSE 0 END) AS success," +
            "SUM(CASE WHEN send_status = 0 THEN 1 ELSE 0 END) AS failure" +
            " FROM gaea_push_history" +
            " WHERE DATE_FORMAT(send_time,'%Y-%m-%d') BETWEEN #{startDate} AND #{endDate}" +
            " GROUP BY template_type,DATE_FORMAT(send_time,'%Y-%m-%d')")
    List<GaeaPushHistoryChart> queryChartData(@Param("startDate") String startDate, @Param("endDate") String endDate);

}
