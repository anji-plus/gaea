package com.haitong.nla.auth.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 帮助中心关键字搜索条件
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SerarchConditionVO implements Serializable {
    private static final long serialVersionUID=1L;
    /** 帮助分类，字典=HELP_CATEGORY */
    private String KeyWord;
}
