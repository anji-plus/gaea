package com.anji.plus.gaea.auth.modules.menu.controller.dto;

import com.anji.plus.gaea.bean.TreeNode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：
 * 菜单按钮树
 * @Author: peiyanni
 * @Date: 2021/2/3 15:02
 */
@Getter
@Setter
public class TreeDTO implements Serializable {
    private List<TreeNode> treeDatas;
    private List<String> checkedCodes;
}
