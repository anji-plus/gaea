package com.anjiplus.template.business.modules.file.controller.dto;

import com.anji.plus.gaea.curd.dto.GaeaBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * (GaeaFile)实体类
 *
 * @author peiyanni
 * @since 2021-02-18 14:48:27
 */
@ApiModel(value = "")
public class GaeaFileDTO extends GaeaBaseDTO {

    /**
     * 文件uuid
     */
    private String fileId;
    /**
     * 文件在linux中的完整目录，比如/app/dist/export/excel/${fileid}.xlsx
     */
    @ApiModelProperty(value = "文件在linux中的完整目录，比如/app/dist/export/excel/${fileid}.xlsx")
    private String filePath;
    /**
     * 通过接口的下载完整http路径
     */
    @ApiModelProperty(value = "通过接口的下载完整http路径")
    private String urlPath;
    /**
     * 文件内容说明，比如 对账单(202001~202012)
     */
    @ApiModelProperty(value = "文件内容说明，比如 对账单(202001~202012)")
    private String fileInstruction;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getFileInstruction() {
        return fileInstruction;
    }

    public void setFileInstruction(String fileInstruction) {
        this.fileInstruction = fileInstruction;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
