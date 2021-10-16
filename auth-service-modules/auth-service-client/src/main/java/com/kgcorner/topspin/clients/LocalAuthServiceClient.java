package com.kgcorner.topspin.clients;


import com.kgcorner.topspin.clients.model.LoginResponse;
import com.kgcorner.topspin.clients.model.TokenResponse;
import com.kgcorner.topspin.model.Token;
import com.kgcorner.topspin.service.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/10/21
 */
@Component
public class LocalAuthServiceClient implements AuthServiceClient {

    @Autowired
    private Authenticator authenticator;

    @Override
    public TokenResponse getToken(String token) {
        TokenResponse tokenResponse = new TokenResponse();
        Token authenticationToken = authenticator.authenticateWithToken(token);
        tokenResponse.setAccessToken(authenticationToken.getAccessToken());
        tokenResponse.setRefreshToken(authenticationToken.getRefreshToken());
        tokenResponse.setExpiresInSeconds(authenticationToken.getExpiresInSeconds());
        return tokenResponse;
    }

    @Override
    public TokenResponse getTokenForOAuth(String token, String serverName) {
        return null;
    }

    @Override
    public TokenResponse resolveAccessToken(String token, String redirectUri, String serverName) {
        return null;
    }

    @Override
    public LoginResponse createLogin(String userName, String password, String userId) {
        return null;
    }
}