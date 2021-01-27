package com.github.anji.plus.gaea.bean;

import java.util.List;

/**
 * 树节点
 * @author lirui
 * @since 2020-11-23
 *
 **/

public class TreeNode {

    private String id;
    private String label;
    private String icon;
    private String resUrl;
    private List<TreeNode> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
