package com.anji.plus.modules.export.controller.param;

import com.anji.plus.dto.BaseQueryBO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 功能描述：
 *
 * @Author: peiyanni
 * @Date: 2021/2/20 12:49
 */
@Setter
@Getter
public class GaeaExportQueryParam extends BaseQueryBO implements Serializable{

    /**
     * 文件标题
     */
    private String fileTitle;
}
