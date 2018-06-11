package com.holyw.holyw.common;

public class HttpResult {

    private int statusCode;

    private String result;

    private String defaultCharset;

    public HttpResult() {}

    public HttpResult(int statusCode, String result) {
        this.statusCode = statusCode;
        this.result = result;
    }

    public HttpResult(int statusCode, String result, String defaultCharset) {
        this.statusCode = statusCode;
        this.result = result;
        this.defaultCharset = defaultCharset;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDefaultCharset() {
        return defaultCharset;
    }

    public void setDefaultCharset(String defaultCharset) {
        this.defaultCharset = defaultCharset;
    }
}
