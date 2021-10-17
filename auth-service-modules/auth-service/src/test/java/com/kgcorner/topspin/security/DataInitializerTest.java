package com.kgcorner.topspin.security;

import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.persistent.LoginPersistentLayer;
import com.kgcorner.topspin.service.RegistrationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 16/10/21
 */

public class DataInitializerTest {

    private RegistrationService registrationService;
    private LoginPersistentLayer loginPersistentLayer;
    private DataInitializer dataInitializer;
    @Before
    public void setup() {
        dataInitializer = new DataInitializer();
        registrationService = mock(RegistrationService.class);
        loginPersistentLayer = mock(LoginPersistentLayer.class);
        Whitebox.setInternalState(dataInitializer, "registrationService", registrationService);
        Whitebox.setInternalState(dataInitializer, "loginPersistentLayer", loginPersistentLayer);
    }

    @Test
    public void createAdminUserWhenCreated() {
        Login login = mock(Login.class);
        when(loginPersistentLayer.getLogin("admin")).thenReturn(login);
        dataInitializer.createAdminUser();
        Mockito.verifyNoMoreInteractions(registrationService);
    }

    @Test
    public void createAdminUser() {
        when(loginPersistentLayer.getLogin("admin")).thenReturn(null);
        dataInitializer.createAdminUser();
        Mockito.verify(registrationService).createAdmin("admin", "admin", "thisisfirstaccount");
    }
}