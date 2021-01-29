package com.github.anji.plus.gaea.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 前端下拉键值对
 * @author lirui
 * @since 2021-01-12
 *
 **/
public class KeyValue implements Serializable {

    private String id;
    private String text;
    private List<KeyValue> children = new ArrayList<>();

    public KeyValue() {}

    public KeyValue(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<KeyValue> getChildren() {
        return children;
    }

    public void setChildren(List<KeyValue> children) {
        this.children = children;
    }
}
