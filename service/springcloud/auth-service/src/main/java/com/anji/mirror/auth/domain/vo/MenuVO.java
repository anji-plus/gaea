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
 * 菜单表
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuVO implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键 */
      private Long menuId;

    /** 菜单代码 */
    private String menuCode;

    /** 菜单名称 */
    private String menuName;

    /** 系统/终端代码 DIC_NAME=SYSTEM_CODE */
    private String sysCode;

    /** 父级id */
    private Long parentId;

    /** 菜单路径 */
    private String menuUrl;

    /** base64图标或者iconfont字体 */
    private String menuIcon;

    /** 排序，升序 */
    private Integer sort;

    /** 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG */
    private Integer enableFlag;

    /**  0--未删除 1--已删除 DIC_NAME=DEL_FLAG */
    private Integer deleteFlag;

    /** 创建人 */
    private String createdBy;

    /** 创建时间 */
    private LocalDateTime createdTime;

    /** 修改人 */
    private String updatedBy;

    /** 修改时间 */
    private LocalDateTime updatedTime;

    /************************** 以下为非原表字段 ****************************/
    /** 菜单关联的所有按钮的actionId */
    private List<Long> actionIds;

    /** 父级菜单名称 */
    private String parentMenuName;

    /** 权限id */
    private Long actionId;

    /** 权限动作 */
    private String actionCode;

}
