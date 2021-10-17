package com.kgcorner.topspin.clients;


import com.kgcorner.topspin.clients.model.LoginResponse;
import com.kgcorner.topspin.clients.model.TokenResponse;
import com.kgcorner.topspin.model.Token;
import com.kgcorner.topspin.service.Authenticator;
import com.kgcorner.topspin.service.RegistrationService;
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

    @Autowired
    private RegistrationService registrationService;

    @Override
    public TokenResponse getToken(String token) {
        Token authenticationToken = authenticator.authenticateWithToken(token);
        return getTokenResponse(authenticationToken);
    }
    @Override
    public TokenResponse getTokenForOAuth(String token, String serverName) {
        Token accessToken = authenticator.validateAccessTokenAndAuthorize(token, serverName);
        return getTokenResponse(accessToken);
    }

    @Override
    public TokenResponse resolveAccessToken(String token, String redirectUri, String serverName) {
        Token token1 = authenticator.resolveTokenAndAuthorize(token, redirectUri, serverName);
        return getTokenResponse(token1);
    }

    @Override
    public LoginResponse createLogin(String userName, String password, String userId) {
        throw new UnsupportedOperationException();
    }



    private TokenResponse getTokenResponse(Token authenticationToken) {
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(authenticationToken.getAccessToken());
        tokenResponse.setRefreshToken(authenticationToken.getRefreshToken());
        tokenResponse.setExpiresInSeconds(authenticationToken.getExpiresInSeconds());
        return tokenResponse;
    }

}