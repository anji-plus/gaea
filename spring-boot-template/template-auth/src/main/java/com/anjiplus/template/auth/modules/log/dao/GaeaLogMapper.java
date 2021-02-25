package com.anjiplus.template.auth.modules.log.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.anjiplus.template.auth.modules.log.controller.param.GaeaLogParam;
import com.anjiplus.template.auth.modules.log.entity.GaeaLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (GaeaLog)Mapper
 *
 * @author peiyanni
 * @since 2021-02-18 16:30:21
 */
@Mapper
public interface GaeaLogMapper extends GaeaBaseMapper<GaeaLog> {

    /**
     * 分页查询日志信息
     * @param logParam
     * @return
     */
    List<GaeaLog> queryLogInfo(Page<GaeaLog> page,@Param("dto")GaeaLogParam logParam);

}
