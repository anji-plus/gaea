package com.anji.mirror.auth.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 运营角色权限表
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleMenuActionVO implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键 */
      private Long id;

    /** 角色id */
    private Long roleId;

    /** 菜单id */
    private Long menuId;

    /** 按钮id */
    private Long actionId;

    /************************** 以下为非原表字段 ****************************/


}
