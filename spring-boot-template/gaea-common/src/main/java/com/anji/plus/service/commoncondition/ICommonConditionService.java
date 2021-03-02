package com.anji.plus.service.commoncondition;


import com.anji.plus.dto.DynamicQueryBo;

import java.util.List;

/**
 * 常用查询sql
 *
 * @author peiyanni
 * @version ICommonConditionService.java
 */
public interface ICommonConditionService {

    /**
     * 根据ID查询获取常用查询sql
     *
     * @param id
     * @return 高级查询集合
     */
    List<DynamicQueryBo> getDynamicQueryBoListById(Long id, List<DynamicQueryBo> paramList);
}
