package com.anji.mirror.gateway.feign;

import com.anji.mirror.common.model.LogVO;
import com.anji.mirror.gateway.feign.hystrix.BaseServiceFeignClientFallback;
import com.anji.mirror.common.security.Authentication;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "auth-service", contextId = "gateway-authService",fallback = BaseServiceFeignClientFallback.class)
public interface AuthServiceFeignClient {

    /**判断token是否过期, 未过期token返回accessUserVO
     * @param token
     * @param servletPath
     * @return
     */
    @GetMapping("/user/getUserAuthByToken")
    Authentication getUserAuthByToken(@RequestParam("token") String token, @RequestParam("servletPath") String servletPath);

    /**保存用户操作日志
     * @param logVO
     * @return
     */
    @PostMapping("/log/saveOperatorLog")
    void saveOperatorLog(@RequestBody LogVO logVO);
}
