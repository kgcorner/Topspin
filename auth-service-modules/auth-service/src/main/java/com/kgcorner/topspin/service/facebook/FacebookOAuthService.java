package com.kgcorner.topspin.service.facebook;

/*
Description : Implementation of Facebook OAuthService
Author: kumar
Created on : 25/08/19
*/

import com.kgcorner.topspin.service.OAuthService;
import com.kgcorner.web.HttpUtil;
import kong.unirest.HttpResponse;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

public class FacebookOAuthService implements OAuthService {
    public static final String FACEBOOK = "facebook";

    @Autowired
    private FacebookConfigProvider facebookConfigProvider;

    @Override
    public String getAccessToken(String code, String redirectUri) {
        String url = facebookConfigProvider.getAccessTokenExchangeUrl(redirectUri, code);
        HttpResponse<String> response = HttpUtil.doGet(url, null, null);
        if(response.getStatus() == HttpStatus.SC_OK) {
            String body = response.getBody();
            JSONObject jsonObject = new JSONObject(body);
            return jsonObject.getString("access_token");
        }
        return null;
    }

    @Override
    public boolean validateAccessToken(String token) {
        String url = facebookConfigProvider.getAccessTokenValidationUrl(token);
        HttpResponse<String> response = HttpUtil.doGet(url, null, null);
        return response.isSuccess();
    }

    @Override
    public String getOAuthServiceName() {
        return FACEBOOK;
    }
}