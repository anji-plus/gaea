package com.github.anji.plus.modules.org.controller;

import com.github.anji.plus.gaea.bean.ResponseBean;
import com.github.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.github.anji.plus.modules.menu.controller.dto.TreeDTO;
import com.github.anji.plus.modules.org.dao.entity.GaeaOrg;
import com.github.anji.plus.modules.org.controller.dto.GaeaOrgDTO;
import com.github.anji.plus.modules.org.controller.param.GaeaOrgParam;
import com.github.anji.plus.modules.org.service.GaeaOrgService;
import com.github.anji.plus.gaea.curd.service.GaeaBaseService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 组织(GaeaOrg)实体类
 *
 * @author lirui
 * @since 2021-02-02 13:37:33
 */
@RestController
@RequestMapping("/org")
@Api(value = "/org", tags = "组织")
public class GaeaOrgController extends GaeaBaseController<GaeaOrgParam, GaeaOrg, GaeaOrgDTO> {
    @Autowired
    private GaeaOrgService gaeaOrgService;

    @Override
    public GaeaBaseService<GaeaOrgParam, GaeaOrg> getService() {
        return gaeaOrgService;
    }

    @Override
    public GaeaOrg getEntity() {
        return new GaeaOrg();
    }

    @Override
    public GaeaOrgDTO getDTO() {
        return new GaeaOrgDTO();
    }


    /**
     * 查询所有可用的机构信息
     *
     * @return
     */
    @GetMapping("/queryAllOrg")
    public ResponseBean queryAllOrg() {
        return responseSuccessWithData(gaeaOrgService.queryAllOrg());
    }

    /**
     * 新增机构
     * @param dto
     * @return
     */
    @PostMapping("/saveOrg")
    public ResponseBean saveOrg(@RequestBody GaeaOrgDTO dto){
        return responseSuccessWithData(gaeaOrgService.saveOrUpdateOrg(dto));
    }

    /**
     * 编辑机构
     * @param dto
     * @return
     */
    @PostMapping("/updateOrg")
    public ResponseBean updateOrg(@RequestBody GaeaOrgDTO dto){
        return responseSuccessWithData(gaeaOrgService.saveOrUpdateOrg(dto));
    }
}

