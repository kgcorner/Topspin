package com.kgcorner.topspin.service;

/*
Description : <Write class Description>
Author: kumar
Created on : 25/08/19
*/

import com.kgcorner.exceptions.ResourceNotFoundException;
import com.kgcorner.topspin.model.Token;

public interface AuthenticationService {
    Token authenticateToken(String token);

    /**
     * Authenticate Access token provided by OAuthServer and returns Token Object for accessing topspin services
     * @param code
     * @param serverName
     * @return
     * @throws ResourceNotFoundException
     */
    Token validateAccessTokenAndAuthenticate(String code, String serverName) throws ResourceNotFoundException;

    /**
     * Resolves accesstoken from given auth code returns Token Object for accessing topspin services
     * @param token
     * @param redirectUri
     * @param serverName
     * @return Token Object for accessing topspin services
     */
    Token resolveAuthCodeAndAuthenticate(String token, String redirectUri, String serverName) throws ResourceNotFoundException;
}