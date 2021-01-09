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
 * 
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_file")
public class FilePO implements Serializable {

    private static final long serialVersionUID=1L;

    /** 文件性一id */
    private String fileId;

    /** 文件在linux中的完整目录，比如/app/dist/export/excel/${fileid}.xlsx */
    private String filePath;

    /** 通过接口的下载完整http路径 */
    private String urlPath;

    /** 文件内容说明，比如 对账单(202001~202012) */
    private String fileInstruction;

    /** 文件创建时间 */
    private LocalDateTime fileCreateTime;

    /** 创建者userId */
    private Long createrUserid;

    /** 创建者loginName */
    private String createrUsername;


}
