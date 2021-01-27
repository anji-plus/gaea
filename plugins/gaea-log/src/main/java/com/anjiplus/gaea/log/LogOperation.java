package com.anjiplus.gaea.log;

/**
 * Hello world!
 * @author lirui
 */
public class LogOperation {

    private String parmas;

    private String method;

    private String path;

    private String pageTitle;

    public String getParmas() {
        return parmas;
    }

    public void setParmas(String parmas) {
        this.parmas = parmas;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    @Override
    public String toString() {
        return "LogOperation{" +
                "parmas='" + parmas + '\'' +
                ", method='" + method + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
