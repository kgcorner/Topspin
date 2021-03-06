package com.kgcorner.topspin;

/*
Description : Wrapper class for Http util
Author: kumar
Created on : 11/10/19
*/

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class HttpUtil {

    /**
     * Sends a get request to given server
     * @param url url of the service to be requested
     * @param headers map of headers if any
     * @return Response of the request
     */
    public static Response doGet(String url, Map<String, String> headers) {
        Response response = new Response();
        HttpClient client = getClient();
        HttpGet httpGetClient = new HttpGet(url);
        if(headers != null) {
            for(Map.Entry<String, String> entry : headers.entrySet()) {

                httpGetClient.setHeader(entry.getKey(), entry.getValue());
            }
        }

        try {
            HttpResponse httpResponse = client.execute(httpGetClient);

            response.setStatus(httpResponse.getStatusLine().getStatusCode());
            response.setData(getContent(httpResponse));
        } catch (IOException e) {
            response.setData(e.getLocalizedMessage());
            response.setStatus(0);
        }
        return response;
    }

    /**
     * Sends out a post request
     * @param url url of the resource
     * @param headers headers to send in request
     * @param data data to be sent out
     * @return response returned by the server
     */
    public static Response doPost(String url, Map<String, Object> headers, Map<String, Object> data) {
        Response response = new Response();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPostClient = new HttpPost(url);
        return getResponse(headers, data, response, client, httpPostClient);
    }

    /**
     * Sends out a put request
     * @param url url of the resource
     * @param headers headers to send in request
     * @param data data to be sent out
     * @return response returned by the server
     */
    public static Response doPut(String url, Map<String, Object> headers, Map<String, Object> data) {
        Response response = new Response();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut httpPostClient = new HttpPut(url);
        return getResponse(headers, data, response, client, httpPostClient);
    }

    private static Response getResponse(Map<String, Object> headers, Map<String, Object> data, Response response,
                                        CloseableHttpClient client, HttpEntityEnclosingRequestBase httpClient) {
        if(headers != null) {
            for(Map.Entry<String, Object> entry : headers.entrySet()) {
                if(entry.getValue() != null)
                    httpClient.setHeader(entry.getKey(), entry.getValue().toString());
                else
                    httpClient.setHeader(entry.getKey(), "");
            }
        }
        List<NameValuePair> entity = new ArrayList<>();
        if(data != null) {
            for(Map.Entry<String,Object> entry : data.entrySet()) {
                if(entry.getValue() != null)
                    entity.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                else
                    entity.add(new BasicNameValuePair(entry.getKey(), ""));
            }
        }
        try {
            httpClient.setEntity(new UrlEncodedFormEntity(entity));
            HttpResponse httpResponse = client.execute(httpClient);
            response.setStatus(httpResponse.getStatusLine().getStatusCode());
            response.setData(getContent(httpResponse));
        } catch (IOException e) {
            response.setData(e.getLocalizedMessage());
            response.setStatus(0);
        }
        return response;
    }


    private static HttpClient getClient() {
        return new DefaultHttpClient();
    }

    private static String getContent(HttpResponse response) throws IOException {
        // Get the response
        BufferedReader rd = new BufferedReader
            (new InputStreamReader(
                response.getEntity().getContent()));
        StringBuilder content = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
            content.append(line);
        }
        return content.toString();
    }

    public static Map<String, String> getAllQueries(String url) throws MalformedURLException {
        URL urlObject = new URL(url);
        Map<String, String> queries = new HashMap<>();
        String queryString = urlObject.getQuery();
        String[] parts = queryString.split(Pattern.quote("&"));
        for(String part : parts) {
            String[] entry = part.split(Pattern.quote("="));
            queries.put(entry[0], entry[1]);
        }
        return queries;
    }

    public static String getPath(String url) throws MalformedURLException {
        URL urlObject = new URL(url);
        return urlObject.getPath();
    }
}