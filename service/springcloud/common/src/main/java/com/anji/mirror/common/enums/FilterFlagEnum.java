package com.anji.mirror.common.enums;
public enum FilterFlagEnum {
    DISABLE(0,"已禁用"),
    ENABLE(1,"已启用"),
    ;

    private int codeValue;
    private String codeDesc;

    private FilterFlagEnum(int  codeValue, String codeDesc) {
        this.codeValue = codeValue;
        this.codeDesc = codeDesc;
    }

    public int   getCodeValue(){ return this.codeValue;}

    public String getCodeDesc(){ return this.codeDesc;}

    //根据codeValue获取枚举
    public static FilterFlagEnum parseFromCodeValue(int codeValue){
        for (FilterFlagEnum e : FilterFlagEnum.values()){
            if(e.codeValue == codeValue){ return e;}
        }
        return null;
    }

    //根据codeValue获取描述
    public static String getCodeDescByCodeBalue(int codeValue){
        FilterFlagEnum enumItem = parseFromCodeValue(codeValue);
        return enumItem == null ? "" : enumItem.getCodeDesc();
    }

    //验证codeValue是否有效
    public static boolean validateCodeValue(int codeValue){ return parseFromCodeValue(codeValue)!=null;}

    //列出所有值字符串
    public static String getString(){
        StringBuffer buffer = new StringBuffer();
        for (FilterFlagEnum e : FilterFlagEnum.values()){
            buffer.append(e.codeValue).append("--").append(e.getCodeDesc()).append(", ");
        }
        buffer.deleteCharAt(buffer.lastIndexOf(","));
        return buffer.toString().trim();
    }


}