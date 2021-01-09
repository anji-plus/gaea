package com.anji.mirror.auth.mapper;

import com.anji.mirror.auth.domain.po.UserRoleOrgPO;
import com.anji.mirror.auth.domain.vo.UserRoleOrgVO;
import com.anji.mirror.auth.domain.vo.UserVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-09-07
 */
public interface UserRoleOrgMapper extends BaseMapper<UserRoleOrgPO> {

    /** XML 自定义分页
     * @param page
     * @param userRoleOrgVO
     * @return
     */
    IPage<UserRoleOrgVO> queryByPage(Page<?> page, @Param("userRoleOrgVO")UserRoleOrgVO userRoleOrgVO);


    /**
     * 查询对应role_id的用户 角色 组织关联关系
     * @param userRoleOrgVO
     * @return
     */
    List<UserRoleOrgVO>  queryUserRoleOrgByRoleId(@Param("userRoleOrgVO")UserRoleOrgVO userRoleOrgVO);
}
