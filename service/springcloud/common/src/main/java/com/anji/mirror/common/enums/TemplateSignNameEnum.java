package com.anji.mirror.common.enums;
public enum TemplateSignNameEnum {
    SQSIGN(1,"上汽安吉"),
    ALISIGN(2,"阿里"),
    JIGSIGN(3,"极光"),
    ;

    private int codeValue;
    private String codeDesc;

    private TemplateSignNameEnum(int  codeValue, String codeDesc) {
        this.codeValue = codeValue;
        this.codeDesc = codeDesc;
    }

    public int   getCodeValue(){ return this.codeValue;}

    public String getCodeDesc(){ return this.codeDesc;}

    //根据codeValue获取枚举
    public static TemplateSignNameEnum parseFromCodeValue(int codeValue){
        for (TemplateSignNameEnum e : TemplateSignNameEnum.values()){
            if(e.codeValue == codeValue){ return e;}
        }
        return null;
    }

    //根据codeValue获取描述
    public static String getCodeDescByCodeBalue(int codeValue){
        TemplateSignNameEnum enumItem = parseFromCodeValue(codeValue);
        return enumItem == null ? "" : enumItem.getCodeDesc();
    }

    //验证codeValue是否有效
    public static boolean validateCodeValue(int codeValue){ return parseFromCodeValue(codeValue)!=null;}

    //列出所有值字符串
    public static String getString(){
        StringBuffer buffer = new StringBuffer();
        for (TemplateSignNameEnum e : TemplateSignNameEnum.values()){
            buffer.append(e.codeValue).append("--").append(e.getCodeDesc()).append(", ");
        }
        buffer.deleteCharAt(buffer.lastIndexOf(","));
        return buffer.toString().trim();
    }


}