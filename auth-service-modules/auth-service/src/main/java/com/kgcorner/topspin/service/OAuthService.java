package com.kgcorner.topspin.service;

/*
Description : Contract for Open Authentication services
Author: kumar
Created on : 25/08/19
*/

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
}