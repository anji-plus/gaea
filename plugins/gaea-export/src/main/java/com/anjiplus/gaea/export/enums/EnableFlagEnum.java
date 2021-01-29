package com.anjiplus.gaea.export.enums;
public enum EnableFlagEnum {
    DISABLE(0,"已禁用"),
    ENABLE(1,"已启用"),
    ;

    private int codeValue;
    private String codeDesc;

    private EnableFlagEnum(int  codeValue, String codeDesc) {
        this.codeValue = codeValue;
        this.codeDesc = codeDesc;
    }

    public int   getCodeValue(){ return this.codeValue;}

    public String getCodeDesc(){ return this.codeDesc;}


}