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
public class MenuActionVO implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键 */
      private Long menuActionId;

    /** 菜单id */
    private Long menuId;

    /** 操作id，如按钮 */
    private Long actionId;

    /************************** 以下为非原表字段 ****************************/

    /** 菜单代码 */
    private String menuCode;

    /** 菜单名称 */
    private String menuName;

    /** 权限动作 */
    private String actionCode;

    /** 权限说明 */
    private String actionName;
}
