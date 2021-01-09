package com.anji.mirror.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TreeVO implements Serializable{

    private Object id; // 树形节点ID String, Integer

    private String label; // 树形节点文本

    private boolean disabled = false; // 该节点是禁用

    private List<TreeVO> children=new ArrayList<TreeVO>();// 子节点

    private Object extend;

    private Long parentId;
    private String name;
    private String value;
    private Integer type;
    private boolean isExpend;
    private boolean isChecked;

    public TreeVO(){}

    public TreeVO(Object id, String label){
        this.id = id;
        this.label = label;
    }
    /************************** getter setter ****************************/

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public List<TreeVO> getChildren() {
        return children;
    }

    public void setChildren(List<TreeVO> children) {
        this.children = children;
    }

    public void addChildren(TreeVO child) {
        this.children.add(child);
    }

    public Object getExtend() {
        return extend;
    }

    public void setExtend(Object extend) {
        this.extend = extend;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public boolean isExpend() {
        return isExpend;
    }

    public void setExpend(boolean expend) {
        isExpend = expend;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "TreeVO{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", children=" + children +
                '}';
    }
}

