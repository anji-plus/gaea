package com.anjiplus.template.business.modules.dict.service.impl;

import com.anjiplus.template.business.modules.dict.service.GaeaDictService;
import com.anjiplus.template.business.modules.dict.dao.entity.GaeaDict;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.anji.plus.gaea.bean.KeyValue;
import com.anji.plus.gaea.cache.CacheHelper;
import com.anji.plus.gaea.cache.RedisKeyEnum;
import com.anji.plus.gaea.constant.BaseOperationEnum;
import com.anji.plus.gaea.constant.GaeaConstant;
import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.anji.plus.gaea.utils.GaeaUtils;
import com.anjiplus.template.business.modules.dict.dao.GaeaDictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (GaeaDict)ServiceImpl
 *
 * @author lr
 * @since 2021-02-23 10:01:02
 */
@Service
public class GaeaDictServiceImpl implements GaeaDictService {

    @Autowired
    private GaeaDictMapper  gaeaDictMapper;

    @Autowired
    private CacheHelper cacheHelper;

    @Override
    public GaeaBaseMapper<GaeaDict> getMapper() {
        return  gaeaDictMapper;
    }


    @Override
    public void processBeforeOperation(GaeaDict entity, BaseOperationEnum operationEnum) {
        //删除缓存
        cacheHelper.delete(RedisKeyEnum.DICT_PREFIX + entity.getLocale() + GaeaConstant.REDIS_SPLIT + entity.getDictName());
        switch (operationEnum) {
            case INSERT:
            case UPDATE:
                refresh(entity.getDictName());
                break;
            default:
        }
    }

    /**
     * 刷新缓存
     * @param dictName
     */
    @Override
    public void refresh(String dictName) {
        LambdaQueryWrapper<GaeaDict> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GaeaDict::getDictName, dictName);
        //查询所有字典项
        List<GaeaDict> gaeaDicts = list(wrapper);
        refreshCache(gaeaDicts);

    }

    /**
     * 刷新全部缓存
     * @param gaeaDicts
     */
    @Override
    public void refreshCache(List<GaeaDict> gaeaDicts) {
        //对数据字典项进行分组，分组key=语言标识:字典编码
        Map<String, Map<String, String>> dictItemMap = gaeaDicts.stream()
                .collect(Collectors
                        .groupingBy(item -> item.getLocale() + GaeaConstant.REDIS_SPLIT + item.getDictName(),
                                Collectors.toMap(GaeaDict::getItemValue, GaeaDict::getItemName,(v1,v2)-> v2)));

        //遍历并保持到Redis中
        dictItemMap.entrySet().stream().forEach(entry -> {
            String key = RedisKeyEnum.DICT_PREFIX.getKey()  + entry.getKey();
            cacheHelper.delete(key);
            cacheHelper.hashSet(key, entry.getValue());
        });
    }

    /**
     * 根据国际化语言查询指定字典编码下拉列表
     * 先从Redis中获取
     * @param dictName 字典名称
     * @param language 语言
     * @return
     */
    @Override
    public List<KeyValue> select(String dictName, String language) {

        //缓存字典Key
        String dictKey = RedisKeyEnum.DICT_PREFIX + language + GaeaConstant.REDIS_SPLIT + dictName;

        Map<String, String> dictMap = cacheHelper.hashGet(dictKey);

        //当查询的字典为空时
        if (CollectionUtils.isEmpty(dictMap)) {
            LambdaQueryWrapper<GaeaDict> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(GaeaDict::getDictName, dictName)
                    .eq(GaeaDict::getLocale, language)
                    .orderByAsc(GaeaDict::getSort);

            List<GaeaDict> list = getMapper().selectList(wrapper);

            List<KeyValue> keyValues = list.stream()
                    .map(dictItemEntity -> new KeyValue(dictItemEntity.getItemValue(),dictItemEntity.getItemName()))
                    .collect(Collectors.toList());
            return keyValues;
        }

        return GaeaUtils.formatKeyValue(dictMap);
    }

}
