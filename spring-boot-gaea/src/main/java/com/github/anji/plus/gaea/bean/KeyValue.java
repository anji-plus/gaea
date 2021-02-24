package com.github.anji.plus.gaea.bean;

import java.io.Serializable;

/**
 * 前端下拉键值对
 * @author lr
 * @since 2021-01-12
 *
 **/
public class KeyValue implements Serializable {

    private Object id;
    private String text;
    public KeyValue() {}

    public KeyValue(Object id, String text) {
        this.id = id;
        this.text = text;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
