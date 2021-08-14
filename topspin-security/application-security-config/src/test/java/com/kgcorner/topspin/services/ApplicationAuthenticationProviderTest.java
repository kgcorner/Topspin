package com.kgcorner.topspin.services;

import com.kgcorner.topspin.model.ApplicationRequestCredentials;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.springframework.security.core.Authentication;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 14/08/21
 */

public class ApplicationAuthenticationProviderTest {
    private ApplicationAuthenticationService service;
    private ApplicationAuthenticationProvider provider;
    @Before
    public void setUp() throws Exception {
        provider = new ApplicationAuthenticationProvider();
        service = mock(ApplicationAuthenticationService.class);
        Whitebox.setInternalState(provider, "applicationAuthenticationService",service);
    }

    @Test
    public void authenticateNotAuthenticationObject() {
        assertNull(provider.authenticate(mock(Authentication.class)));
    }

    @Test
    public void authenticate() {
        ApplicationRequestCredentials credentials = new ApplicationRequestCredentials("name",
            null,"123", "9087", false);
        ApplicationRequestCredentials validatedCredentials = new ApplicationRequestCredentials("name",
            "key","123", "9087", true);
        when(service.validateRequest("name", "9087","123")).thenReturn(validatedCredentials);
        ApplicationRequestCredentials result = (ApplicationRequestCredentials) provider.authenticate(credentials);
        assertEquals("key", result.getApplicationKey());
        assertTrue(result.isAuthenticated());

    }

    @Test
    public void supportsFail() {
        assertFalse(provider.supports(Object.class));
    }

    @Test
    public void supports() {
        assertTrue(provider.supports(ApplicationRequestCredentials.class));
    }
}