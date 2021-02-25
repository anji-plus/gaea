package com.anjiplus.template.business.modules.push.template.service;

import com.anji.plus.gaea.bean.ResponseBean;
import com.anji.plus.gaea.curd.service.GaeaBaseService;
import com.anjiplus.template.business.modules.push.template.controller.dto.GaeaPushTemplateDTO;
import com.anjiplus.template.business.modules.push.template.controller.param.GaeaPushTemplateParam;
import com.anjiplus.template.business.modules.push.template.dao.entity.GaeaPushTemplate;

/**
 * (GaeaPushTemplate)Service
 *
 * @author lr
 * @since 2021-02-08 09:36:40
 */
public interface GaeaPushTemplateService extends GaeaBaseService<GaeaPushTemplateParam, GaeaPushTemplate> {

    ResponseBean preViewTemplate(GaeaPushTemplateDTO templateVO);
}
