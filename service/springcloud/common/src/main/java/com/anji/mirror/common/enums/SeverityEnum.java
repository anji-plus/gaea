package com.anji.mirror.common.enums;
public enum SeverityEnum {
    ERRORALARM(1,"Error告警"),
    WARNALARM(2,"Warn提醒"),
    ;

    private int codeValue;
    private String codeDesc;

    private SeverityEnum(int  codeValue, String codeDesc) {
        this.codeValue = codeValue;
        this.codeDesc = codeDesc;
    }

    public int   getCodeValue(){ return this.codeValue;}

    public String getCodeDesc(){ return this.codeDesc;}

    //根据codeValue获取枚举
    public static SeverityEnum parseFromCodeValue(int codeValue){
        for (SeverityEnum e : SeverityEnum.values()){
            if(e.codeValue == codeValue){ return e;}
        }
        return null;
    }

    //根据codeValue获取描述
    public static String getCodeDescByCodeBalue(int codeValue){
        SeverityEnum enumItem = parseFromCodeValue(codeValue);
        return enumItem == null ? "" : enumItem.getCodeDesc();
    }

    //验证codeValue是否有效
    public static boolean validateCodeValue(int codeValue){ return parseFromCodeValue(codeValue)!=null;}

    //列出所有值字符串
    public static String getString(){
        StringBuffer buffer = new StringBuffer();
        for (SeverityEnum e : SeverityEnum.values()){
            buffer.append(e.codeValue).append("--").append(e.getCodeDesc()).append(", ");
        }
        buffer.deleteCharAt(buffer.lastIndexOf(","));
        return buffer.toString().trim();
    }


}