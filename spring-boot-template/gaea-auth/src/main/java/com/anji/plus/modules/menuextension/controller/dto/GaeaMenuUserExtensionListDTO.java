package com.anji.plus.modules.menuextension.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 功能描述：
 *
 * @Author: peiyanni
 * @Date: 2021/2/24 14:50
 */
@Getter
@Setter
public class GaeaMenuUserExtensionListDTO extends GaeaMenuUserExtensionDTO {
    private List<GaeaMenuUserExtensionListDTO> children;
}
