package com.anji.mirror.common.enums;
public enum AlertIntervalEnum {
    MINUTE2(2,"2分钟"),
    MINUTE4(4,"4分钟"),
    MINUTE6(6,"6分钟"),
    MINUTE8(8,"8分钟"),
    MINUTE15(15,"15分钟"),
    MINUTE30(30,"30分钟"),
    MINUTE60(60,"60分钟"),
    HOUR4(240,"4小时"),
    HOUR8(480,"8小时"),
    ;

    private int codeValue;
    private String codeDesc;

    private AlertIntervalEnum(int  codeValue, String codeDesc) {
        this.codeValue = codeValue;
        this.codeDesc = codeDesc;
    }

    public int   getCodeValue(){ return this.codeValue;}

    public String getCodeDesc(){ return this.codeDesc;}

    //根据codeValue获取枚举
    public static AlertIntervalEnum parseFromCodeValue(int codeValue){
        for (AlertIntervalEnum e : AlertIntervalEnum.values()){
            if(e.codeValue == codeValue){ return e;}
        }
        return null;
    }

    //根据codeValue获取描述
    public static String getCodeDescByCodeBalue(int codeValue){
        AlertIntervalEnum enumItem = parseFromCodeValue(codeValue);
        return enumItem == null ? "" : enumItem.getCodeDesc();
    }

    //验证codeValue是否有效
    public static boolean validateCodeValue(int codeValue){ return parseFromCodeValue(codeValue)!=null;}

    //列出所有值字符串
    public static String getString(){
        StringBuffer buffer = new StringBuffer();
        for (AlertIntervalEnum e : AlertIntervalEnum.values()){
            buffer.append(e.codeValue).append("--").append(e.getCodeDesc()).append(", ");
        }
        buffer.deleteCharAt(buffer.lastIndexOf(","));
        return buffer.toString().trim();
    }


}