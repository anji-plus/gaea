package com.anjiplus.template.auth.modules.menu.service.impl;

import com.anjiplus.template.common.MagicValueConstants;
import com.anjiplus.template.auth.modules.action.dao.GaeaActionMapper;
import com.anjiplus.template.auth.modules.action.dao.entity.GaeaAction;
import com.anjiplus.template.auth.modules.menu.controller.dto.GaeaMenuDTO;
import com.anjiplus.template.auth.modules.menu.controller.dto.TreeDTO;
import com.anjiplus.template.auth.modules.menu.controller.param.MenuActionReqParam;
import com.anjiplus.template.auth.modules.role.dao.entity.GaeaRoleMenuAction;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.anji.plus.gaea.bean.TreeNode;
import com.anji.plus.gaea.constant.Enabled;
import com.anjiplus.template.auth.modules.menu.controller.dto.GaeaLeftMenuDTO;
import com.anjiplus.template.auth.modules.menu.dao.GaeaMenuActionMapper;
import com.anjiplus.template.auth.modules.menu.dao.entity.GaeaMenu;
import com.anjiplus.template.auth.modules.menu.dao.GaeaMenuMapper;
import com.anjiplus.template.auth.modules.menu.dao.entity.GaeaMenuAction;
import com.anjiplus.template.auth.modules.menu.service.GaeaMenuService;
import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.anjiplus.template.auth.modules.role.dao.GaeaRoleMenuActionMapper;
import com.anjiplus.template.auth.modules.role.service.GaeaRoleService;
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
    private GaeaRoleMenuActionMapper gaeaRoleMenuActionMapper;

    @Autowired
    private GaeaMenuActionMapper gaeaMenuActionMapper;

    @Autowired
    private GaeaRoleService gaeaRoleService;

    @Autowired
    private GaeaActionMapper gaeaActionMapper;

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
        LambdaQueryWrapper<GaeaRoleMenuAction> queryWrapper = Wrappers.<GaeaRoleMenuAction>lambdaQuery()
                .in(GaeaRoleMenuAction::getRoleCode, roles);


        //菜单编码与其下的按钮
        Map<String, Set<String>> menuActionMap = gaeaRoleMenuActionMapper.selectList(queryWrapper)
                .stream()
                .collect(Collectors.groupingBy(GaeaRoleMenuAction::getMenuCode,
                        Collectors.mapping(GaeaRoleMenuAction::getActionCode, Collectors.toSet())));
        if(null==menuActionMap||menuActionMap.size()<=0){
            return new ArrayList<>();
        }
        //获取当前用户所有菜单
        LambdaQueryWrapper<GaeaMenu> resourceQueryWrapper = Wrappers.<GaeaMenu>lambdaQuery()
                .eq(GaeaMenu::getEnabled, Enabled.YES.getValue())
                .in(GaeaMenu::getMenuCode, menuActionMap.keySet())
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
            setDtoMeta(gaeaMenuDTO);

            gaeaMenuDTO.setPermission(menuActionMap.get(gaeaMenuDTO.getMenuCode()));
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
            return setChild(gaeaLeftMenuDTO, menuMap);
        }).sorted(Comparator.comparing(GaeaLeftMenuDTO::getSort)).collect(Collectors.toList());

        return menuResult;
    }

    private void setDtoMeta(GaeaLeftMenuDTO gaeaMenuDTO) {
        Map<String, String> meta = new HashMap<>(2);
        meta.put("title", gaeaMenuDTO.getName());
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
        //设置元数据
        setDtoMeta(gaeaMenuDTO);

        //当没有父菜单code时，递归结束
        if(StringUtils.isEmpty(gaeaMenuDTO.getParentCode())|| MagicValueConstants.STRING_ZERO.equals(gaeaMenuDTO.getParentCode())) {
            return gaeaMenuDTO;
        }
        GaeaMenu gaeaMenu = menuMap.get(gaeaMenuDTO.getMenuCode());
        GaeaLeftMenuDTO dto = new GaeaLeftMenuDTO();
        BeanUtils.copyProperties(gaeaMenu, dto);
        dto.setName(gaeaMenu.getMenuName());
        return setChild(dto, menuMap);
    }

    /**
     * 获取所有菜单按钮树
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

        //查询菜单与按钮的对应关系
        List<GaeaMenuAction> gaeaMenuActions = gaeaMenuActionMapper.selectList(Wrappers.emptyWrapper());

        //给菜单分组
        Map<String, List<TreeNode>> menuActionMap = gaeaMenuActions.stream()
                .collect(Collectors.groupingBy(GaeaMenuAction::getMenuCode,Collectors.mapping(action -> {
                    TreeNode treeNode = new TreeNode();
                    String key = action.getMenuCode() + ":"+ action.getActionCode();
                    treeNode.setId(key);
                    treeNode.setLabel(key);
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
        List<TreeNode> result = setTreeNode(menuActionMap, gaeaAuthMenuDTOS, rootResources);

        return result;
    }

    /**
     * 设置子节点
     * @param menuActionMap
     * @param gaeaAuthMenuDTOS
     * @param rootResources
     * @return
     */
    private List<TreeNode> setTreeNode(Map<String, List<TreeNode>> menuActionMap, List<GaeaMenuDTO> gaeaAuthMenuDTOS, List<GaeaMenuDTO> rootResources) {
        return rootResources.stream().map(resource -> {
            TreeNode node = new TreeNode();
            node.setId(resource.getMenuCode());
            node.setLabel(resource.getMenuName());
            //树子集合
            List<TreeNode> treeResult = new ArrayList<>();
            //菜单对应的actions
            List<TreeNode> actionTreeNodes = menuActionMap.get(resource.getMenuCode());
            if (!CollectionUtils.isEmpty(actionTreeNodes)) {
                treeResult.addAll(actionTreeNodes);
            }

            //菜单子菜单
            List<TreeNode> subResourcesTemp = getSubResources(resource.getMenuCode(), gaeaAuthMenuDTOS, menuActionMap);
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
    public List<TreeNode> getSubResources(String menuCode, List<GaeaMenuDTO> resources,Map<String, List<TreeNode>> menuActionMap) {
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
        List<TreeNode> result = setTreeNode(menuActionMap, resources, subResources);
        return result;
    }

    /**
     * 获取当前用户拥有的按钮权限
     * @param roleCode
     * @return
     */
    @Override
    public List<String> getSelectActions(String roleCode) {
        LambdaQueryWrapper<GaeaRoleMenuAction> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(GaeaRoleMenuAction::getRoleCode, roleCode);
        List<GaeaRoleMenuAction> gaeaRoleMenuActions = gaeaRoleMenuActionMapper.selectList(queryWrapper);

        return gaeaRoleMenuActions.stream()
                .map(action -> action.getMenuCode() + ":" + action.getActionCode())
                .collect(Collectors.toList());
    }

    @Override
    public TreeDTO queryActionTreeForMenu(String menuCode) {
        //该菜单已经关联的按钮
        QueryWrapper<GaeaMenuAction> menuQueryWrapper = new QueryWrapper<GaeaMenuAction>();
        menuQueryWrapper.lambda().select(GaeaMenuAction::getActionCode).eq(GaeaMenuAction::getMenuCode,menuCode);
        List<GaeaMenuAction> menuActionList = gaeaMenuActionMapper.selectList(menuQueryWrapper);
        List<String> actionCodeOfMenu = new ArrayList<>();
        menuActionList.stream().forEach(menuAction -> {
            actionCodeOfMenu.add(menuAction.getActionCode());
        });
        //所有的按钮
        QueryWrapper<GaeaAction> queryWrapper = new QueryWrapper<GaeaAction>();
        queryWrapper.lambda().select(GaeaAction::getActionCode,GaeaAction::getActionName)
                .eq(GaeaAction::getEnabled,Enabled.YES.getValue());
        List<GaeaAction> actionList = gaeaActionMapper.selectList(queryWrapper);
        List<TreeNode> tree=new ArrayList<TreeNode>();
        actionList.stream().forEach(action ->{
            TreeNode treeVO = new TreeNode();
            treeVO.setId(action.getActionCode());
            treeVO.setLabel(action.getActionName());
            tree.add(treeVO);
        });
        TreeDTO result=new TreeDTO();
        result.setCheckedCodes(actionCodeOfMenu);
        result.setTreeDatas(tree);
        return result;
    }

    @Override
    @Transactional
    public Boolean saveActionTreeForMenu(MenuActionReqParam requestModel) {
        //清除菜单的旧关联按钮
        QueryWrapper<GaeaMenuAction> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.lambda().eq(GaeaMenuAction::getMenuCode,requestModel.getMenuCode());
        gaeaMenuActionMapper.delete(menuQueryWrapper);
        //保存新关联
        List<String> actionCodeList=requestModel.getActionCodes();
        List<GaeaMenuAction> gaeaMenuActionList= actionCodeList.stream().map(e->{
            GaeaMenuAction menuAction = new GaeaMenuAction();
            menuAction.setActionCode(e);
            menuAction.setMenuCode(requestModel.getMenuCode());
            return menuAction;
        }).collect(Collectors.toList());

        gaeaMenuActionMapper.insertBatch(gaeaMenuActionList);
        //根据menuId清除t_role_menu_action的已勾选的actionIds
        QueryWrapper<GaeaRoleMenuAction> roleMenuActionQueryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<GaeaRoleMenuAction> lambdaQueryWrapper= roleMenuActionQueryWrapper.lambda();
        lambdaQueryWrapper.eq(GaeaRoleMenuAction::getMenuCode,requestModel.getMenuCode());
        if (actionCodeList.size() > 0) {
            lambdaQueryWrapper.notIn(GaeaRoleMenuAction::getActionCode,actionCodeList);
        }
        gaeaRoleMenuActionMapper.delete(lambdaQueryWrapper);
        return true;
    }

}
