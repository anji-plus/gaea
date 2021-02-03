package com.github.anji.plus.modules.dict.controller;

import com.github.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.github.anji.plus.modules.dict.dao.entity.GaeaDictItem;
import com.github.anji.plus.modules.dict.controller.dto.GaeaDictItemDTO;
import com.github.anji.plus.modules.dict.controller.param.GaeaDictItemParam;
import com.github.anji.plus.modules.dict.service.GaeaDictItemService;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 数据字典项(GaeaDictItem)实体类
 *
 * @author lirui
 * @since 2021-02-03 12:49:37
 */
@RestController
@RequestMapping("/gaeaDictItem")
@Api(value = "/gaeaDictItem", tags = "数据字典项")
public class GaeaDictItemController extends GaeaBaseController<GaeaDictItemParam, GaeaDictItem, GaeaDictItemDTO> {
    @Autowired
    private GaeaDictItemService gaeaDictItemService;

    @Override
    public GaeaBaseService<GaeaDictItemParam, GaeaDictItem> getService() {
        return gaeaDictItemService;
    }

    @Override
    public GaeaDictItem getEntity() {
        return new GaeaDictItem();
    }

    @Override
    public GaeaDictItemDTO getDTO() {
        return new GaeaDictItemDTO();
    }
}
