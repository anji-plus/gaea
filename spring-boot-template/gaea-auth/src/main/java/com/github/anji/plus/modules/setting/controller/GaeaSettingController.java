package com.github.anji.plus.modules.setting.controller;

import com.github.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import com.github.anji.plus.modules.setting.controller.dto.GaeaSettingDTO;
import com.github.anji.plus.modules.setting.controller.param.GaeaSettingParam;
import com.github.anji.plus.modules.setting.entity.GaeaSetting;
import com.github.anji.plus.modules.setting.service.GaeaSettingService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (GaeaSetting)实体类
 * 动态参数相关
 * @author peiyanni
 * @since 2021-02-05 16:58:58
 */
@RestController
@RequestMapping("/setting")
@Api(value = "/setting", tags = "")
public class GaeaSettingController extends GaeaBaseController<GaeaSettingParam, GaeaSetting, GaeaSettingDTO> {
    @Autowired
    private GaeaSettingService gaeaSettingService;

    @Override
    public GaeaBaseService<GaeaSettingParam, GaeaSetting> getService() {
        return gaeaSettingService;
    }

    @Override
    public GaeaSetting getEntity() {
        return new GaeaSetting();
    }

    @Override
    public GaeaSettingDTO getDTO() {
        return new GaeaSettingDTO();
    }
}