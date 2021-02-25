package com.anji.plus.modules.menuextension.service.impl;

import com.anji.plus.common.RespCommonCode;
import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.anji.plus.gaea.exception.BusinessExceptionBuilder;
import com.anji.plus.modules.menuextension.dao.GaeaMenuExtensionMapper;
import com.anji.plus.modules.menuextension.entity.GaeaMenuExtension;
import com.anji.plus.modules.menuextension.entity.GaeaMenuUserExtension;
import com.anji.plus.modules.menuextension.service.GaeaMenuUserExtensionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.anji.plus.modules.menuextension.controller.dto.GaeaMenuUserExtensionListDTO;
import com.github.anji.plus.modules.menuextension.dao.GaeaMenuUserExtensionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * (GaeaMenuUserExtension)ServiceImpl
 *
 * @author makejava
 * @since 2021-02-04 17:15:36
 */
@Service
public class GaeaMenuUserExtensionServiceImpl implements GaeaMenuUserExtensionService {
    @Autowired
    private GaeaMenuUserExtensionMapper gaeaMenuUserExtensionMapper;
    @Autowired
    private GaeaMenuExtensionMapper gaeaMenuExtensionMapper;

    @Override
    public GaeaBaseMapper<GaeaMenuUserExtension> getMapper() {
        return gaeaMenuUserExtensionMapper;
    }


    @Override
    public List<GaeaMenuUserExtensionListDTO> getUserMenuList(String currentUser, String menuCode, String tableCode) {
        List<GaeaMenuUserExtensionListDTO> menuUserCustomExtensionListVos = gaeaMenuUserExtensionMapper.getByUser(currentUser, menuCode, tableCode);
        if (CollectionUtils.isEmpty(menuUserCustomExtensionListVos)) {
            LambdaQueryWrapper<GaeaMenuExtension> wrapper= Wrappers.lambdaQuery();
            wrapper.eq(GaeaMenuExtension::getMenuCode,menuCode)
                    .and(e->e.eq(GaeaMenuExtension::getTableCode,tableCode));
            List<GaeaMenuExtension> list = gaeaMenuExtensionMapper.selectList(wrapper);
            if (!CollectionUtils.isEmpty(list)) {
                List<GaeaMenuUserExtension> userList=new ArrayList<>();
                list.forEach(menuGeneralExtension -> {
                    GaeaMenuUserExtension menuUserCustomExtension = new GaeaMenuUserExtension();
                    BeanUtils.copyProperties(menuGeneralExtension, menuUserCustomExtension);
                    menuUserCustomExtension.setUsername(currentUser);
                    userList.add(menuUserCustomExtension);
                });
                gaeaMenuUserExtensionMapper.insertBatch(userList);
            }
            menuUserCustomExtensionListVos = gaeaMenuUserExtensionMapper.getByUser(currentUser, menuCode, tableCode);
        }
        menuUserCustomExtensionListVos = sort(menuUserCustomExtensionListVos);
        return menuUserCustomExtensionListVos;
    }

    /**
     * 组装、排序
     *
     * @param menuUserCustomExtensionListVos
     * @return
     */
    private List<GaeaMenuUserExtensionListDTO> sort(List<GaeaMenuUserExtensionListDTO> menuUserCustomExtensionListVos) {
        //取一级菜单
        List<GaeaMenuUserExtensionListDTO> list = menuUserCustomExtensionListVos.stream().filter(menuUserCustomExtensionListVo ->
                StringUtils.isEmpty(menuUserCustomExtensionListVo.getGroupName())).collect(Collectors.toList());
        //取二级菜单
        menuUserCustomExtensionListVos.removeAll(list);
        //二级菜单分组
        Map<String, List<GaeaMenuUserExtensionListDTO>> collect = menuUserCustomExtensionListVos.stream().collect(Collectors.groupingBy(GaeaMenuUserExtensionListDTO::getGroupName));
        //组装虚拟一级菜单，子菜单是二级菜单
        for (Map.Entry<String, List<GaeaMenuUserExtensionListDTO>> entry : collect.entrySet()) {
            GaeaMenuUserExtensionListDTO menuUserCustomExtensionListVo = new GaeaMenuUserExtensionListDTO();
            List<GaeaMenuUserExtensionListDTO> value = entry.getValue();
            AtomicReference<Integer> visible = new AtomicReference<>(0);
            value.forEach(menuUserCustomExtensionListVo1 -> {
                if (null != menuUserCustomExtensionListVo1.getVisible() && 1 == menuUserCustomExtensionListVo1.getVisible()) {
                    visible.set(1);
                }
            });
            GaeaMenuUserExtensionListDTO son = value.get(0);
            menuUserCustomExtensionListVo.setName(son.getGroupName());
            menuUserCustomExtensionListVo.setSortNo(son.getSortNo());
            menuUserCustomExtensionListVo.setChildren(value);
            menuUserCustomExtensionListVo.setVisible(visible.get());
            list.add(menuUserCustomExtensionListVo);
        }
        //按照排序号重新排序
        list = list.stream().sorted(Comparator.comparingLong(GaeaMenuUserExtensionListDTO::getSortNo)).collect(Collectors.toList());
        return list;
    }

    @Override
    @Transactional
    public Boolean batchUpdate(List<GaeaMenuUserExtensionListDTO> reqData) {
        if (reqData == null || reqData.isEmpty()) {
            return false;
        }
        ArrayList<GaeaMenuUserExtension> menuUserCustomExtensions = new ArrayList<>();
        AtomicInteger i = new AtomicInteger();
        reqData.forEach(menuUserCustomExtensionListVo -> {
            i.getAndIncrement();
            GaeaMenuUserExtension menuUserCustomExtension = new GaeaMenuUserExtension();
            BeanUtils.copyProperties(menuUserCustomExtensionListVo, menuUserCustomExtension);
            menuUserCustomExtension.setSortNo(i.intValue());
            menuUserCustomExtensions.add(menuUserCustomExtension);
        });
        if (!(checkSortNo(menuUserCustomExtensions))) {
            throw BusinessExceptionBuilder.build(RespCommonCode.MENU_TABLE_CODE_EXIST);
        }
        menuUserCustomExtensions.stream().forEach(e->{
            gaeaMenuUserExtensionMapper.updateById(e);
        });
        return true;
    }

    /**
     * 校验子菜单是否移除父菜单范围
     *
     * @param menuUserCustomExtensions
     * @return
     */
    private boolean checkSortNo(ArrayList<GaeaMenuUserExtension> menuUserCustomExtensions) {
        //过滤掉一级菜单
        List<GaeaMenuUserExtension> collect1 = menuUserCustomExtensions.stream().filter(menuUserCustomExtensionListVo ->
                StringUtils.isNotEmpty(menuUserCustomExtensionListVo.getGroupName())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(collect1)) {
            //按照groupName分组
            Map<String, List<GaeaMenuUserExtension>> collect = collect1.stream().collect(Collectors.groupingBy(GaeaMenuUserExtension::getGroupName));
            for (Map.Entry<String, List<GaeaMenuUserExtension>> entry : collect.entrySet()) {
                List<GaeaMenuUserExtension> value = entry.getValue();
                int size = value.size();
                if (size > 1) {
                    //按照排序号重新排序
                    value = value.stream().sorted(Comparator.comparingLong(GaeaMenuUserExtension::getSortNo)).collect(Collectors.toList());
                    //（子菜单最大排序号-子菜单最小排序号）是否等于（子菜单个数-1）
                    // 等于是正确排序，不等于是错误排序
                    if ((value.get(size - 1).getSortNo() - value.get(0).getSortNo()) != (size - 1)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}