package com.kgcorner.topspin.service.facebook;

/*
Description : Implementation of Facebook OAuthService
Author: kumar
Created on : 25/08/19
*/

import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.model.factory.AuthServiceModelFactory;
import com.kgcorner.topspin.service.OAuthService;
import com.kgcorner.utils.Strings;
import com.kgcorner.web.HttpUtil;
import kong.unirest.HttpResponse;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FacebookOAuthService implements OAuthService {
    public static final String FACEBOOK = "facebook";

    @Autowired
    private FacebookConfigProvider facebookConfigProvider;

    @Autowired
    private AuthServiceModelFactory authServiceModelFactory;

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
    public String getUserInfo(String accessToken) {
        String url = facebookConfigProvider.getUserInfoUrl("email,name", accessToken);
        HttpResponse<String> response = HttpUtil.doGet(url, null, null);
        return response.isSuccess() ? response.getBody() : null;
    }

    @Override
    public Login createLoginObject(String data) {
        if(Strings.isNullOrEmpty(data))
            throw new IllegalArgumentException("Data can't be null");
        JSONObject jsonObject = new JSONObject(data);
        Login login = authServiceModelFactory.createNewLogin();
        login.setUsername(jsonObject.getString("email"));
        return login;
    }

    @Override
    public String getOAuthServiceName() {
        return FACEBOOK;
    }
}