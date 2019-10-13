package com.kgcorner.topspin;

/*
Description : Wrapper class for Http util
Author: kumar
Created on : 11/10/19
*/

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
}