package com.anji.mirror.push.domain.common;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class Mail implements Serializable {

    private Long mailId;

    private String title;

    private String from;

    private String to;

    private String copy;

    private String content;

    private String templateCode;

    private Date sendTime;

    private Integer times;

    private Long projectId;

    private String sendTimeStr;

    private String projectCode;

    private String customParams;

    private String createdBy;       //创建人
    private Date createdTime;       //创建时间
    private String updatedBy;       //更新人
    private Date updatedTime;       //更新时间

}
