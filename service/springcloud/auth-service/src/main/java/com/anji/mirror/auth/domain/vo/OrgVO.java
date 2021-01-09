package com.anji.mirror.auth.domain.vo;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 组织机构表
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrgVO implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键 */
      private Long orgId;

    /** 机构代码 */
    private String orgCode;

    /** 机构名称 */
    private String orgName;

    /** 上级组织code */
    private String orgParentCode;

    /** 上级部门名字 */
    private String orgParentName;

    /** 外部机构代码（从外系统同步过来得编码） */
    private String outOrgCode;

    /** 外部机构父级编码（从外系统同步过来得父级编码） */
    private String outOrgParentCode;

    /** 机构级别 */
    private String orgLevel;

    /** 组织类型 */
    private String orgType;

    /** 联系人 */
    private String linkman;

    /** 手机号 */
    private String mobilePhone;

    /** 固定电话 */
    private String telephone;

    /** 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG */
    private Integer enableFlag;

    /**  0--未删除 1--已删除 DIC_NAME=DEL_FLAG */
    private Integer deleteFlag;

    /** 描述信息 */
    private String remark;

    /** 创建用户编码 */
    private String createdBy;

    /** 创建时间 */
    private LocalDateTime createdTime;

    /** 修改用户编码 */
    private String updatedBy;

    /** 修改时间 */
    private LocalDateTime updatedTime;

    /************************** 以下为非原表字段 ****************************/

    /** 树结构pId */
    private String orgPCode;
    /** 是否包含子节点 */
    private boolean hasChildren = true;

    /** 用户所属组织 */
    private List<Long> orgIdList;


}
