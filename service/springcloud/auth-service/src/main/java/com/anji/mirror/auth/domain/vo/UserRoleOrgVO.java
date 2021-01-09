package com.anji.mirror.auth.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-09-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserRoleOrgVO implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键 */
      private Long id;

    /** 用户ID */
    private Long userId;

    /** 角色ID */
    private Long roleId;

    /** 组织ID */
    private Long orgId;

    /************************** 以下为非原表字段 ****************************/

    /** 菜单代码 */
    private String menuCode;

    /** 权限动作 */
    private String actionCode;

    /** 菜单的父id */
    private Long parentId;

    /** 组织代码 */
    private String orgCode;

    /** 真实性 */
    private String realName;

    /** 登录名 */
    private String loginName;

    /** 角色名字 */
    private String roleName;

    /** 组织名字 */
    private String orgName;

}
