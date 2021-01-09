package com.anji.mirror.auth.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 导出中心
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_export")
public class ExportPO implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "export_id", type = IdType.AUTO)
    private Long exportId;

    /** 文件在t_file中的id，前端传它来读流接口显示，http://auth/file/download/fileId */
    private String fileId;

    /** 文件标题，比如:对账单报表6月份报表  */
    private String fileTitle;

    /** 导出前，查询的数据开始时间 */
    private LocalDateTime resultStartTime;

    /** 导出前，查询的数据结束时间 */
    private LocalDateTime resultEndTime;

    /** 导出查询结果，数据总条数 */
    private Long resultSize;

    /** 文件导出触发时间 */
    private LocalDateTime fileCreateTime;

    /** 文件生成完成时间 */
    private LocalDateTime fileFinishTime;

    /** 文件状态，creating生成中，success生成成功,failed生成失败 */
    private String fileStatus;

    /** 创建者userId */
    private Long createrUserid;

    /** 创建者loginName */
    private String createrUsername;

    /** 备注 */
    private String remark;

}
