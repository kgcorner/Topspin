package com.kgcorner.topspin.config;

import com.kgcorner.topspin.services.ApplicationAuthenticationProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mock;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 14/08/21
 */

public class ApplicationConfigTest {

    private ApplicationConfig applicationConfig;
    private ApplicationAuthenticationProvider provider;

    @Before
    public void setUp() throws Exception {
        provider = mock(ApplicationAuthenticationProvider.class);
        applicationConfig = new ApplicationConfig();
        Whitebox.setInternalState(applicationConfig, "authenticationProvider", provider);
    }

    @Test
    public void configureAuthenticationManager() {
        AuthenticationManagerBuilder authenticationManager = mock(AuthenticationManagerBuilder.class);
        try {
            applicationConfig.configure(authenticationManager);
            Mockito.verify(authenticationManager).authenticationProvider(provider);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}