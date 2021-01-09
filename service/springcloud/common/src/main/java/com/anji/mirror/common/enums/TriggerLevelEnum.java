package com.anji.mirror.common.enums;
public enum TriggerLevelEnum {
    LOW("low","低级"),
    MEDIUM("medium","中级"),
    HIGH("high","高级"),
    ;

    private String codeValue;
    private String codeDesc;

    private TriggerLevelEnum(String  codeValue, String codeDesc) {
        this.codeValue = codeValue;
        this.codeDesc = codeDesc;
    }

    public String   getCodeValue(){ return this.codeValue;}

    public String getCodeDesc(){ return this.codeDesc;}

    //根据codeValue获取枚举
    public static TriggerLevelEnum parseFromCodeValue(String codeValue){
        for (TriggerLevelEnum e : TriggerLevelEnum.values()){
            if(e.codeValue.equals(codeValue)){ return e;}
        }
        return null;
    }

    //根据codeValue获取描述
    public static String getCodeDescByCodeBalue(String codeValue){
        TriggerLevelEnum enumItem = parseFromCodeValue(codeValue);
        return enumItem == null ? "" : enumItem.getCodeDesc();
    }

    //验证codeValue是否有效
    public static boolean validateCodeValue(String codeValue){ return parseFromCodeValue(codeValue)!=null;}

    //列出所有值字符串
    public static String getString(){
        StringBuffer buffer = new StringBuffer();
        for (TriggerLevelEnum e : TriggerLevelEnum.values()){
            buffer.append(e.codeValue).append("--").append(e.getCodeDesc()).append(", ");
        }
        buffer.deleteCharAt(buffer.lastIndexOf(","));
        return buffer.toString().trim();
    }


}