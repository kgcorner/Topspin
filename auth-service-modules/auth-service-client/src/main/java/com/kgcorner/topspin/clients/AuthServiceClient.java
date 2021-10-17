package com.kgcorner.topspin.clients;

import com.kgcorner.topspin.clients.model.LoginResponse;
import com.kgcorner.topspin.clients.model.TokenResponse;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/10/21
 */

public interface AuthServiceClient {
    /**
     * Authenticate and return Access token
     * @param token
     * @return
     */
    TokenResponse getToken(String token);

    /**
     * Authenticate given access token against given oAuth Service and returns access token
     * @param token
     * @param serverName
     * @return
     */
    TokenResponse getTokenForOAuth(String token, String serverName);

    /**
     * authenticate give auth code against given oath service, resolves access token
     * and returns access token generated for topspin
     * @param token
     * @param redirectUri
     * @param serverName
     * @return
     */
    TokenResponse resolveAccessToken(String token,String redirectUri,String serverName);

    /**
     * Creates login resource
     * @param userName
     * @param password
     * @param userId
     * @return
     */
    LoginResponse createLogin(String userName, String password, String userId);
}