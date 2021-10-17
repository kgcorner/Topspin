package com.kgcorner.topspin.clients;

import com.kgcorner.topspin.clients.model.TokenResponse;
import com.kgcorner.topspin.model.Token;
import com.kgcorner.topspin.service.Authenticator;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/10/21
 */

public class LocalAuthServiceClientTest {

    private LocalAuthServiceClient localAuthServiceClient;
    private Authenticator authenticator;

    @Before
    public void setUp() throws Exception {
        localAuthServiceClient = new LocalAuthServiceClient();
        authenticator = mock(Authenticator.class);
        Whitebox.setInternalState(localAuthServiceClient, "authenticator", authenticator);
    }

    @Test
    public void getToken() {
        String authToken = "authToken";
        Token token = mock(Token.class);
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        int expires = 100;
        when(token.getExpiresInSeconds()).thenReturn(expires);
        when(token.getRefreshToken()).thenReturn(refreshToken);
        when(token.getAccessToken()).thenReturn(accessToken);
        when(authenticator.authenticateWithToken(authToken)).thenReturn(token);
        TokenResponse tokenInResponse = localAuthServiceClient.getToken(authToken);
        assertEquals(refreshToken, tokenInResponse.getRefreshToken());
        assertEquals(accessToken, tokenInResponse.getAccessToken());
        assertEquals(expires, tokenInResponse.getExpiresInSeconds());
    }

    @Test
    public void getTokenForOAuth() {
        String code = "code";
        String serverName = "server-name";
        String authToken = "authToken";
        Token token = mock(Token.class);
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        int expires = 100;
        when(token.getExpiresInSeconds()).thenReturn(expires);
        when(token.getRefreshToken()).thenReturn(refreshToken);
        when(token.getAccessToken()).thenReturn(accessToken);
        when(authenticator.authenticateWithToken(authToken)).thenReturn(token);
        when(authenticator.validateAccessTokenAndAuthorize(code,serverName)).thenReturn(token);
        TokenResponse tokenInResponse = localAuthServiceClient.getTokenForOAuth(code, serverName);
        assertEquals(refreshToken, tokenInResponse.getRefreshToken());
        assertEquals(accessToken, tokenInResponse.getAccessToken());
        assertEquals(expires, tokenInResponse.getExpiresInSeconds());
    }

    @Test
    public void resolveAccessToken() {
        String bearerToken = "code";
        String redirectUri = "redirectUri";
        String serverName = "server-name";
        String authToken = "authToken";
        Token token = mock(Token.class);
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        int expires = 100;
        when(token.getExpiresInSeconds()).thenReturn(expires);
        when(token.getRefreshToken()).thenReturn(refreshToken);
        when(token.getAccessToken()).thenReturn(accessToken);
        when(authenticator.authenticateWithToken(authToken)).thenReturn(token);
        when(authenticator.resolveTokenAndAuthorize(bearerToken, redirectUri, serverName)).thenReturn(token);
        TokenResponse tokenInResponse = localAuthServiceClient.resolveAccessToken(bearerToken, redirectUri, serverName);
        assertEquals(refreshToken, tokenInResponse.getRefreshToken());
        assertEquals(accessToken, tokenInResponse.getAccessToken());
        assertEquals(expires, tokenInResponse.getExpiresInSeconds());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createLogin() {
        localAuthServiceClient.createLogin("","","");
    }
}