package com.anji.plus.modules.authority.controller;

import com.anji.plus.gaea.bean.ResponseBean;
import com.anji.plus.gaea.bean.TreeNode;
import com.anji.plus.gaea.curd.controller.GaeaBaseController;
import com.anji.plus.gaea.curd.service.GaeaBaseService;
import com.anji.plus.modules.authority.controller.dto.GaeaAuthorityDTO;
import com.anji.plus.modules.authority.controller.param.GaeaAuthorityParam;
import com.anji.plus.modules.authority.dao.entity.GaeaAuthority;
import com.anji.plus.modules.authority.dao.entity.GaeaRoleAuthority;
import com.anji.plus.modules.authority.service.GaeaAuthorityService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单表(GaeaAuthority)实体类
 *
 * @author lirui
 * @since 2021-03-01 10:03:51
 */
@RestController
@RequestMapping("/gaeaAuthority")
@Api(value = "/gaeaAuthority", tags = "菜单表")
public class GaeaAuthorityController extends GaeaBaseController<GaeaAuthorityParam, GaeaAuthority, GaeaAuthorityDTO> {
    @Autowired
    private GaeaAuthorityService gaeaAuthorityService;

    @Override
    public GaeaBaseService<GaeaAuthorityParam, GaeaAuthority> getService() {
        return gaeaAuthorityService;
    }

    @Override
    public GaeaAuthority getEntity() {
        return new GaeaAuthority();
    }

    @Override
    public GaeaAuthorityDTO getDTO() {
        return new GaeaAuthorityDTO();
    }


    /**
     * 权限树
     * @return
     */
    @GetMapping("/authority/tree/{org}")
    public ResponseBean authorityTree(@PathVariable("org") String org) {
        List<TreeNode> treeNodes = gaeaAuthorityService.authorityTree();

        //查询当前用户拥有的权限
        List<GaeaRoleAuthority> gaeaRoleAuthorities = gaeaAuthorityService.userAuthorities(org);
        //路径
        List authorities  = gaeaRoleAuthorities.stream().map(GaeaRoleAuthority::getAuthorityPath).collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>(2);
        result.put("has", authorities);
        result.put("all", treeNodes);
        return responseSuccessWithData(result);
    }


    /**
     * 分配
     * @param gaeaAuthorityDTO
     * @return
     */
    @PostMapping("/role/authority")
    public ResponseBean roleAuthority(@RequestBody GaeaAuthorityDTO gaeaAuthorityDTO) {
        String roleCode = gaeaAuthorityDTO.getRoleCode();
        if (StringUtils.isBlank(roleCode)) {
            return responseSuccess();
        }
        List<String> authorities = gaeaAuthorityDTO.getAuthorities();
        if (CollectionUtils.isEmpty(authorities)) {
            return responseSuccess();
        }
        gaeaAuthorityService.insertRoleAuthority(roleCode, authorities);
        return responseSuccess();
    }
}
