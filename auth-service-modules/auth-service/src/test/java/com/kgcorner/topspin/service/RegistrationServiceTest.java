package com.kgcorner.topspin.service;

import com.kgcorner.crypto.Hasher;
import com.kgcorner.dao.DataRepository;
import com.kgcorner.topspin.Properties;
import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.model.factory.AuthServiceModelFactory;
import com.kgcorner.topspin.model.persistent.LoginPersistentLayer;
import com.kgcorner.topspin.models.DummyLogin;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.powermock.api.mockito.PowerMockito.when;

/*
Description : Unit test for Registration service
Author: kumar
Created on : 21/10/19
*/
@RunWith(PowerMockRunner.class)
public class RegistrationServiceTest {

    public static final String PASSWORD_SALT = "askdhajskldjskldjklasjdklasjdklasjdklasdaskdhajskldjskldjklasjdklasjdklas" +
        "jdklasdaskdasdasedashajskldjskldjklasjdklasjdklasjdklasdaskdhaj";
    private LoginPersistentLayer mockedLoginPersistentLayer;
    private Properties mockedProperties;
    private RegistrationService registrationService;
    private AuthServiceModelFactory mockedAuthServiceModelFactory;

    @Before
    public void setup() {
        this.registrationService = new RegistrationService();
        this.mockedLoginPersistentLayer = PowerMockito.mock(LoginPersistentLayer.class);
        this.mockedProperties = PowerMockito.mock(Properties.class);
        this.mockedAuthServiceModelFactory = PowerMockito.mock(AuthServiceModelFactory.class);
        Whitebox.setInternalState(this.registrationService,
            "loginPersistentLayer", mockedLoginPersistentLayer);
        Whitebox.setInternalState(this.registrationService,
            "properties", mockedProperties);
        Whitebox.setInternalState(this.registrationService,
            "authServiceModelFactory", mockedAuthServiceModelFactory);

    }

    @Test
    public void createLogin() {
        Login login = getDummyLogin();
        when(mockedLoginPersistentLayer.createLogin(login)).thenReturn(login);
        when(mockedProperties.getPasswordSalt()).thenReturn(PASSWORD_SALT);
        when(mockedAuthServiceModelFactory.createNewLogin()).thenReturn(new DummyLogin());
        Login response = registrationService.createLogin(login.getUserName(), "password", login.getUserId());
        Assert.assertNotNull("Created login is null", response);
        Assert.assertEquals("User name is not matching", login.getUserName(), response.getUserName());
        Assert.assertEquals("password is not matching", login.getPassword(), response.getPassword());
        Assert.assertEquals("user id is not matching", login.getUserId(), response.getUserId());
    }

    @Test
    public void createLoginWithEmptyPassword() {
        Login login = getDummyLogin();
        login.setPassword(null);
        when(mockedLoginPersistentLayer.createLogin(login)).thenReturn(login);
        when(mockedProperties.getPasswordSalt()).thenReturn(PASSWORD_SALT);
        when(mockedAuthServiceModelFactory.createNewLogin()).thenReturn(new DummyLogin());
        Login response = registrationService.createLogin(login.getUserName(), null, login.getUserId());
        Assert.assertNotNull("Created login is null", response);
        Assert.assertEquals("User name is not matching", login.getUserName(), response.getUserName());
        Assert.assertEquals("password is not matching", login.getPassword(), response.getPassword());
        Assert.assertEquals("user id is not matching", login.getUserId(), response.getUserId());
    }

    private Login getDummyLogin() {
        Login login = new DummyLogin();
        login.setUserId("XXX");
        login.setUserName("user");
        login.setPassword(Hasher.getCrypt("password",
            PASSWORD_SALT));
        login.setUserId("0");
        return login;
    }
}