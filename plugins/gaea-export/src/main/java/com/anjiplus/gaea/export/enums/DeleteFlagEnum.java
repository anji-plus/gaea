package com.anjiplus.gaea.export.enums;
public enum DeleteFlagEnum {
    DELETED(1,"已删除"),
    UNDELETED(0,"未删除"),
    ;

    private int codeValue;
    private String codeDesc;

    private DeleteFlagEnum(int  codeValue, String codeDesc) {
        this.codeValue = codeValue;
        this.codeDesc = codeDesc;
    }

    public int   getCodeValue(){ return this.codeValue;}

    public String getCodeDesc(){ return this.codeDesc;}

}