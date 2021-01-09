package com.anji.mirror.auth.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleVO implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键 */
      private Long roleId;

    /** 角色名称 */
    private String roleName;

    /** 角色说明 */
    private String roleDesc;

    /** 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG */
    private Integer enableFlag;

    /**  0--未删除 1--已删除 DIC_NAME=DEL_FLAG */
    private Integer deleteFlag;

    /** 创建人 */
    private String createdBy;

    /** 创建时间 */
    private LocalDateTime createdTime;

    /** 更新人 */
    private String updatedBy;

    /** 更新时间 */
    private LocalDateTime updatedTime;

    /************************** 以下为非原表字段 ****************************/

    /** 角色所拥有的菜单操作组合 */
    private List<String> menuActionIds;

    /** 角色所关联的组织id */
    private List<Long> orgIds;

    /**
     * 用户_组织 列表
     */
    private List<String> userIdOrgIds;
}
