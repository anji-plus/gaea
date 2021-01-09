package com.anji.mirror.common.enums;
public enum AuditFlagEnum {
    WAITING("waiting","待审核"),
    ONGOING("ongoing","审核中"),
    APPROVED("approved","通过"),
    REJECTED("rejected","拒绝"),
    ;

    private String codeValue;
    private String codeDesc;

    private AuditFlagEnum(String  codeValue, String codeDesc) {
        this.codeValue = codeValue;
        this.codeDesc = codeDesc;
    }

    public String   getCodeValue(){ return this.codeValue;}

    public String getCodeDesc(){ return this.codeDesc;}

    //根据codeValue获取枚举
    public static AuditFlagEnum parseFromCodeValue(String codeValue){
        for (AuditFlagEnum e : AuditFlagEnum.values()){
            if(e.codeValue.equals(codeValue)){ return e;}
        }
        return null;
    }

    //根据codeValue获取描述
    public static String getCodeDescByCodeBalue(String codeValue){
        AuditFlagEnum enumItem = parseFromCodeValue(codeValue);
        return enumItem == null ? "" : enumItem.getCodeDesc();
    }

    //验证codeValue是否有效
    public static boolean validateCodeValue(String codeValue){ return parseFromCodeValue(codeValue)!=null;}

    //列出所有值字符串
    public static String getString(){
        StringBuffer buffer = new StringBuffer();
        for (AuditFlagEnum e : AuditFlagEnum.values()){
            buffer.append(e.codeValue).append("--").append(e.getCodeDesc()).append(", ");
        }
        buffer.deleteCharAt(buffer.lastIndexOf(","));
        return buffer.toString().trim();
    }


}