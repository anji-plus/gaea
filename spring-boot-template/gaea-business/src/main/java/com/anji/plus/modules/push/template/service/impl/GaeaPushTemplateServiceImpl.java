package com.anji.plus.modules.push.template.service.impl;

import com.anji.plus.gaea.bean.ResponseBean;
import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.anji.plus.gaea.utils.GaeaBeanUtils;
import com.anji.plus.modules.push.template.controller.dto.GaeaPushTemplateDTO;
import com.anji.plus.modules.push.template.dao.GaeaPushTemplateMapper;
import com.anji.plus.modules.push.template.dao.entity.GaeaPushTemplate;
import com.anji.plus.modules.push.template.service.GaeaPushTemplateService;
import com.anjiplus.gaea.push.domain.po.TemplatePO;
import com.anjiplus.gaea.push.utils.TemplateAnalysisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * (GaeaPushTemplate)ServiceImpl
 *
 * @author lr
 * @since 2021-02-08 09:36:40
 */
@Service
public class GaeaPushTemplateServiceImpl implements GaeaPushTemplateService {
    @Autowired
    private GaeaPushTemplateMapper gaeaPushTemplateMapper;

    @Override
    public GaeaBaseMapper<GaeaPushTemplate> getMapper() {
        return gaeaPushTemplateMapper;
    }

    /**
     * 预览
     *
     * @return
     */
    @Override
    public ResponseBean preViewTemplate(GaeaPushTemplateDTO templateVO) {
        if (templateVO.getId() != null) {
            GaeaPushTemplate gaeaPushTemplate = selectOne(templateVO.getId());
            GaeaBeanUtils.copyAndFormatter(gaeaPushTemplate, templateVO);
        }

        TemplatePO templatePO = TemplateAnalysisUtil.analysisTemplate(templateVO.getTemplateShow(), true, templateVO.getTemplateType().toLowerCase().equals("mail"));
        Map<String, Object> param = TemplateAnalysisUtil.getPreParam(templatePO.getParamMap());
        String html = TemplateAnalysisUtil.buildHTML(templatePO, param, templatePO.getParamMap(), templateVO.getTemplateType().toLowerCase().equals("mail"));
        if (templateVO.getTemplateType().toLowerCase().equals("dingtalk")) {
            templatePO.setHtml(html.replace("\n", "</br>"));
        } else {
            templatePO.setHtml(html);
        }
        templatePO.setParamMap(TemplateAnalysisUtil.conversionParaMap(templatePO.getParamMap()));
        return ResponseBean.builder().data(templatePO).build();
    }

}
