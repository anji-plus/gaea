package com.anjiplus.template.business.modules.dict.service;

import com.anji.plus.gaea.bean.KeyValue;
import com.anji.plus.gaea.curd.service.GaeaBaseService;
import com.anjiplus.template.business.modules.dict.controller.param.GaeaDictParam;
import com.anjiplus.template.business.modules.dict.dao.entity.GaeaDict;

import java.util.List;

/**
 * (GaeaDict)Service
 *
 * @author lr
 * @since 2021-02-23 10:01:02
 */
public interface GaeaDictService extends GaeaBaseService<GaeaDictParam, GaeaDict> {

    /**
     * 刷新缓存
     * @param dictCode
     */
    void refresh(String dictCode);

    /**
     * 刷新全部缓存
     * @param gaeaDicts
     */
    void refreshCache(List<GaeaDict> gaeaDicts);


    /**
     * 获取指定字典code下拉
     * @param dictCode
     * @param language
     * @return
     */
    List<KeyValue> select(String dictCode, String language);

}
