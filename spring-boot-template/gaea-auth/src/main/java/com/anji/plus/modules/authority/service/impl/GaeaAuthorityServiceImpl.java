package com.anji.plus.modules.authority.service.impl;

import com.anji.plus.gaea.bean.TreeNode;
import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.anji.plus.modules.authority.dao.GaeaAuthorityMapper;
import com.anji.plus.modules.authority.dao.entity.GaeaAuthority;
import com.anji.plus.modules.authority.service.GaeaAuthorityService;
import com.anji.plus.modules.menu.dao.GaeaMenuAuthorityMapper;
import com.anji.plus.modules.menu.dao.entity.GaeaMenuAuthority;
import com.anji.plus.modules.role.dao.GaeaRoleMenuAuthorityMapper;
import com.anji.plus.modules.role.dao.entity.GaeaRoleMenuAuthority;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单表(GaeaAuthority)ServiceImpl
 *
 * @author lirui
 * @since 2021-03-01 10:03:51
 */
@Service
public class GaeaAuthorityServiceImpl implements GaeaAuthorityService {

    @Autowired
    private GaeaAuthorityMapper  gaeaAuthorityMapper;

    @Autowired
    private GaeaRoleMenuAuthorityMapper gaeaRoleMenuAuthorityMapper;

    @Autowired
    private GaeaMenuAuthorityMapper gaeaMenuAuthorityMapper;

    @Override
    public GaeaBaseMapper<GaeaAuthority> getMapper() {
        return  gaeaAuthorityMapper;
    }


    @Override
    public List<TreeNode> authorityTree() {
        List<GaeaAuthority> gaeaAuthorities = gaeaAuthorityMapper.selectList(Wrappers.lambdaQuery());

        Map<String, String> codeNameMap = gaeaAuthorities.stream()
                .filter(gaeaAuthority -> StringUtils.isNotBlank(gaeaAuthority.getAuthName()))
                .collect(Collectors.toMap(GaeaAuthority::getAuthCode, GaeaAuthority::getAuthName));

        Map<String, List<GaeaAuthority>> applicationTree = gaeaAuthorities.stream()
                .collect(Collectors.groupingBy(GaeaAuthority::getApplicationName));

        List<TreeNode> result = new ArrayList<>();

        applicationTree.entrySet().stream().forEach(entry -> {
            TreeNode treeNode = new TreeNode();
            result.add(treeNode);
            treeNode.setId(entry.getKey());
            if (codeNameMap.containsKey(entry.getKey())) {
                treeNode.setLabel(codeNameMap.get(entry.getKey()));
            } else {
                treeNode.setLabel(entry.getKey());
            }

            List<TreeNode> children = new ArrayList<>();
            treeNode.setChildren(children);
            //controller的Bean名称与方法的
            Map<String, List<GaeaAuthority>> beanMap = entry.getValue().stream().collect(Collectors.groupingBy(GaeaAuthority::getParentCode));

            beanMap.entrySet().stream().forEach(e -> {
                TreeNode node = new TreeNode();
                children.add(node);
                node.setId(e.getKey());
                if (codeNameMap.containsKey(e.getKey())) {
                    node.setLabel(codeNameMap.get(e.getKey()));
                } else {
                    node.setLabel(e.getKey());
                }
                List<TreeNode> beanChildren = new ArrayList<>();
                node.setChildren(beanChildren);

                List<GaeaAuthority> beanAuthorities = e.getValue();
                beanAuthorities.stream().forEach(beanAuthority -> {
                    TreeNode nodeMethod = new TreeNode();
                    beanChildren.add(nodeMethod);
                    nodeMethod.setId(beanAuthority.getAuthCode());
                    if (codeNameMap.containsKey(beanAuthority.getAuthCode())) {
                        nodeMethod.setLabel(beanAuthority.getAuthName());
                    } else {
                        nodeMethod.setLabel(beanAuthority.getAuthCode());
                    }
                });
            });


        });
        return result;
    }

    /**
     * 获取指定角色的权限
     * @param org
     * @param roles
     * @return
     */
    @Override
    public List<GaeaRoleMenuAuthority> userAuthorities(String org, List<String> roles) {

        LambdaQueryWrapper<GaeaRoleMenuAuthority> wrapper = Wrappers.lambdaQuery();
        wrapper.in(GaeaRoleMenuAuthority::getRoleCode, roles);
        List<GaeaRoleMenuAuthority> gaeaRoleAuthorities = gaeaRoleMenuAuthorityMapper.selectList(wrapper);
        return gaeaRoleAuthorities;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertRoleAuthority(String role, List<String> authorities) {
        List<GaeaAuthority> gaeaAuthorities = list(Wrappers.emptyWrapper());

        List<GaeaMenuAuthority> gaeaMenuAuthorities = gaeaMenuAuthorityMapper.selectList(Wrappers.emptyWrapper());

        Map<String, String> menuAuthorityMap = gaeaMenuAuthorities.stream().collect(Collectors.toMap(GaeaMenuAuthority::getAuthCode, GaeaMenuAuthority::getMenuCode));

        Map<String, String> pathCodeMap = gaeaAuthorities.stream().collect(Collectors.toMap(GaeaAuthority::getPath, GaeaAuthority::getAuthCode, (v1, v2) -> v2));

        LambdaQueryWrapper<GaeaRoleMenuAuthority> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GaeaRoleMenuAuthority::getRoleCode, role);
        //先删除指定角色的权限
        gaeaRoleMenuAuthorityMapper.delete(wrapper);

        //保存
        if(!CollectionUtils.isEmpty(authorities)) {
            List<GaeaRoleMenuAuthority> GaeaRoleAuthorities = authorities.stream().map(authority -> {
                GaeaRoleMenuAuthority gaeaRoleMenuAuthority = new GaeaRoleMenuAuthority();
                gaeaRoleMenuAuthority.setRoleCode(role);
                gaeaRoleMenuAuthority.setAuthPath(authority);
                gaeaRoleMenuAuthority.setMenuCode(menuAuthorityMap.get(authority));
                gaeaRoleMenuAuthority.setAuthCode(pathCodeMap.get(authority));

                return gaeaRoleMenuAuthority;
            }).collect(Collectors.toList());
            gaeaRoleMenuAuthorityMapper.insertBatch(GaeaRoleAuthorities);
        }

    }
}
