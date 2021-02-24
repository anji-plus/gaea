package com.github.anji.plus.modules.dict.controller;

import com.github.anji.plus.gaea.bean.KeyValue;
import com.github.anji.plus.gaea.bean.ResponseBean;
import com.github.anji.plus.gaea.cache.CacheHelper;
import com.github.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import com.github.anji.plus.modules.dict.controller.dto.GaeaDictDTO;
import com.github.anji.plus.modules.dict.controller.param.GaeaDictParam;
import com.github.anji.plus.modules.dict.dao.entity.GaeaDict;
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
 * (GaeaDict)实体类
 *
 * @author lr
 * @since 2021-02-23 10:01:02
 */
@RestController
@RequestMapping("/gaeaDict")
@Api(value = "/gaeaDict", tags = "")
public class GaeaDictController extends GaeaBaseController<GaeaDictParam, GaeaDict, GaeaDictDTO> {

    @Autowired
    private GaeaDictService gaeaDictService;

    @Autowired
    private CacheHelper cacheHelper;

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

        //查询所有字典项
        List<GaeaDict> gaeaDicts = gaeaDictService.findAll();
        //刷新
        gaeaDictService.refreshCache(gaeaDicts);
        return responseSuccess();
    }


    /**
     * 下拉菜单
     * @return
     */
    @GetMapping("/select/{dictCode}")
    public ResponseBean select(@PathVariable("dictCode") String dictName){
        Locale locale = LocaleContextHolder.getLocale();
        //语言
        String language = locale.getLanguage();

        List<KeyValue> keyValues = gaeaDictService.select(dictName,language);
        return responseSuccessWithData(keyValues);
    }
}
