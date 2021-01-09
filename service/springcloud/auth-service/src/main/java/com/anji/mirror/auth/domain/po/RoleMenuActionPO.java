package com.anji.mirror.auth.domain.po;

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
@TableName("t_role_menu_action")
public class RoleMenuActionPO implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 角色id */
    private Long roleId;

    /** 菜单id */
    private Long menuId;

    /** 按钮id */
    private Long actionId;


}
