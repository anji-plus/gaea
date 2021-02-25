package com.anji.plus.gaea.auth.modules.menuextension.dao;

import com.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.anji.plus.gaea.auth.modules.menuextension.entity.GaeaMenuUserExtension;
import com.anji.plus.gaea.auth.modules.menuextension.controller.dto.GaeaMenuUserExtensionListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (GaeaMenuUserExtension)Mapper
 *
 * @author makejava
 * @since 2021-02-04 17:15:36
 */
@Mapper
public interface GaeaMenuUserExtensionMapper extends GaeaBaseMapper<GaeaMenuUserExtension> {

    /**
     * 查询用户所配置的菜单自定义列
     * @param username
     * @param menuCode
     * @param tableCode
     * @return
     */
    List<GaeaMenuUserExtensionListDTO> getByUser(@Param("userName")String username,@Param("menuCode")String menuCode,@Param("tableCode")String tableCode);

}