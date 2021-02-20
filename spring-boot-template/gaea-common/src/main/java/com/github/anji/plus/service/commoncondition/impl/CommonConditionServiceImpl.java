package com.github.anji.plus.service.commoncondition.impl;

import com.alibaba.fastjson.JSON;
import com.github.anji.plus.common.CacheConstants;
import com.github.anji.plus.common.CacheTimeConstants;
import com.github.anji.plus.dto.DynamicQueryBo;
import com.github.anji.plus.feign.AuthServiceClient;
import com.github.anji.plus.service.cache.CacheService;
import com.github.anji.plus.service.commoncondition.ICommonConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * CommonConditionServiceImpl
 * </pre>
 *
 * @author peiyanni
 * @version CommonConditionServiceImpl.java,
 */
@Service
public class CommonConditionServiceImpl implements ICommonConditionService {
    @Autowired
    private CacheService cacheService;

    @Autowired
    private AuthServiceClient authServiceClient;

    @Override
    public List<DynamicQueryBo> getDynamicQueryBoListById(Long id, List<DynamicQueryBo> paramList) {
        List<DynamicQueryBo> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(paramList)) {
            result.addAll(paramList);
        }
        // 先查缓存
        String key = CacheConstants.COMMON_QUERY_CONDITION + id;
        Serializable serializable = cacheService.get(key);
        if (null == serializable) {
            List<DynamicQueryBo> getSqlList = authServiceClient.getDynamicQueryBoListById(id);
            if (!CollectionUtils.isEmpty(getSqlList)) {
                result.addAll(getSqlList);
            }
            cacheService.add(key, JSON.toJSONString(getSqlList), CacheTimeConstants.QUERY_CONDITION_TIME_THIRTY_DAYS, TimeUnit.DAYS);

        } else {
            result.addAll(JSON.parseArray((String) serializable, DynamicQueryBo.class));
        }
        return result;
    }

}
