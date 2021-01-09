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
@TableName("t_menu_action")
public class MenuActionPO implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键 */
    @TableId(value = "menu_action_id", type = IdType.AUTO)
    private Long menuActionId;

    /** 菜单id */
    private Long menuId;

    /** 操作id，如按钮 */
    private Long actionId;


}
