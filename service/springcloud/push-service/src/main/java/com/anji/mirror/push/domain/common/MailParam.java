package com.anji.mirror.push.domain.common;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Data
public class MailParam {


    String templateCode;

    String subject;

    String to;

    String from;

    String copy;

    String Bcc;

    String param;

    Map<String, MultipartFile> fileMap;

    Map paramMap;

    String secret;

    String sign;


}
