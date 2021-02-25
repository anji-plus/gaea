package com.anjiplus.template.business.modules.push.template.controller;

import com.anji.plus.gaea.bean.ResponseBean;
import com.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.anji.plus.gaea.curd.service.GaeaBaseService;
import com.anjiplus.template.business.modules.push.template.controller.dto.GaeaPushTemplateDTO;
import com.anjiplus.template.business.modules.push.template.controller.param.GaeaPushTemplateParam;
import com.anjiplus.template.business.modules.push.template.controller.param.PushParamVO;
import com.anjiplus.template.business.modules.push.template.dao.entity.GaeaPushTemplate;
import com.anjiplus.template.business.modules.push.template.service.GaeaPushTemplateService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (GaeaPushTemplate)实体类
 *
 * @author lr
 * @since 2021-02-08 09:36:40
 */
@RestController
@RequestMapping("/gaeaPushTemplate")
@Api(value = "/gaeaPushTemplate", tags = "")
public class GaeaPushTemplateController extends GaeaBaseController<GaeaPushTemplateParam, GaeaPushTemplate, GaeaPushTemplateDTO> {
    @Autowired
    private GaeaPushTemplateService gaeaPushTemplateService;

    @Override
    public GaeaBaseService<GaeaPushTemplateParam, GaeaPushTemplate> getService() {
        return gaeaPushTemplateService;
    }

    @Override
    public GaeaPushTemplate getEntity() {
        return new GaeaPushTemplate();
    }

    @Override
    public GaeaPushTemplateDTO getDTO() {
        return new GaeaPushTemplateDTO();
    }


    @PostMapping("/preview")
    public ResponseBean preview(@RequestBody GaeaPushTemplateDTO gaeaPushTemplateDTO) {
        return gaeaPushTemplateService.preViewTemplate(gaeaPushTemplateDTO);
    }


    @PostMapping("/testSendPush")
    public ResponseBean testSendPush(@RequestBody PushParamVO requestModel) {//完成
        return ResponseBean.builder().build();
    }
}
