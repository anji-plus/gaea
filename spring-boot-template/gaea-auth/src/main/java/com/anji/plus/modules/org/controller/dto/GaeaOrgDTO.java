package com.anji.plus.modules.org.controller.dto;

import com.anji.plus.gaea.curd.dto.GaeaBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 组织(GaeaOrg)实体类
 *
 * @author lr
 * @since 2021-02-02 13:37:33
 */
@ApiModel(value = "组织")
public class GaeaOrgDTO extends GaeaBaseDTO {
        /**
    * 机构代码
    */
    @ApiModelProperty(value = "机构代码")
    private String orgCode;
        /**
    * 机构名称
    */
    @ApiModelProperty(value = "机构名称")
    private String orgName;
        /**
    * 上级组织code
    */
    @ApiModelProperty(value = "上级组织code")
    private String orgParentCode;
        /**
    * 上级部门名字
    */
    @ApiModelProperty(value = "上级部门名字")
    private String orgParentName;
        /**
    * 外部机构代码（从外系统同步过来得编码）
    */
    @ApiModelProperty(value = "外部机构代码（从外系统同步过来得编码）")
    private String outOrgCode;
        /**
    * 外部机构父级编码（从外系统同步过来得父级编码）
    */
    @ApiModelProperty(value = "外部机构父级编码（从外系统同步过来得父级编码）")
    private String outOrgParentCode;
        /**
    * 机构级别
    */
    @ApiModelProperty(value = "机构级别")
    private String orgLevel;
        /**
    * 组织类型
    */
    @ApiModelProperty(value = "组织类型")
    private String orgType;
        /**
    * 联系人
    */
    @ApiModelProperty(value = "联系人")
    private String linkman;
        /**
    * 手机号
    */
    @ApiModelProperty(value = "手机号")
    private String mobilePhone;
        /**
    * 固定电话
    */
    @ApiModelProperty(value = "固定电话")
    private String telephone;
        /**
    * 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG
    */
    @ApiModelProperty(value = "0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG")
    private Integer enabled;
        /**
    *  0--未删除 1--已删除 DIC_NAME=DEL_FLAG
    */
    @ApiModelProperty(value = " 0--未删除 1--已删除 DIC_NAME=DEL_FLAG")
    private Integer deleteFlag;
        /**
    * 描述信息
    */
    @ApiModelProperty(value = "描述信息")
    private String remark;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgParentCode() {
        return orgParentCode;
    }

    public void setOrgParentCode(String orgParentCode) {
        this.orgParentCode = orgParentCode;
    }

    public String getOrgParentName() {
        return orgParentName;
    }

    public void setOrgParentName(String orgParentName) {
        this.orgParentName = orgParentName;
    }

    public String getOutOrgCode() {
        return outOrgCode;
    }

    public void setOutOrgCode(String outOrgCode) {
        this.outOrgCode = outOrgCode;
    }

    public String getOutOrgParentCode() {
        return outOrgParentCode;
    }

    public void setOutOrgParentCode(String outOrgParentCode) {
        this.outOrgParentCode = outOrgParentCode;
    }

    public String getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
