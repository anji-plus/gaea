package com.github.anji.plus.modules.dict.controller;

import com.github.anji.plus.gaea.bean.KeyValue;
import com.github.anji.plus.gaea.bean.ResponseBean;
import com.github.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import com.github.anji.plus.modules.dict.controller.dto.GaeaDictDTO;
import com.github.anji.plus.modules.dict.controller.param.GaeaDictParam;
import com.github.anji.plus.modules.dict.dao.entity.GaeaDict;
import com.github.anji.plus.modules.dict.service.GaeaDictItemService;
import com.github.anji.plus.modules.dict.service.GaeaDictService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

/**
 * 数组字典(GaeaDict)实体类
 *
 * @author lirui
 * @since 2021-02-03 12:47:45
 */
@RestController
@RequestMapping("/gaeaDict")
@Api(value = "/gaeaDict", tags = "数组字典")
public class GaeaDictController extends GaeaBaseController<GaeaDictParam, GaeaDict, GaeaDictDTO> {

    @Autowired
    private GaeaDictService gaeaDictService;

    @Autowired
    private GaeaDictItemService gaeaDictItemService;

    @Override
    public GaeaBaseService<GaeaDictParam, GaeaDict> getService() {
        return gaeaDictService;
    }

    @Override
    public GaeaDict getEntity() {
        return new GaeaDict();
    }

    @Override
    public GaeaDictDTO getDTO() {
        return new GaeaDictDTO();
    }

    /**
     * 刷新数据字典
     * @return
     */
    @GetMapping("/fresh")
    public ResponseBean refresh() {
        gaeaDictService.refresh(null);
        return responseSuccess();
    }

    /**
     * 刷新指定编码的数据字典
     * @return
     */
    @GetMapping("/fresh/{dictCode}")
    public ResponseBean refresh(@PathVariable("dictCode") String dictCode) {
        gaeaDictService.refresh(dictCode);
        return responseSuccess();
    }

    /**
     * 下拉菜单
     * @return
     */
    @GetMapping("/select/{dictCode}")
    public ResponseBean select(@PathVariable("dictCode") String dictCode){
        Locale locale = LocaleContextHolder.getLocale();
        //语言
        String language = locale.getLanguage();

        List<KeyValue> keyValues = gaeaDictItemService.select(dictCode,language);
        return responseSuccessWithData(keyValues);
    }
}
