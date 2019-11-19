package com.kgcorner.web;

import kong.unirest.GetRequest;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 19/11/19
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(Unirest.class)
public class HttpUtilTest {

    @Test
    public void doGet() {
        String url = "url";
        String data = "data";
        HttpResponse<String> stringHttpResponse = mock(HttpResponse.class);
        GetRequest request = mock(GetRequest.class);
        mockStatic(Unirest.class);
        when(Unirest.get(url)).thenReturn(request);
        when(request.queryString("a","b")).thenReturn(request);
        when(request.header("a","b")).thenReturn(request);
        when(request.asString()).thenReturn(stringHttpResponse);
        Map<String, String> queries = new HashMap<>();
        queries.put("a", "b");
        Map<String, String> headers = new HashMap<>();
        headers.put("a", "b");
        HttpResponse httpResponse = HttpUtil.doGet(url, queries, headers);
        assertNotNull(httpResponse);
    }

    @Test
    public void doPost() {
        String url = "url";
        String data = "data";
        HttpResponse<String> stringHttpResponse = mock(HttpResponse.class);
        HttpRequestWithBody request = mock(HttpRequestWithBody.class);
        mockStatic(Unirest.class);
        when(Unirest.post(url)).thenReturn(request);
        when(request.queryString("a","b")).thenReturn(request);
        when(request.header("a","b")).thenReturn(request);
        when(request.asString()).thenReturn(stringHttpResponse);
        Map<String, String> queries = new HashMap<>();
        queries.put("a", "b");
        Map<String, String> headers = new HashMap<>();
        headers.put("a", "b");
        Map<String, String> body = new HashMap<>();
        body.put("a", "b");
        HttpResponse httpResponse = HttpUtil.doPost(url, queries, headers, body);
        assertNotNull(httpResponse);
    }
}