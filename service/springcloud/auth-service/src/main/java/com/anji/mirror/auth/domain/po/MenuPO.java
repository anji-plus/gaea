package com.anji.mirror.auth.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
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
@TableName("t_menu")
public class MenuPO implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键 */
    @TableId(value = "menu_id", type = IdType.AUTO)
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


}
