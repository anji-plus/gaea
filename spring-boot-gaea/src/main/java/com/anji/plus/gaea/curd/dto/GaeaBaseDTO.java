package com.anji.plus.gaea.curd.dto;



import com.anji.plus.gaea.annotation.Formatter;
import com.anji.plus.gaea.cache.FormatterKey;
import com.anji.plus.gaea.utils.GaeaUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 基础传输对象
 * @author lr
 * @since 2021-01-12
 */
public class GaeaBaseDTO {
    private Long id;

    /**
     * 创建人
     */
    @Formatter(key = FormatterKey.USER_NICKNAME_KEY, targetField = "createBy")
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改人
     */
    @Formatter(key = FormatterKey.USER_NICKNAME_KEY, targetField = "updateBy")
    private String updateBy;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * accessKey用于校验单条记录权限，防止越权
     * @return
     */
    public String getAccessKey() {
        if (id == null) {
            return null;
        }
        return GaeaUtils.getPassKey(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
