package com.anji.mirror.push.domain.common;


import java.util.List;
import java.util.Map;

/**
 * @author anji gaea teams
 * @Date: 2020/10/17
 * @Description:
 */
public class TParam {

    int begin;

    int end;

    int contentBegin;

    int contentEnd;

    String listParam;

    String itemVar;

    List<String> itemParamList;

    Map<String, String> paramMap;

    String listTemplate;

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getContentBegin() {
        return contentBegin;
    }

    public void setContentBegin(int contentBegin) {
        this.contentBegin = contentBegin;
    }

    public int getContentEnd() {
        return contentEnd;
    }

    public void setContentEnd(int contentEnd) {
        this.contentEnd = contentEnd;
    }

    public String getListParam() {
        return listParam;
    }

    public void setListParam(String listParam) {
        this.listParam = listParam;
    }

    public String getItemVar() {
        return itemVar;
    }

    public void setItemVar(String itemVar) {
        this.itemVar = itemVar;
    }

    public List<String> getItemParamList() {
        return itemParamList;
    }

    public void setItemParamList(List<String> itemParamList) {
        this.itemParamList = itemParamList;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public String getListTemplate() {
        return listTemplate;
    }

    public void setListTemplate(String listTemplate) {
        this.listTemplate = listTemplate;
    }
}
