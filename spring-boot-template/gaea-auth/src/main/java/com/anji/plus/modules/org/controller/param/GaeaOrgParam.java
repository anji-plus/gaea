package com.anji.plus.modules.org.controller.param;


import com.anji.plus.gaea.curd.params.PageParam;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 组织(GaeaOrg)param
 *
 * @author lr
 * @since 2021-02-02 13:37:33
 */
@Setter
@Getter
public class GaeaOrgParam extends PageParam implements Serializable {
    /**
     * 机构代码
     */
    private String orgCode;
    /**
     * 机构名称
     */
    private String orgName;
    /**
     * 上级组织code
     */
    private String orgParentCode;

    /**
     * 外部机构代码（从外系统同步过来得编码）
     */
    private String outOrgCode;
    /**
     * 外部机构父级编码（从外系统同步过来得父级编码）
     */
    private String outOrgParentCode;

    /**
     * 组织类型
     */
    private String orgType;
    /**
     * 联系人
     */
    private String linkman;

    /**
     * 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG
     */
    private Integer enabled;
}
