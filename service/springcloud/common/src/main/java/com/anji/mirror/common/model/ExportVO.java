package com.anji.mirror.common.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 导出中心
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-11-23
 */

public class ExportVO implements Serializable {

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

    /************************** 以下为非原表字段 ****************************/
    /** 自定义参数，导出模板渲染时，可以使用 */
    private Map<String, Object> parameters;
    /** 导出的list */
    private List list;
    /** 导出模式 template--模板模式 simplate--简单模式 */
    private String exportModel;
    /** 文件格式，excel pdf */
    private String fileType;
    /** 模板模式时，模板id */
    private String reportTemplateId;
    /** 文件格式，完整路径: /app/dist/export/excel/a3e847fhtu2341234asdfasd.xlsx*/
    private String filePath;

    /** 导出类型*/
    private String exportType;

    /**
     * 文件内容中标题
     */
    private String title;

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

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public String getExportModel() {
        return exportModel;
    }

    public void setExportModel(String exportModel) {
        this.exportModel = exportModel;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getReportTemplateId() {
        return reportTemplateId;
    }

    public void setReportTemplateId(String reportTemplateId) {
        this.reportTemplateId = reportTemplateId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
