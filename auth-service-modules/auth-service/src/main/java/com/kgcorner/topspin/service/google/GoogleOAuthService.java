package com.kgcorner.topspin.service.google;

/*
Description : <Write class Description>
Author: kumar
Created on : 25/08/19
*/

import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.model.factory.AuthServiceModelFactory;
import com.kgcorner.topspin.service.OAuthService;
import com.kgcorner.web.HttpUtil;
import kong.unirest.HttpResponse;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GoogleOAuthService implements OAuthService {

    @Autowired
    private GoogleConfigProvider googleConfigProvider;

    @Autowired
    private AuthServiceModelFactory authServiceModelFactory;

    public static final String GOOGLE = "google";

    @Override
    public String getAccessToken(String code, String redirectUri) {
        String url = googleConfigProvider.getAccessTokenExchangeUrl(redirectUri, code);
        Map<String, String> postData = new HashMap<>();
        postData.put("code", code);
        postData.put("client_id", googleConfigProvider.getAppKey());
        postData.put("client_secret", googleConfigProvider.getSecretKey());
        postData.put("redirect_uri", redirectUri);
        postData.put("grant_type", "authorization_code");
        HttpResponse<String> response = HttpUtil.doPost(url, null, null, postData);
        if(response.getStatus() == HttpStatus.SC_OK) {
            String body = response.getBody();
            JSONObject jsonObject = new JSONObject(body);
            return jsonObject.getString("access_token");
        }
        return null;
    }

    @Override
    public boolean validateAccessToken(String token) {
        String url = googleConfigProvider.getAccessTokenValidationUrl(token);
        HttpResponse<String> response = HttpUtil.doGet(url, null, null);
        return response.isSuccess();
    }

    @Override
    public String getUserInfo(String accessToken) {
        String url = googleConfigProvider.getUserInfoUrl(null, null);
        Map<String, String>  headerData = new HashMap<>();
        headerData.put("Authorization","Bearer "+accessToken);
        HttpResponse<String> response = HttpUtil.doGet(url, null, headerData);
        if(response.isSuccess()) {
            return response.getBody();
        }
        return null;
    }

    @Override
    public Login createLoginObject(String data) {
        JSONObject jsonObject = new JSONObject(data);
        String email = jsonObject.getJSONArray("emailAddresses").getJSONObject(0).getString("value");
        Login login = authServiceModelFactory.createNewLogin();
        login.setUserName(email);
        return login;
    }

    @Override
    public String getOAuthServiceName() {
        return GOOGLE;
    }
}