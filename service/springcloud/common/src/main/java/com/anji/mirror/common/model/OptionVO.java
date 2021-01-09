package com.anji.mirror.common.model;

public class OptionVO {
    private String label;
    private Object value;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "OptionVO{" +
                "label='" + label + '\'' +
                ", value=" + value +
                '}';
    }
}
