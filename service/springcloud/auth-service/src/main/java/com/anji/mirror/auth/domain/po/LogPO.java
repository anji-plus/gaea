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
 * 用户操作日志表
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_log")
public class LogPO implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键 */
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;

    /** 用户ID */
    private Long userId;

    /** 用户名称 */
    private String userName;

    /** 请求路径 */
    private String requestUrl;

    /** 页面或按钮标题 */
    private String pageTitle;

    /** 请求参数 */
    private String requestParam;

    /** 响应参数 */
    private String responseParam;

    /** 来源IP */
    private String sourceIp;

    /** 访问时间 */
    private LocalDateTime requestTime;


}
