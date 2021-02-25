package com.anjiplus.template.auth.modules.menu.dao;

import com.anjiplus.template.auth.modules.menu.dao.entity.GaeaMenu;
import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单表(GaeaMenu)Mapper
 *
 * @author lr
 * @since 2021-02-02 13:36:43
 */
@Mapper
public interface GaeaMenuMapper extends GaeaBaseMapper<GaeaMenu> {


}
