package com.github.anji.plus.module.demo.entity;

import java.util.Date;
import com.github.anji.plus.gaea.curd.entity.GaeaBaseEntity;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 运营权限表(TAction)实体类
 *
 * @author lirui
 * @since 2021-01-29 18:45:49
 */
@TableName("TAction")
public class TAction extends GaeaBaseEntity implements Serializable {
        /**
    * 主键
    */    
    private Long actionId;
        /**
    * 权限动作
    */    
    private String actionCode;
        /**
    * 权限说明
    */    
    private String actionName;
        /**
    * 排序，升序
    */    
    private Integer sort;
        /**
    * 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG
    */    
    private Integer enableFlag;
        /**
    *  0--未删除 1--已删除 DIC_NAME=DEL_FLAG
    */    
    private Integer deleteFlag;
        /**
    * 创建人
    */    
    private String createdBy;
        /**
    * 创建时间
    */    
    private Date createdTime;
        /**
    * 修改人
    */    
    private String updatedBy;
        /**
    * 修改时间
    */    
    private Date updatedTime;
                
    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }
                    
    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }
                    
    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
                    
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
                    
    public Integer getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(Integer enableFlag) {
        this.enableFlag = enableFlag;
    }
                    
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
                    
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
                    
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
                    
    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
                    
    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
    

}