package com.anji.mirror.common.enums;
public enum HelpCategoryEnum {
    LOGIN_REGISTER("login_register","登录注册"),
    AUTH_ROLE("auth_role","权限角色"),
    DICT_MANAGER("dict_manager","字典管理"),
    SYSTEM_SETTING("system_setting","系统设置"),
    MESSAGE_PUSH("message_push","消息推送"),
    DEVICE_MANAGER("device_manager","设备管理"),
    ITEM_CALCULATE("item_calculate","监控计算"),
    TRIGGER_ALERT("trigger_alert","触发告警"),
    ;

    private String codeValue;
    private String codeDesc;

    private HelpCategoryEnum(String  codeValue, String codeDesc) {
        this.codeValue = codeValue;
        this.codeDesc = codeDesc;
    }

    public String   getCodeValue(){ return this.codeValue;}

    public String getCodeDesc(){ return this.codeDesc;}

    //根据codeValue获取枚举
    public static HelpCategoryEnum parseFromCodeValue(String codeValue){
        for (HelpCategoryEnum e : HelpCategoryEnum.values()){
            if(e.codeValue.equals(codeValue)){ return e;}
        }
        return null;
    }

    //根据codeValue获取描述
    public static String getCodeDescByCodeBalue(String codeValue){
        HelpCategoryEnum enumItem = parseFromCodeValue(codeValue);
        return enumItem == null ? "" : enumItem.getCodeDesc();
    }

    //验证codeValue是否有效
    public static boolean validateCodeValue(String codeValue){ return parseFromCodeValue(codeValue)!=null;}

    //列出所有值字符串
    public static String getString(){
        StringBuffer buffer = new StringBuffer();
        for (HelpCategoryEnum e : HelpCategoryEnum.values()){
            buffer.append(e.codeValue).append("--").append(e.getCodeDesc()).append(", ");
        }
        buffer.deleteCharAt(buffer.lastIndexOf(","));
        return buffer.toString().trim();
    }


}