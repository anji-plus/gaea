package com.anjiplus.template.common.feign;

import com.anjiplus.template.common.dto.DynamicQueryBo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <pre>
 * AuthServiceClient
 * </pre>
 * , fallback = CommonBaseServiceFallback.class
 *
 * @author peiyanni
 * @version AuthServiceClient.java
 */
@FeignClient(name = "gaea-auth", fallback = AuthServiceClientFallback.class)
public interface AuthServiceClient {

    /**
     * 根据ID查询获取常用查询sql集合
     *
     * @param commonId
     * @return List<DynamicQueryBo>
     */
    @RequestMapping(value = "/gaeaCommonCondition/getDynamicQueryBoListById", method = RequestMethod.POST)
    List<DynamicQueryBo> getDynamicQueryBoListById(@RequestParam("commonId") Long commonId);


}
