package com.anji.mirror.common.enums;
public enum ManufacturerEnum {
    REDHAT("Redhat","Redhat"),
    H3C("H3C","华三通信"),
    HUAWEI("HUAWEI","华为"),
    APC("APC","APC"),
    HP("HP","HP"),
    MICROSOFT("Microsoft","Microsoft"),
    QNAP("QNAP","QNAP"),
    SR("SR","SR"),
    VMWARE("VMware","VMware"),
    QIZHI("qizhi","奇治"),
    SANGFOR("sangfor","深信服"),
    SOFTNEXT("softnext","守内安"),
    BARRACUDA("Barracuda","梭子鱼"),
    SECWORLD("secworld","网神"),
    ;

    private String codeValue;
    private String codeDesc;

    private ManufacturerEnum(String  codeValue, String codeDesc) {
        this.codeValue = codeValue;
        this.codeDesc = codeDesc;
    }

    public String   getCodeValue(){ return this.codeValue;}

    public String getCodeDesc(){ return this.codeDesc;}

    //根据codeValue获取枚举
    public static ManufacturerEnum parseFromCodeValue(String codeValue){
        for (ManufacturerEnum e : ManufacturerEnum.values()){
            if(e.codeValue.equals(codeValue)){ return e;}
        }
        return null;
    }

    //根据codeValue获取描述
    public static String getCodeDescByCodeBalue(String codeValue){
        ManufacturerEnum enumItem = parseFromCodeValue(codeValue);
        return enumItem == null ? "" : enumItem.getCodeDesc();
    }

    //验证codeValue是否有效
    public static boolean validateCodeValue(String codeValue){ return parseFromCodeValue(codeValue)!=null;}

    //列出所有值字符串
    public static String getString(){
        StringBuffer buffer = new StringBuffer();
        for (ManufacturerEnum e : ManufacturerEnum.values()){
            buffer.append(e.codeValue).append("--").append(e.getCodeDesc()).append(", ");
        }
        buffer.deleteCharAt(buffer.lastIndexOf(","));
        return buffer.toString().trim();
    }


}