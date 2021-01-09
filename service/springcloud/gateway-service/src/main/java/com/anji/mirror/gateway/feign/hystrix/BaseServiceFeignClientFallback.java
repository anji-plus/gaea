package com.anji.mirror.gateway.feign.hystrix;

import com.anji.mirror.common.model.LogVO;
import com.anji.mirror.common.security.Authentication;
import com.anji.mirror.gateway.feign.AuthServiceFeignClient;
import org.springframework.stereotype.Service;

@Service
public class BaseServiceFeignClientFallback implements AuthServiceFeignClient {

    @Override
    public Authentication getUserAuthByToken(String token, String servletPath) {
        return Authentication.fail();
    }

    @Override
    public void saveOperatorLog(LogVO logVO) {

    }
}
