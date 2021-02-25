package com.anjiplus.template.common.service.commoncondition.impl;

import com.alibaba.fastjson.JSON;
import com.anjiplus.template.common.dto.DynamicQueryBo;
import com.anjiplus.template.common.feign.AuthServiceClient;
import com.anjiplus.template.common.service.cache.CacheService;
import com.anjiplus.template.common.service.commoncondition.ICommonConditionService;
import com.anjiplus.template.common.CacheConstants;
import com.anjiplus.template.common.CacheTimeConstants;
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
