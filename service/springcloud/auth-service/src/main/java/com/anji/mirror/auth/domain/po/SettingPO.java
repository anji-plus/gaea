package com.anji.mirror.auth.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-10-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_setting")
public class SettingPO implements Serializable {

    private static final long serialVersionUID=1L;

    /** PK */
    @TableId(value = "setting_id", type = IdType.AUTO)
    private Long settingId;

    /** 参数名称 */
    private String settingName;

    /** 参数描述 */
    private String settingLabel;

    /** 参数值类型，input input-number json-array */
    private String settingType;

    /** 参数表单 */
    private String settingForm;

    /** 表单保存的值，int\String\Json */
    private String settingValue;

    /** 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG */
    private Integer enableFlag;

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


    @TableField(exist = false)
    private String itemDesc;

}
