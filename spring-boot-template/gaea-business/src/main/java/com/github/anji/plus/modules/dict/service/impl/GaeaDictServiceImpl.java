package com.github.anji.plus.modules.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.anji.plus.gaea.cache.CacheHelper;
import com.github.anji.plus.gaea.cache.RedisKeyEnum;
import com.github.anji.plus.gaea.constant.BaseOperationEnum;
import com.github.anji.plus.gaea.constant.GaeaConstant;
import com.github.anji.plus.modules.dict.dao.GaeaDictItemMapper;
import com.github.anji.plus.modules.dict.dao.entity.GaeaDict;
import com.github.anji.plus.modules.dict.dao.GaeaDictMapper;
import com.github.anji.plus.modules.dict.dao.entity.GaeaDictItem;
import com.github.anji.plus.modules.dict.service.GaeaDictService;
import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数组字典(GaeaDict)ServiceImpl
 *
 * @author lirui
 * @since 2021-02-03 12:47:45
 */
@Service
public class GaeaDictServiceImpl implements GaeaDictService {

    @Autowired
    private GaeaDictMapper  gaeaDictMapper;

    @Autowired
    private CacheHelper cacheHelper;

    @Autowired
    private GaeaDictItemMapper gaeaDictItemMapper;

    @Override
    public GaeaBaseMapper<GaeaDict> getMapper() {
        return  gaeaDictMapper;
    }

    @Override
    public void processBeforeOperation(GaeaDict entity, BaseOperationEnum operationEnum) {
        switch (operationEnum) {
            case DELETE:
                //删除数据字典项
                gaeaDictItemMapper.delete(Wrappers.<GaeaDictItem>lambdaQuery().eq(GaeaDictItem::getDictCode,entity.getDictCode()));
                //删除缓存
                cacheHelper.delete(RedisKeyEnum.DICT_PREFIX.getKey() + entity.getDictCode());

                break;
            default:
        }
    }

    /**
     * 刷新缓存
     * @param dictCode
     */
    @Override
    public void refresh(String dictCode) {
        LambdaQueryWrapper<GaeaDictItem> wrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(dictCode)) {
            wrapper.eq(GaeaDictItem::getDictCode, dictCode);
        }
        //查询所有字典项
        List<GaeaDictItem> gaeaDictItems = gaeaDictItemMapper.selectList(wrapper);
        refreshCache(gaeaDictItems);

    }

    /**
     * 刷新缓存
     * @param gaeaDictItems
     */
    private void refreshCache(List<GaeaDictItem> gaeaDictItems) {
        //对数据字典项进行分组，分组key=语言标识:字典编码
        Map<String, Map<String, String>> dictItemMap = gaeaDictItems.stream()
                .collect(Collectors
                        .groupingBy(item -> item.getLocale() + GaeaConstant.REDIS_SPLIT + item.getDictCode(),
                                Collectors.toMap(GaeaDictItem::getItemValue, GaeaDictItem::getItemName)));

        //遍历并保持到Redis中
        dictItemMap.entrySet().stream().forEach(entry -> {
            String key = RedisKeyEnum.DICT_PREFIX.getKey() + entry.getKey();
            cacheHelper.delete(key);
            cacheHelper.hashSet(key, entry.getValue());
        });
    }
}
