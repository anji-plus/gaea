package com.anji.mirror.auth.mapper;

import com.anji.mirror.auth.domain.po.UserPO;
import com.anji.mirror.auth.domain.vo.UserVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
public interface UserMapper extends BaseMapper<UserPO> {

    /** XML 自定义分页
     * @param page
     * @param userVO
     * @return
     */
    IPage<UserVO> queryByPage(Page<?> page, @Param("userVO")UserVO userVO);


    List<UserVO> queryUserByUserNameOrMail(@Param("userVO")UserVO userVO);

    List<UserVO> queryByLoginNameOrPhone(@Param("userVO")UserVO userVO);
}
