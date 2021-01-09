package com.anji.mirror.auth.domain.vo;

public class RequestVO<T> {

    private Integer currentPage=1;      //当前是每几页
    private Integer pageSize=10;        //每页显示多少行
    private String orderBy;             //排序字段及升序降序

    private String opToken;             //当前操作者token
    private Long opUserId;              //当前操作者userId
    private String opUserName;          //当前操作者用户名
    private String opIsFrom;            //当前操作者客户端类型 pc/app
    private String opSourceIP;          //当前操作者来源ip
    private String sign;
    private String time;

    private T requestData;
}
