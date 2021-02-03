package com.github.anji.plus.modules.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.anji.plus.gaea.bean.KeyValue;
import com.github.anji.plus.gaea.cache.CacheHelper;
import com.github.anji.plus.gaea.cache.RedisKeyEnum;
import com.github.anji.plus.gaea.constant.BaseOperationEnum;
import com.github.anji.plus.gaea.constant.GaeaConstant;
import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.github.anji.plus.gaea.utils.GaeaUtils;
import com.github.anji.plus.modules.dict.dao.GaeaDictItemMapper;
import com.github.anji.plus.modules.dict.dao.entity.GaeaDictItem;
import com.github.anji.plus.modules.dict.service.GaeaDictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据字典项(GaeaDictItem)ServiceImpl
 *
 * @author lirui
 * @since 2021-02-03 12:49:37
 */
@Service
public class GaeaDictItemServiceImpl implements GaeaDictItemService {

    @Autowired
    private GaeaDictItemMapper  gaeaDictItemMapper;

    @Autowired
    private CacheHelper cacheHelper;

    @Override
    public GaeaBaseMapper<GaeaDictItem> getMapper() {
        return  gaeaDictItemMapper;
    }

    /**
     * 后置处理，增、改后更新字典缓存，删后去掉字典缓存
     * @param entity
     * @param operationEnum 操作类型
     */
    @Override
    public void processAfterOperation(GaeaDictItem entity, BaseOperationEnum operationEnum) {
        String key = RedisKeyEnum.DICT_PREFIX.getKey() + entity.getDictCode();
        switch (operationEnum) {
            case INSERT:
                cacheHelper.hashSet(key, entity.getItemValue(),entity.getItemName());
            case UPDATE:
                cacheHelper.hashSet(key,entity.getItemValue(),entity.getItemName());
                break;
            case DELETE:
                cacheHelper.hashDel(key, entity.getItemValue());
                break;
            default:
        }
    }

    /**
     * 根据国际化语言查询指定字典编码下拉列表
     * 先从Redis中获取
     * @param dictCode 字典编码
     * @param language 语言
     * @return
     */
    @Override
    public List<KeyValue> select(String dictCode, String language) {

        //缓存字典Key
        String dictKey = RedisKeyEnum.DICT_PREFIX + language + GaeaConstant.REDIS_SPLIT + dictCode;

        Map<String, String> dictMap = cacheHelper.hashGet(dictKey);

        //当查询的字典为空时
        if (CollectionUtils.isEmpty(dictMap)) {
            LambdaQueryWrapper<GaeaDictItem> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(GaeaDictItem::getDictCode, dictCode)
                    .eq(GaeaDictItem::getLocale, language)
                    .orderByAsc(GaeaDictItem::getSort);

            List<GaeaDictItem> list = gaeaDictItemMapper.selectList(wrapper);
            List<KeyValue> keyValues = list.stream()
                    .map(dictItemEntity -> new KeyValue(dictItemEntity.getItemValue(),dictItemEntity.getItemName()))
                    .collect(Collectors.toList());
            return keyValues;
        }

        return GaeaUtils.formatKeyValue(dictMap);
    }

}
