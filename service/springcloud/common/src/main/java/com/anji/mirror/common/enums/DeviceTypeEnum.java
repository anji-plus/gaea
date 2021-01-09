package com.anji.mirror.common.enums;
public enum DeviceTypeEnum {
    SWITCH("switch","交换机"),
    ROUTER("router","路由器"),
    FIREWALL("firewall","防火墙"),
    WIFI("wifi","无线"),
    PRINTER("printer","打印机"),
    SERVER("server","服务器"),
    FORTRESS_MACHINE("fortress_machine","堡垒机"),
    VPN("vpn","VPN"),
    UPS("ups","ups系统"),
    PC("pc","pc"),
    NETDISC("netdisc","网盘"),
    ;

    private String codeValue;
    private String codeDesc;

    private DeviceTypeEnum(String  codeValue, String codeDesc) {
        this.codeValue = codeValue;
        this.codeDesc = codeDesc;
    }

    public String   getCodeValue(){ return this.codeValue;}

    public String getCodeDesc(){ return this.codeDesc;}

    //根据codeValue获取枚举
    public static DeviceTypeEnum parseFromCodeValue(String codeValue){
        for (DeviceTypeEnum e : DeviceTypeEnum.values()){
            if(e.codeValue.equals(codeValue)){ return e;}
        }
        return null;
    }

    //根据codeValue获取描述
    public static String getCodeDescByCodeBalue(String codeValue){
        DeviceTypeEnum enumItem = parseFromCodeValue(codeValue);
        return enumItem == null ? "" : enumItem.getCodeDesc();
    }

    //验证codeValue是否有效
    public static boolean validateCodeValue(String codeValue){ return parseFromCodeValue(codeValue)!=null;}

    //列出所有值字符串
    public static String getString(){
        StringBuffer buffer = new StringBuffer();
        for (DeviceTypeEnum e : DeviceTypeEnum.values()){
            buffer.append(e.codeValue).append("--").append(e.getCodeDesc()).append(", ");
        }
        buffer.deleteCharAt(buffer.lastIndexOf(","));
        return buffer.toString().trim();
    }


}