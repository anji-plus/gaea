package com.anjiplus.gaea.export.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 导出中心
 * </p>
 *
 * @author gaea teams
 * @since 2021-01-27
 */

public class ExportOperation extends ExportBaseInfo {
    /************************** 以下为Export操作所需参数 ****************************/
    /** 自定义参数，导出模板渲染时，可以使用 */
    private Map<String, Object> parameters;
    /** 导出的list */
    private List list;
    /** 模板模式时，模板id */
    private String reportTemplateId;
    /** 文件格式，完整路径: /app/dist/export/excel/a3e847fhtu2341234asdfasd.xlsx*/
    private String filePath;
    /** 导出类型对应ExportTypeEnum枚举*/
    private String exportType;

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
}
