package com.anji.plus.feign;

import com.anjiplus.gaea.export.vo.ExportOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 功能描述：
 *
 * @Author: peiyanni
 * @Date: 2021/2/19 14:32
 */
@Service
@Slf4j
public class BusinessServiceClientFallback implements BusinessServiceClient {

    @Override
    public Boolean export(ExportOperation exportOperation) {
        log.info("执行了fallback");
        return false;
    }
}
