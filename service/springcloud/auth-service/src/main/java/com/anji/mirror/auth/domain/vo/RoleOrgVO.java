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
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleOrgVO implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键 */
    private Long roleOrgId;

    /** roleid */
    private Long roleId;

    /** 组织id */
    private Long orgId;

    /************************** 以下为非原表字段 ****************************/

    /** 角色名字 */
    private String roleName;

    /** 组织名字 */
    private String orgName;


}
