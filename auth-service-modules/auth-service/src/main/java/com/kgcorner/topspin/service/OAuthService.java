package com.kgcorner.topspin.service;

/*
Description : Contract for Open Authentication services
Author: kumar
Created on : 25/08/19
*/

import com.kgcorner.topspin.model.Login;

public interface OAuthService {
    /**
     * Fetches access token using auth code
     * @param code auth code
     * @param redirectUri redirect uri
     * @return accesstoken
     */
    String getAccessToken(String code, String redirectUri);

    /**
     * Validates the access token
     * @param token
     * @return
     */
    boolean validateAccessToken(String token);

    /***
     * Returns the name of the service which is used for authentication
     * @return
     */
    String getOAuthServiceName();

    /**
     * Returns user information from OAuth Server
     * @return user's info in JSON format
     */
    String getUserInfo(String accessToken);

    Login createLoginObject(String data);
}