package com.anjiplus.gaea.export.enums;

public enum ExportTypeEnum {
    SIMPLE_EXCEL("excel", "easy_excel"),
    JASPER_TEMPLATE_EXCEL("jasper_template_excel", "jasper_template_excel"),
    JASPER_TEMPLATE_PDF("jasper_template_pdf", "jasper_template_pdf"),;

    private String codeValue;
    private String codeDesc;

    private ExportTypeEnum(String codeValue, String codeDesc) {
        this.codeValue = codeValue;
        this.codeDesc = codeDesc;
    }

    public String getCodeValue() {
        return this.codeValue;
    }

    public String getCodeDesc() {
        return this.codeDesc;
    }

}