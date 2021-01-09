package com.anji.mirror.common.enums;
public enum ExportTypeEnum {
    SIMPLE_EXCEL("excel","easy_excel"),
    SIMPLE_PDF("pdf","itext"),
    JASPER_TEMPLATE_EXCEL("jasper_template_excel","jasper_template_excel"),
    JASPER_TEMPLATE_PDF("jasper_template_pdf","jasper_template_pdf"),
    ;

    private String codeValue;
    private String codeDesc;

    private ExportTypeEnum(String  codeValue, String codeDesc) {
        this.codeValue = codeValue;
        this.codeDesc = codeDesc;
    }

    public String   getCodeValue(){ return this.codeValue;}

    public String getCodeDesc(){ return this.codeDesc;}

    //根据codeValue获取枚举
    public static ExportTypeEnum parseFromCodeValue(String codeValue){
        for (ExportTypeEnum e : ExportTypeEnum.values()){
            if(e.codeValue.equals(codeValue)){ return e;}
        }
        return null;
    }

    //根据codeValue获取描述
    public static String getCodeDescByCodeBalue(String codeValue){
        ExportTypeEnum enumItem = parseFromCodeValue(codeValue);
        return enumItem == null ? "" : enumItem.getCodeDesc();
    }

    //验证codeValue是否有效
    public static boolean validateCodeValue(String codeValue){ return parseFromCodeValue(codeValue)!=null;}

    //列出所有值字符串
    public static String getString(){
        StringBuffer buffer = new StringBuffer();
        for (ExportTypeEnum e : ExportTypeEnum.values()){
            buffer.append(e.codeValue).append("--").append(e.getCodeDesc()).append(", ");
        }
        buffer.deleteCharAt(buffer.lastIndexOf(","));
        return buffer.toString().trim();
    }


}