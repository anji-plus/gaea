package com.anjiplus.gaea.export.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 功能描述：
 *
 * @Author: peiyanni
 * @Date: 2021/1/28 12:57
 */
public class ExportBaseInfo implements Serializable {
    private static final long serialVersionUID=1L;

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


    public Long getExportId() {
        return exportId;
    }

    public void setExportId(Long exportId) {
        this.exportId = exportId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }

    public LocalDateTime getResultStartTime() {
        return resultStartTime;
    }

    public void setResultStartTime(LocalDateTime resultStartTime) {
        this.resultStartTime = resultStartTime;
    }

    public LocalDateTime getResultEndTime() {
        return resultEndTime;
    }

    public void setResultEndTime(LocalDateTime resultEndTime) {
        this.resultEndTime = resultEndTime;
    }

    public Long getResultSize() {
        return resultSize;
    }

    public void setResultSize(Long resultSize) {
        this.resultSize = resultSize;
    }

    public LocalDateTime getFileCreateTime() {
        return fileCreateTime;
    }

    public void setFileCreateTime(LocalDateTime fileCreateTime) {
        this.fileCreateTime = fileCreateTime;
    }

    public LocalDateTime getFileFinishTime() {
        return fileFinishTime;
    }

    public void setFileFinishTime(LocalDateTime fileFinishTime) {
        this.fileFinishTime = fileFinishTime;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public Long getCreaterUserid() {
        return createrUserid;
    }

    public void setCreaterUserid(Long createrUserid) {
        this.createrUserid = createrUserid;
    }

    public String getCreaterUsername() {
        return createrUsername;
    }

    public void setCreaterUsername(String createrUsername) {
        this.createrUsername = createrUsername;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
