package com.kgcorner.topspin.providers;

import com.kgcorner.crypto.JwtUtility;
import com.kgcorner.topspin.clients.AuthServiceClient;
import com.kgcorner.topspin.model.BearerAuthToken;
import com.kgcorner.topspin.model.DummyToken;
import com.kgcorner.topspin.model.Role;
import com.kgcorner.topspin.model.SCHEMES;
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
public class DefaultBearerTokenAuthenticationProviderTest {

    private DefaultBearerTokenAuthenticationProvider provider;
    private AuthServiceClient client;
    @Before
    public void setup() {
        provider = new DefaultBearerTokenAuthenticationProvider();
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
                if(token.startsWith(SCHEMES.BEARER+" ")) {
                    DummyToken dummyToken = new DummyToken();
                    dummyToken.setAccessToken(accessToken);
                    return dummyToken;
                }
                return null;
            }
        });
        PowerMockito.mockStatic(JwtUtility.class);
        PowerMockito.when(JwtUtility.getClaim("ROLE", accessToken)).thenReturn("ADMIN");
        BearerAuthToken bearerAuthToken = new BearerAuthToken(null, "abc");
        Authentication authenticatedToken = provider.authenticate(bearerAuthToken);
        assertTrue(authenticatedToken instanceof BearerAuthToken);
        bearerAuthToken = (BearerAuthToken) authenticatedToken;
        assertNotNull( bearerAuthToken.getAuthorities());

        assertEquals("ADMIN",((Role) bearerAuthToken.getAuthorities().toArray()[0]).getAuthority());
        assertEquals("accessToken",bearerAuthToken.getPrincipal().toString());
    }

    @Test
    public void supports() {
        assertTrue(provider.supports(BearerAuthToken.class));
    }
}