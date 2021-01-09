package com.anji.mirror.auth.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class StringParamVO implements Serializable {

    private static final long serialVersionUID=1L;
    String param;
}
