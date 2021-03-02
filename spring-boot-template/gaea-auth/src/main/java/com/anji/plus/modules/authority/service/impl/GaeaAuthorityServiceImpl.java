package com.anji.plus.modules.authority.service.impl;

import com.anji.plus.gaea.bean.TreeNode;
import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.anji.plus.gaea.holder.UserContentHolder;
import com.anji.plus.modules.authority.dao.GaeaAuthorityMapper;
import com.anji.plus.modules.authority.dao.GaeaRoleAuthorityMapper;
import com.anji.plus.modules.authority.dao.entity.GaeaAuthority;
import com.anji.plus.modules.authority.dao.entity.GaeaRoleAuthority;
import com.anji.plus.modules.authority.service.GaeaAuthorityService;
import com.anji.plus.modules.user.service.GaeaUserService;
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
    private GaeaRoleAuthorityMapper gaeaRoleAuthorityMapper;

    @Autowired
    private GaeaUserService gaeaUserService;

    @Override
    public GaeaBaseMapper<GaeaAuthority> getMapper() {
        return  gaeaAuthorityMapper;
    }


    @Override
    public List<TreeNode> authorityTree() {
        List<GaeaAuthority> gaeaAuthorities = gaeaAuthorityMapper.selectList(Wrappers.lambdaQuery());

        Map<String, String> codeNameMap = gaeaAuthorities.stream()
                .filter(gaeaAuthority -> StringUtils.isNotBlank(gaeaAuthority.getAuthName()))
                .collect(Collectors.toMap(GaeaAuthority::getPath, GaeaAuthority::getAuthName));

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
                    nodeMethod.setId(beanAuthority.getPath());
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
     * @return
     */
    @Override
    public List<GaeaRoleAuthority> userAuthorities(String org) {

        String username = UserContentHolder.getContext().getUsername();
        //查询当前用户指定机构的角色
        List<String> roles = gaeaUserService.getRoleByUserOrg(username, org);
        if (CollectionUtils.isEmpty(roles)) {
            return new ArrayList<>();
        }

        LambdaQueryWrapper<GaeaRoleAuthority> wrapper = Wrappers.lambdaQuery();
        wrapper.in(GaeaRoleAuthority::getRoleCode, roles);
        List<GaeaRoleAuthority> gaeaRoleAuthorities = gaeaRoleAuthorityMapper.selectList(wrapper);
        return gaeaRoleAuthorities;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertRoleAuthority(String role, List<String> authorities) {
        List<GaeaAuthority> gaeaAuthorities = list(Wrappers.emptyWrapper());

        Map<String, String> pathCodeMap = gaeaAuthorities.stream().collect(Collectors.toMap(GaeaAuthority::getPath, GaeaAuthority::getAuthCode, (v1, v2) -> v2));


        List<GaeaRoleAuthority> GaeaRoleAuthorities = authorities.stream().map(authority -> {
            GaeaRoleAuthority gaeaRoleAuthority = new GaeaRoleAuthority();
            gaeaRoleAuthority.setRoleCode(role);
            gaeaRoleAuthority.setAuthorityPath(authority);
            gaeaRoleAuthority.setAuthCode(pathCodeMap.get(authority));

            return gaeaRoleAuthority;
        }).collect(Collectors.toList());

        LambdaQueryWrapper<GaeaRoleAuthority> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GaeaRoleAuthority::getRoleCode, role);
        //先删除指定角色的权限
        gaeaRoleAuthorityMapper.delete(wrapper);

        //保存
        gaeaRoleAuthorityMapper.insertBatch(GaeaRoleAuthorities);

    }
}
