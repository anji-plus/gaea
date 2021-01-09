package com.anji.mirror.common.enums;

import com.anji.mirror.common.model.ResponseModel;

import java.text.MessageFormat;

/**
 * 公共的返回应答码
 * druid连接池配置
 * @author anji gaea teams
 * @since 2020-08-20
 */
public enum RepCodeEnum {

    /** 0001 - 0099 网关应答码 */
    SUCCESS("0000", "成功"),
    FAIL("0001", "操作失败"),

    ERROR("9999", "服务器内部异常"),

    BLANK_ERROR("0011", "{0}不能为空"),
    NULL_ERROR("0011", "{0}不能为空"),
    NOT_NULL_ERROR("0012", "{0}必须为空"),
    NOT_EXIST_ERROR("0013", "{0}不存在"),
    EXIST_ERROR("0014", "{0}已存在"),
    PARAM_TYPE_ERROR("0015", "{0}类型错误"),
    PARAM_FORMAT_ERROR("0016", "{0}格式错误"),

    GATEWAY_DATA_NULL_ERROR("0020","data参数不能为空"),
    GATEWAY_PARM_ERROR("0021", "参数校验失败"),
    GATEWAY_TIMESTAMP_ERROR("0022", "时间戳超过服务器允许误差"),
    GATEWAY_SIGN_ERROR("0023", "签名校验失败"),
    GATEWAY_TOKEN_ERROR("0024", "登录超时或已被注销, 请重新登录"),
    GATEWAY_AUTH_ERROR("0025", "权限校验失败"),
    GATEWAY_COMMON_ERROR("0026", "服务处理异常"),
    GATEWAY_REJECT_IP_ERROR("0027", "拒绝该ip访问"),
    GATEWAY_TIMESTAMP_NULL_ERROR("0028", "时间戳不能为空"),
    GATEWAY_SIGN_NULL_ERROR("0029", "签名不能为空"),
    GATEWAY_TOKEN_NULL_ERROR("0030", "令牌token不能为空"),
    GATEWAY_TIME_FORMAT_ERROR("0031", "time必须是long类型，开始请求的毫秒数"),

    /**=========================== 1xxxx base服务开始 ===================================*/
    /** 1001 - 1099 权限业务应答码 */

    /** 1101 - 1199 角色业务应答码 */

    /** 1201 - 1299 用户业务应答码 */
    USER_LOGIN_ERROR("1201", "登录失败"),
    USER_LOGOUT_ERROR("1202", "退出失败"),
    USER_TIMEOUT_ERROR("1203", "登陆超时请重新登录"),

    USER_NOTEXIST_ERROR("1204", "用户不存在"),
    USER_PARM_ISBLANK("1205", "参数为空"),
    USER_EXCEPTION_ERROR("1206", "未知异常"),
    USER_PASSWORD_ERROR("1207", "密码不正确"),
    USER_PASSWORD_DECRYPTION_FAILED_ERROR("1208","密码解密失败"),
    USER_PASSWORD_NOT_ENCRYPTED_ERROR("1209","密码未加密"),
    USER_PASSWORD_LENGTH_ERROR("1210","密码长度不能小于6大于16个字符"),
    USER_NOTLOGIN_ERROR("1211", "用户未登录"),
    USER_EXIST_ERROR("1212", "用户名已存在"),
    USER_NAME_NULL_ERROR("1213", "用户名不能为空"),
    USER_ENABLE_ERROR("1214", "用户已被禁用"),
    USER_PASSWORD_NULL_ERROR("1215","密码不能为空"),
    USER_FORBID_REGISTER("1216", "禁止注册用户"),
    USER_MAIL_EXIST_ERROR("1217", "邮箱已经存在"),
    USER_PHONE_EXIST_ERROR("1218", "手机号码已经存在"),
    USER_NICKNAME_NULL_ERROR("1219","姓名不能为空"),
    USER_ID_NULL_ERROR("1220","用户ID不能为空"),
    USER_ID_NOT_EMPTY_ERROR("1221","创建用户时不能传用户ID"),
    USER_GET_VERIFICATIONCODE_ERROR("1222","生成验证码失败"),
    USER_PHONE_NOT_FOUNT_ACCOUNT_ERROR("1223","该手机号未找到关联账户"),
    USER_FREQUENT_OPERATION_ERROR("1224","操作太快，请稍后重试"),
    USER_VERIFICATION_CODE_INVALIDATION_ERROR("1225","验证码已失效,请重新获取"),
    USER_INCORRECT_VERIFICATION_CODE_ERROR("1226","验证码输入错误"),
    USER_VERIFICATIONCODE_TYPE_ERROR("1227","短信验证码类型错误"),
    USER_EMAIL_NOT_FOUNT_ACCOUNT_ERROR("1228","该邮箱未找到关联账户"),
    USER_USERNAME_OR_PASSWORD_ERROR("1229","用户名或密码不正确"),
    USER_INCONSISTENT_PASSWORD_ERROR("1230","密码和确认密码不一致"),
    USER_PASSWORD_CHANGE_ERROR("1231","修改密码错误"),
    USER_IMAGE_VERIFICATIONCODE_EXPIRED("1232","图片验证码已过期，请重新获取"),
    USER_IMAGE_VERIFICATIONCODE_ERROR("1232","图片验证码输入不正确"),
    USER_SMS_VERIFICATIONCODE_SEND_SUCCESS("1233", "短信验证码已发出，请注意查收"),
    USER_MAIL_VERIFICATIONCODE_SEND_SUCCESS("1234", "邮件验证码已发出，请注意查收"),
    USER_PASSWORD_CONFIG_PASSWORD_CANOT_EQUAL("1235", "新密码不能和原密码一致"),
    USER_LOGIN_ATTEMPTS_EXCEEDS_THE_UPPER_LIMIT("1236", "尝试登陆次数超过上限，请三分钟后再次登录"),
    USER_OLD_PASSWORD_ERROR("1237", "旧密码不正确"),


    /** 1300 - 1399 字典相关*/
    BASECODE_ERROR("1300","数据字典值不对"),


    /** 2000 - 3000 analysis 业务相关 */
    ANALYSIS_DELETE_FAIL_WITH_DEVICE_INFO("2000", "删除失败，请先删除设备信息。"),
    ANALYSIS_DELETE_FAIL_WITH_INDEX_EXTRACTION_RULE("2001", "删除失败，请先删除指标提取规则。"),
    ANALYSIS_DELETE_FAIL_WITH_INDEX_EXTRACTION_RESULT_CALCULATION("2002", "删除失败，请先删除指标提取结果计算。"),
    ANALYSIS_BEGIN_TIME_MORE_THAN_THEEND_TIME("20003", "开始时间大于结束时间"),


    /** 3000 - 4000 syslog 业务相关 */
    SYSLOG_QUERY_KEYWORD_IS_TOO_SIMPLE("3001","查询关键字至少包含字母、数字、中文中的一种字符"),

    /**=========================== 6xxxx api服务开始 ===================================*/
    API_SEND_SMS_ERROR("6101", "短信发送失败"),
    API_TEMPLATE_RESOLUTION_JSON_ERROR("6102","模板解析json失败"),

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
