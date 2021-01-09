package com.anji.mirror.auth.enums;

import com.anji.mirror.common.model.ResponseModel;

import java.text.MessageFormat;

/**
 * 该服务特有的返回应答码
 * @author anji gaea teams
 * @since 2020-08-20
 */
public enum RepCodeEnum {

    /**=========================== 公共错误码 0001 - 0999 不得修改===================================*/
    /** 0001 - 0099 网关应答码 */
    SUCCESS("0000", "成功"),
    FAIL("0001", "操作失败"),

    ERROR("9999", "服务器内部异常"),

    BLANK_ERROR("0011", "{0}不能为空"),
    NULL_ERROR("0011", "{0}不能为空"),
    NOT_NULL_ERROR("0012", "{0}必须为空"),
    NOT_EXIST_ERROR("0013", "{0}数据库中不存在"),
    EXIST_ERROR("0014", "{0}数据库中已存在"),
    PARAM_TYPE_ERROR("0015", "{0}类型错误"),
    PARAM_FORMAT_ERROR("0016", "{0}格式错误"),

    /**=========================== auth-service服务错误码 1001 - 1999 ===================================*/
    /** 1001 - 1999 xx业务应答码 */
    TITLE_EXISTS_ERROR("1001", "{0}已经存在"),
    ENCRYPTION_ERROR("1002", "解密脱敏失败"),

    /** 1101 - 1199 yy业务应答码 */

    /** 1201 - 1299 zz业务应答码 */

    ;
    private String code;
    private String desc;

    RepCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public String getCode() {
        return code;
    }
    public String getDesc() {
        return desc;
    }
    public String getName(){
        return this.name();
    }

    /** 将入参fieldNames与this.desc组合成错误信息
     *  {fieldName}不能为空
     * @param fieldNames
     * @return
     */
    public ResponseModel parseError(Object... fieldNames) {
        ResponseModel errorMessage=new ResponseModel();
        String newDesc = MessageFormat.format(this.desc, fieldNames);

        errorMessage.setRepCode(this.code);
        errorMessage.setRepMsg(newDesc);
        return errorMessage;
    }

}
