package com.anji.plus.modules.menu.service.impl;

import com.anji.plus.common.MagicValueConstants;
import com.anji.plus.gaea.bean.TreeNode;
import com.anji.plus.gaea.constant.Enabled;
import com.anji.plus.gaea.constant.GaeaConstant;
import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.anji.plus.modules.menu.controller.dto.GaeaLeftMenuDTO;
import com.anji.plus.modules.menu.controller.dto.GaeaMenuDTO;
import com.anji.plus.modules.menu.dao.GaeaMenuAuthorityMapper;
import com.anji.plus.modules.menu.dao.GaeaMenuMapper;
import com.anji.plus.modules.menu.dao.entity.GaeaMenu;
import com.anji.plus.modules.menu.dao.entity.GaeaMenuAuthority;
import com.anji.plus.modules.menu.service.GaeaMenuService;
import com.anji.plus.modules.role.dao.GaeaRoleMenuAuthorityMapper;
import com.anji.plus.modules.role.dao.entity.GaeaRoleMenuAuthority;
import com.anji.plus.modules.role.service.GaeaRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单表(GaeaMenu)ServiceImpl
 *
 * @author lr
 * @since 2021-02-02 13:36:43
 */
@Service
public class GaeaMenuServiceImpl implements GaeaMenuService {

    @Autowired
    private GaeaMenuMapper  gaeaMenuMapper;

    @Autowired
    private GaeaRoleMenuAuthorityMapper gaeaRoleMenuAuthorityMapper;

    @Autowired
    private GaeaMenuAuthorityMapper gaeaMenuAuthorityMapper;

    @Autowired
    private GaeaRoleService gaeaRoleService;

    @Override
    public GaeaBaseMapper<GaeaMenu> getMapper() {
        return  gaeaMenuMapper;
    }


    /**
     * 获取角色对应的菜单
     *
     * @param roles
     * @return
     */
    @Override
    public List<GaeaLeftMenuDTO> getMenus(List<String> roles) {
        if(CollectionUtils.isEmpty(roles)) {
            return new ArrayList<>();
        }

        //查询指定角色对应的菜单按钮
        LambdaQueryWrapper<GaeaRoleMenuAuthority> queryWrapper = Wrappers.<GaeaRoleMenuAuthority>lambdaQuery()
                .in(GaeaRoleMenuAuthority::getRoleCode, roles);


        //菜单编码与其下的按钮
        Map<String, Set<String>> menuAuthMap = gaeaRoleMenuAuthorityMapper.selectList(queryWrapper)
                .stream()
                .collect(Collectors.groupingBy(GaeaRoleMenuAuthority::getMenuCode,
                        Collectors.mapping(GaeaRoleMenuAuthority::getAuthCode, Collectors.toSet())));


        if(CollectionUtils.isEmpty(menuAuthMap)){
            return new ArrayList<>();
        }

        //获取当前用户所有菜单
        LambdaQueryWrapper<GaeaMenu> resourceQueryWrapper = Wrappers.<GaeaMenu>lambdaQuery()
                .eq(GaeaMenu::getEnabled, Enabled.YES.getValue())
                .in(GaeaMenu::getMenuCode, menuAuthMap.keySet())
                .orderByAsc(GaeaMenu::getSort);

        //当前用户所有的叶子菜单
        List<GaeaMenu> leafMenuList = gaeaMenuMapper.selectList(resourceQueryWrapper);
        if(CollectionUtils.isEmpty(leafMenuList)){
            return new ArrayList<>();
        }
        //查询所有菜单
        List<GaeaMenu> allMenus = gaeaMenuMapper.selectList(Wrappers.emptyWrapper());

        //菜单编码与菜单的对应关系
        Map<String, GaeaMenu> menuMap = allMenus.stream().collect(Collectors.toMap(GaeaMenu::getMenuCode, gaeaMenu -> gaeaMenu));

        //转换
        List<GaeaLeftMenuDTO> leafMenus = leafMenuList.stream().map(gaeaMenu -> {
            GaeaLeftMenuDTO gaeaMenuDTO = new GaeaLeftMenuDTO();
            BeanUtils.copyProperties(gaeaMenu, gaeaMenuDTO);
            gaeaMenuDTO.setName(gaeaMenu.getMenuName());
            setDtoMeta(gaeaMenuDTO, gaeaMenu);

            gaeaMenuDTO.setPermission(menuAuthMap.get(gaeaMenuDTO.getMenuCode()));
            return gaeaMenuDTO;
        }).collect(Collectors.toList());


        //按父菜单Code分组
        Map<String, List<GaeaLeftMenuDTO>> leafParentMap = leafMenus.stream().collect(Collectors.groupingBy(GaeaLeftMenuDTO::getParentCode));

        List<GaeaLeftMenuDTO> menuResult = leafParentMap.entrySet().stream().map(entry -> {
            GaeaMenu gaeaMenu = menuMap.get(entry.getKey());
            GaeaLeftMenuDTO gaeaLeftMenuDTO = new GaeaLeftMenuDTO();
            BeanUtils.copyProperties(gaeaMenu, gaeaLeftMenuDTO);
            gaeaLeftMenuDTO.setName(gaeaMenu.getMenuName());
            gaeaLeftMenuDTO.setChildren(entry.getValue());
            //设置元数据
            setDtoMeta(gaeaLeftMenuDTO,gaeaMenu);
            return setChild(gaeaLeftMenuDTO, menuMap);
        }).sorted(Comparator.comparing(GaeaLeftMenuDTO::getSort)).collect(Collectors.toList());

        return menuResult;
    }

    private void setDtoMeta(GaeaLeftMenuDTO gaeaMenuDTO, GaeaMenu gaeaMenu) {
        Map<String, String> meta = new HashMap<>(2);
        meta.put("title", gaeaMenu.getMenuName());
        meta.put("icon", gaeaMenuDTO.getMenuIcon());
        gaeaMenuDTO.setMeta(meta);
    }


    /**
     * 递归寻找父菜单
     * @param gaeaMenuDTO
     * @param menuMap
     * @return
     */
    public GaeaLeftMenuDTO setChild(GaeaLeftMenuDTO gaeaMenuDTO, Map<String, GaeaMenu> menuMap) {

        //当没有父菜单code时，递归结束
        if(StringUtils.isEmpty(gaeaMenuDTO.getParentCode())|| MagicValueConstants.STRING_ZERO.equals(gaeaMenuDTO.getParentCode())) {
            return gaeaMenuDTO;
        }
        GaeaMenu gaeaMenu = menuMap.get(gaeaMenuDTO.getMenuCode());
        GaeaLeftMenuDTO dto = new GaeaLeftMenuDTO();
        BeanUtils.copyProperties(gaeaMenu, dto);
        dto.setName(gaeaMenu.getMenuName());
        setDtoMeta(dto, gaeaMenu);
        return setChild(dto, menuMap);
    }

    /**
     * 获取所有菜单权限树
     *
     * @return
     */
    @Override
    public List<TreeNode> getTree() {

        //获取所有菜单
        LambdaQueryWrapper<GaeaMenu> resourceQueryWrapper = Wrappers.<GaeaMenu>lambdaQuery()
                .eq(GaeaMenu::getEnabled, Enabled.YES.getValue())
                .orderByAsc(GaeaMenu::getSort);


        List<GaeaMenu> allResources = gaeaMenuMapper.selectList(resourceQueryWrapper);

        //查询菜单与权限的对应关系
        List<GaeaMenuAuthority> gaeaMenuActions = gaeaMenuAuthorityMapper.selectList(Wrappers.emptyWrapper());

        //给菜单分组
        Map<String, List<TreeNode>> menuAuthMap = gaeaMenuActions.stream()
                .collect(Collectors.groupingBy(GaeaMenuAuthority::getMenuCode,Collectors.mapping(authority -> {
                    TreeNode treeNode = new TreeNode();
                    String key = authority.getMenuCode() + GaeaConstant.REDIS_SPLIT + authority.getAuthCode();
                    treeNode.setId(key);
                    treeNode.setLabel(authority.getAuthCode());
                    return treeNode;
                }, Collectors.toList())));

        //转换DTO,用于返回
        List<GaeaMenuDTO> gaeaAuthMenuDTOS = allResources.stream().map(gaeaMenu -> {
            GaeaMenuDTO dto = new GaeaMenuDTO();
            BeanUtils.copyProperties(gaeaMenu, dto);
            return dto;
        }).collect(Collectors.toList());

        //一级菜单（没有父菜单）
        List<GaeaMenuDTO> rootResources = gaeaAuthMenuDTOS.stream().filter(resource -> StringUtils.isBlank(resource.getParentCode())).collect(Collectors.toList());

        //遍及一级菜单，找出它的下级菜单
        List<TreeNode> result = setTreeNode(gaeaAuthMenuDTOS, rootResources, menuAuthMap);

        return result;
    }

    /**
     * 设置子节点
     * @param gaeaMenuDTOS
     * @param rootResources
     * @return
     */
    private List<TreeNode> setTreeNode(List<GaeaMenuDTO> gaeaMenuDTOS, List<GaeaMenuDTO> rootResources,Map<String, List<TreeNode>> menuAuthMap) {
        return rootResources.stream().map(resource -> {
            TreeNode node = new TreeNode();
            node.setId(resource.getMenuCode());
            node.setLabel(resource.getMenuName());
            //树子集合
            List<TreeNode> treeResult = new ArrayList<>();
            //菜单对应的权限
            List<TreeNode> authTreeNodes = menuAuthMap.get(resource.getMenuCode());
            if (!CollectionUtils.isEmpty(authTreeNodes)) {
                treeResult.addAll(authTreeNodes);
            }

            //菜单子菜单
            List<TreeNode> subResourcesTemp = getSubResources(resource.getMenuCode(), gaeaMenuDTOS, menuAuthMap);
            if (!CollectionUtils.isEmpty(subResourcesTemp)) {
                treeResult.addAll(subResourcesTemp);
            }

            if (!CollectionUtils.isEmpty(treeResult)) {
                node.setChildren(treeResult);
            }
            return node;
        }).collect(Collectors.toList());
    }

    /**
     * 递归
     *
     * @param menuCode
     * @param resources
     * @return
     */
    public List<TreeNode> getSubResources(String menuCode, List<GaeaMenuDTO> resources,Map<String, List<TreeNode>> menuAuthMap) {
        List<GaeaMenuDTO> subResources = new ArrayList<>();
        resources.stream().forEach(resource -> {
            //当前code的下级菜单
            if (StringUtils.equals(menuCode, resource.getParentCode())) {
                subResources.add(resource);
            }
        });

        if (subResources.isEmpty()) {
            return new ArrayList<>();
        }

        //子菜单
        List<TreeNode> result = setTreeNode(resources, subResources, menuAuthMap);
        return result;
    }

    /**
     * 获取当前用户拥有的按钮权限
     * @param roleCode
     * @return
     */
    @Override
    public List<String> getSelectActions(String roleCode) {
        LambdaQueryWrapper<GaeaRoleMenuAuthority> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(GaeaRoleMenuAuthority::getRoleCode, roleCode);
        List<GaeaRoleMenuAuthority> gaeaRoleMenuAuthorities = gaeaRoleMenuAuthorityMapper.selectList(queryWrapper);

        return gaeaRoleMenuAuthorities.stream()
                .map(action -> action.getAuthCode())
                .collect(Collectors.toList());
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveMenuAuthorities(String menuCode, List<String> authorities) {

        List<GaeaMenuAuthority> gaeaMenuAuthorities = authorities.stream().map(authority -> {
            GaeaMenuAuthority gaeaMenuAuthority = new GaeaMenuAuthority();
            gaeaMenuAuthority.setMenuCode(menuCode);
            gaeaMenuAuthority.setAuthCode(authority);
            return gaeaMenuAuthority;
        }).collect(Collectors.toList());

        int i = gaeaMenuAuthorityMapper.insertBatch(gaeaMenuAuthorities);
        return i > 0;
    }
}
