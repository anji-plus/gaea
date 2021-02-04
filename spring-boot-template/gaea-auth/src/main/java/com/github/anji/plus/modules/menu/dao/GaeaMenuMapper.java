package com.github.anji.plus.modules.menu.dao;

import com.github.anji.plus.modules.menu.dao.entity.GaeaMenu;
import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单表(GaeaMenu)Mapper
 *
 * @author lirui
 * @since 2021-02-02 13:36:43
 */
@Mapper
public interface GaeaMenuMapper extends GaeaBaseMapper<GaeaMenu> {


}
