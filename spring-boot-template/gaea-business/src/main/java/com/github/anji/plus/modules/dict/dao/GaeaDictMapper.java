package com.github.anji.plus.modules.dict.dao;

import com.github.anji.plus.modules.dict.dao.entity.GaeaDict;
import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数组字典(GaeaDict)Mapper
 *
 * @author lirui
 * @since 2021-02-03 12:47:45
 */
@Mapper
public interface GaeaDictMapper extends GaeaBaseMapper<GaeaDict> {


}