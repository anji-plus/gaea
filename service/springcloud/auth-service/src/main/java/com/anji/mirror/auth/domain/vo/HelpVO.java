package com.anji.mirror.auth.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 帮助中心表
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HelpVO implements Serializable {

    private static final long serialVersionUID=1L;

    /** PK */
      private Long helpId;

    /** 帮助分类，字典=HELP_CATEGORY */
    private String helpCategory;

    /** 标题 */
    private String helpTitle;

    /** 文本内容 */
    private String helpContent;

    /** 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG */
    private Integer enableFlag;

    /** 排序号 */
    private Integer sort;

    /** 备注 */
    private String remark;

    /** 创建人 */
    private String createdBy;

    /** 创建时间 */
    private LocalDateTime createdTime;

    /** 修改人 */
    private String updatedBy;

    /** 修改时间 */
    private LocalDateTime updatedTime;

    /************************** 以下为非原表字段 ****************************/


}
