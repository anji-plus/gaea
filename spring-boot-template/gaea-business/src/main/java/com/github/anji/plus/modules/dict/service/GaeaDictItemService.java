package com.github.anji.plus.modules.dict.service;

import com.github.anji.plus.gaea.bean.KeyValue;
import com.github.anji.plus.modules.dict.dao.entity.GaeaDictItem;
import com.github.anji.plus.modules.dict.controller.param.GaeaDictItemParam;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;

import java.util.List;

/**
 * 数据字典项(GaeaDictItem)Service
 *
 * @author lirui
 * @since 2021-02-03 12:49:37
 */
public interface GaeaDictItemService extends GaeaBaseService<GaeaDictItemParam, GaeaDictItem> {

    /**
     * 获取指定字典code下拉
     * @param dictCode
     * @return
     */
    List<KeyValue> select(String dictCode, String language);
}
