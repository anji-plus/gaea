package com.github.anji.plus.feign;

import com.anjiplus.gaea.export.vo.ExportOperation;
import com.github.anji.plus.dto.DynamicQueryBo;
import com.github.anji.plus.gaea.bean.ResponseBean;
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
public class BusinessServiceClientFallback implements BusinessServiceClient {

    @Override
    public Boolean export(ExportOperation exportOperation) {
        log.info("执行了fallback");
        return false;
    }
}
