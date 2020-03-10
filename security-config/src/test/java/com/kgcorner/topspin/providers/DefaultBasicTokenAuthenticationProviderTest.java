package com.kgcorner.topspin.providers;

import com.kgcorner.crypto.JwtUtility;
import com.kgcorner.topspin.clients.AuthServiceClient;
import com.kgcorner.topspin.clients.model.Token;
import com.kgcorner.topspin.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.security.core.Authentication;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 23/02/20
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(JwtUtility.class)
public class DefaultBasicTokenAuthenticationProviderTest {

    private DefaultBasicTokenAuthenticationProvider provider;
    private AuthServiceClient client;
    @Before
    public void setup() {
        provider = new DefaultBasicTokenAuthenticationProvider();
        client = PowerMockito.mock(AuthServiceClient.class);
        Whitebox.setInternalState(provider, "client", client);
    }

    @Test
    public void authenticate() {
        String accessToken = "accessToken";
        PowerMockito.when(client.getToken(Matchers.anyString())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                String token = invocationOnMock.getArgument(0).toString();
                if(token.startsWith(SCHEMES.BASIC+" ")) {
                    Token tokenModel = new Token();
                    tokenModel.setAccessToken(accessToken);
                    return tokenModel;
                }
                return null;
            }
        });
        PowerMockito.mockStatic(JwtUtility.class);
        PowerMockito.when(JwtUtility.getClaim("ROLE", accessToken)).thenReturn("ADMIN");
        BasicAuthToken basicAuthToken = new BasicAuthToken("user", "password");
        Authentication authenticatedToken = provider.authenticate(basicAuthToken);
        assertTrue(authenticatedToken instanceof BasicAuthToken);
        basicAuthToken = (BasicAuthToken) authenticatedToken;
        assertNotNull( basicAuthToken.getAuthorities());

        assertEquals("ADMIN",((Role) basicAuthToken.getAuthorities().toArray()[0]).getAuthority());
        assertEquals("accessToken", basicAuthToken.getPrincipal().toString());
    }

    @Test
    public void authenticateForMultipleRole() {
        String accessToken = "accessToken";
        PowerMockito.when(client.getToken(Matchers.anyString())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                String token = invocationOnMock.getArgument(0).toString();
                if(token.startsWith(SCHEMES.BASIC+" ")) {
                    Token tokenModel = new Token();
                    tokenModel.setAccessToken(accessToken);
                    return tokenModel;
                }
                return null;
            }
        });
        PowerMockito.mockStatic(JwtUtility.class);
        PowerMockito.when(JwtUtility.getClaim("ROLE", accessToken)).thenReturn("ADMIN,USER");
        BasicAuthToken basicAuthToken = new BasicAuthToken("user", "password");
        Authentication authenticatedToken = provider.authenticate(basicAuthToken);
        assertTrue(authenticatedToken instanceof BasicAuthToken);
        basicAuthToken = (BasicAuthToken) authenticatedToken;
        assertNotNull( basicAuthToken.getAuthorities());

        assertEquals("ADMIN",((Role) basicAuthToken.getAuthorities().toArray()[0]).getAuthority());
        assertEquals("USER",((Role) basicAuthToken.getAuthorities().toArray()[1]).getAuthority());
        assertEquals("accessToken", basicAuthToken.getPrincipal().toString());
    }

    @Test
    public void supports() {
        assertTrue(provider.supports(BasicAuthToken.class));
    }
}