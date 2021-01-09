package com.anji.mirror.auth.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 数据字典表
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DictVO implements Serializable {

    private static final long serialVersionUID=1L;

    /** PK */
      private Long dictId;

    /** 字典名称 */
    private String dictName;

    /** 字典描述 */
    private String dictDesc;

    /** 代码英文 */
    private String itemName;

    /** 代码值，对应select option 值 */
    private String itemValue;

    /** 代码描述，对应select option 中文描述 */
    private String itemDesc;

    /** 字典扩展配置，select change时回传给前端 */
    private String itemExtend;

    /** 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG */
    private Integer enableFlag;

    /**  0--未删除 1--已删除 DIC_NAME=DEL_FLAG */
    private Integer deleteFlag;

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
