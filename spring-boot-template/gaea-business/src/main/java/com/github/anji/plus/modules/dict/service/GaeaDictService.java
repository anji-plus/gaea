package com.github.anji.plus.modules.dict.service;

import com.github.anji.plus.modules.dict.dao.entity.GaeaDict;
import com.github.anji.plus.modules.dict.controller.param.GaeaDictParam;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;

/**
 * 数组字典(GaeaDict)Service
 *
 * @author lirui
 * @since 2021-02-03 12:47:45
 */
public interface GaeaDictService extends GaeaBaseService<GaeaDictParam, GaeaDict> {

    /**
     * 刷新缓存
     * @param dictCode
     */
    void refresh(String dictCode);

}
