package com.github.anji.plus.modules.export.controller.dto;

import com.github.anji.plus.gaea.curd.dto.GaeaBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 导出中心(GaeaExport)实体类
 *
 * @author makejava
 * @since 2021-02-07 17:12:25
 */
@ApiModel(value = "导出中心")
public class GaeaExportDTO extends GaeaBaseDTO {
    /**
     * 文件在t_file中的id，前端传它来读流接口显示，http://auth/file/download/fileId
     */
    @ApiModelProperty(value = "文件在t_file中的id，前端传它来读流接口显示，http://auth/file/download/fileId")
    private String fileId;
    /**
     * 文件标题，比如:对账单报表6月份报表
     */
    @ApiModelProperty(value = "文件标题，比如:对账单报表6月份报表")
    private String fileTitle;
    /**
     * 导出前，查询的数据开始时间
     */
    @ApiModelProperty(value = "导出前，查询的数据开始时间")
    private Date resultStartTime;
    /**
     * 导出前，查询的数据结束时间
     */
    @ApiModelProperty(value = "导出前，查询的数据结束时间")
    private Date resultEndTime;
    /**
     * 导出查询结果，数据总条数
     */
    @ApiModelProperty(value = "导出查询结果，数据总条数")
    private Long resultSize;
    /**
     * 文件导出触发时间
     */
    @ApiModelProperty(value = "文件导出触发时间")
    private Date fileCreateTime;
    /**
     * 文件生成完成时间
     */
    @ApiModelProperty(value = "文件生成完成时间")
    private Date fileFinishTime;
    /**
     * 文件状态，creating生成中，success生成成功,failed生成失败
     */
    @ApiModelProperty(value = "文件状态，creating生成中，success生成成功,failed生成失败")
    private String fileStatus;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

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

    public Date getResultStartTime() {
        return resultStartTime;
    }

    public void setResultStartTime(Date resultStartTime) {
        this.resultStartTime = resultStartTime;
    }

    public Date getResultEndTime() {
        return resultEndTime;
    }

    public void setResultEndTime(Date resultEndTime) {
        this.resultEndTime = resultEndTime;
    }

    public Long getResultSize() {
        return resultSize;
    }

    public void setResultSize(Long resultSize) {
        this.resultSize = resultSize;
    }

    public Date getFileCreateTime() {
        return fileCreateTime;
    }

    public void setFileCreateTime(Date fileCreateTime) {
        this.fileCreateTime = fileCreateTime;
    }

    public Date getFileFinishTime() {
        return fileFinishTime;
    }

    public void setFileFinishTime(Date fileFinishTime) {
        this.fileFinishTime = fileFinishTime;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}