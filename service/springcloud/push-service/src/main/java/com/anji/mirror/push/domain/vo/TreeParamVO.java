package com.anji.mirror.push.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author anji gaea teams
 * @Date: 2020/10/30
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TreeParamVO {
    List<TemplateTreeVO> treeList;
}
