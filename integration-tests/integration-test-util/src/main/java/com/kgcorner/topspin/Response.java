package com.kgcorner.topspin;

/*
Description : Represents
Author: kumar
Created on : 11/10/19
*/

import java.util.List;
import java.util.Map;

public class Response {
    private String data;
    private int status;
    private Map<String, List<String>> headers;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }
}
