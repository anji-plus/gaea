package com.anjiplus.template.auth.modules.org.service;

import com.anjiplus.template.auth.modules.org.controller.dto.GaeaOrgDTO;
import com.anjiplus.template.auth.modules.org.dao.entity.GaeaOrg;
import com.anjiplus.template.auth.modules.org.controller.param.GaeaOrgParam;
import com.anji.plus.gaea.curd.service.GaeaBaseService;

import java.util.List;

/**
 * 组织(GaeaOrg)Service
 *
 * @author lr
 * @since 2021-02-02 13:37:33
 */
public interface GaeaOrgService extends GaeaBaseService<GaeaOrgParam, GaeaOrg> {

    /**
     * 查询所有可用机构信息
     * @return
     */
    List<GaeaOrg> queryAllOrg();

    Boolean saveOrUpdateOrg(GaeaOrgDTO gaeaOrgDTO);
}