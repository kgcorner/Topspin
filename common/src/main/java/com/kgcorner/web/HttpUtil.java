package com.kgcorner.web;

/*
Description : Utility class for making web requests
Author: kumar
Created on : 15/09/19
*/

import kong.unirest.*;

import java.util.Map;

public class HttpUtil {

    public static HttpResponse doGet(String url, Map<String, String> queries, Map<String, String> headers) {
        GetRequest getRequest = Unirest.get(url);
        if(queries != null) {
            for(Map.Entry<String, String> entries : queries.entrySet()) {
                getRequest = getRequest.queryString(entries.getKey(), entries.getValue());
            }
        }

        if(headers != null) {
            for(Map.Entry<String, String> entries : headers.entrySet()) {
                getRequest = getRequest.header(entries.getKey(), entries.getValue());
            }
        }
        return getRequest.asString();
    }

    public static HttpResponse doPost(String url, Map<String, String> queries, Map<String, String> headers,
                                      Map<String, String> body) {
        HttpRequestWithBody postRequest = Unirest.post(url);
        MultipartBody multipartBodyRequest = null;
        if(queries != null) {
            for(Map.Entry<String, String> entries : queries.entrySet()) {
                postRequest = postRequest.queryString(entries.getKey(), entries.getValue());
            }
        }

        if(headers != null) {
            for(Map.Entry<String, String> entries : headers.entrySet()) {
                postRequest = postRequest.header(entries.getKey(), entries.getValue());
            }
        }

        if(body != null) {

            for(Map.Entry<String, String> entries : body.entrySet()) {
                if(multipartBodyRequest == null)
                    multipartBodyRequest = postRequest.field(entries.getKey(), entries.getValue());
                else
                    multipartBodyRequest = multipartBodyRequest.field(entries.getKey(), entries.getValue());

            }
        }
        if(multipartBodyRequest == null)
            return postRequest.asString();
        else
            return multipartBodyRequest.asString();
    }


}