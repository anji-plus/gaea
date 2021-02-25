package com.anjiplus.template.common.feign;

import com.anjiplus.template.common.dto.DynamicQueryBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：
 *
 * @Author: peiyanni
 * @Date: 2021/2/19 14:32
 */
@Service
@Slf4j
public class AuthServiceClientFallback implements AuthServiceClient {
    @Override
    public List<DynamicQueryBo> getDynamicQueryBoListById(Long commonId) {
        return new ArrayList<>(0);
    }
}
