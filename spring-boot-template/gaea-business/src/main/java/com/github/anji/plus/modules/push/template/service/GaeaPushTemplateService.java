package com.github.anji.plus.modules.push.template.service;

import com.github.anji.plus.gaea.bean.ResponseBean;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import com.github.anji.plus.modules.push.template.controller.dto.GaeaPushTemplateDTO;
import com.github.anji.plus.modules.push.template.controller.param.GaeaPushTemplateParam;
import com.github.anji.plus.modules.push.template.dao.entity.GaeaPushTemplate;

/**
 * (GaeaPushTemplate)Service
 *
 * @author lr
 * @since 2021-02-08 09:36:40
 */
public interface GaeaPushTemplateService extends GaeaBaseService<GaeaPushTemplateParam, GaeaPushTemplate> {

    ResponseBean preViewTemplate(GaeaPushTemplateDTO templateVO);
}
