package com.github.anji.plus.gaea.curd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.anji.plus.gaea.curd.entity.GaeaBaseEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * mybatis基础接口
 * @author lirui
 * @since 2021-01-12
 */
public interface GaeaBaseMapper<T extends GaeaBaseEntity> extends BaseMapper<T> {

    /**
     * 如果要自动填充，@{@code Param}(xx) xx参数名必须是 list/collection/array 3个的其中之一
     *
     * @param list
     * @return
     */
    int insertBatch(@Param("list") List<T> list);


    /**
     *  根据ID 更新指定字段
     * @param map 指定字段和值
     * @param id id
     * @return
     */
    int updateFieldsById(@Param("map") Map<String, Object> map, @Param("id") Long id);
}
