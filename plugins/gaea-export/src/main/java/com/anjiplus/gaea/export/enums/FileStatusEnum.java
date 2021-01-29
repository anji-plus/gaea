package com.anjiplus.gaea.export.enums;
public enum FileStatusEnum {
    CREATING("creating","生成中"),
    SUCCESS("success","生成成功"),
    FAILED("failed","生成失败"),
    ;

    private String codeValue;
    private String codeDesc;

    private FileStatusEnum(String  codeValue, String codeDesc) {
        this.codeValue = codeValue;
        this.codeDesc = codeDesc;
    }

    public String   getCodeValue(){ return this.codeValue;}

    public String getCodeDesc(){ return this.codeDesc;}

    //根据codeValue获取枚举
    public static FileStatusEnum parseFromCodeValue(String codeValue){
        for (FileStatusEnum e : FileStatusEnum.values()){
            if(e.codeValue.equals(codeValue)){ return e;}
        }
        return null;
    }

    //根据codeValue获取描述
    public static String getCodeDescByCodeBalue(String codeValue){
        FileStatusEnum enumItem = parseFromCodeValue(codeValue);
        return enumItem == null ? "" : enumItem.getCodeDesc();
    }

    //验证codeValue是否有效
    public static boolean validateCodeValue(String codeValue){ return parseFromCodeValue(codeValue)!=null;}

    //列出所有值字符串
    public static String getString(){
        StringBuffer buffer = new StringBuffer();
        for (FileStatusEnum e : FileStatusEnum.values()){
            buffer.append(e.codeValue).append("--").append(e.getCodeDesc()).append(", ");
        }
        buffer.deleteCharAt(buffer.lastIndexOf(","));
        return buffer.toString().trim();
    }


}