package com.kgcorner.topspin.service;

import com.kgcorner.crypto.Hasher;
import com.kgcorner.topspin.Properties;
import com.kgcorner.topspin.dao.AuthenticationDao;
import com.kgcorner.topspin.model.Login;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;
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
    private AuthenticationDao<Login> mockedLoginAuthenticationDao;
    private Properties mockedProperties;
    private RegistrationService registrationService;

    @Before
    public void setup() {
        this.registrationService = new RegistrationService();
        this.mockedLoginAuthenticationDao = PowerMockito.mock(AuthenticationDao.class);
        this.mockedProperties = PowerMockito.mock(Properties.class);
        Whitebox.setInternalState(this.registrationService,
            "loginAuthenticationDao", mockedLoginAuthenticationDao);
        Whitebox.setInternalState(this.registrationService,
            "properties", mockedProperties);

    }

    @Test
    public void createLogin() {
        Login login = getDummyLogin();
        when(mockedLoginAuthenticationDao.create(login)).thenReturn(login);
        when(mockedProperties.getPasswordSalt()).thenReturn(PASSWORD_SALT);
        Login response = registrationService.createLogin(login.getUserName(), "password", login.getUserId());
        Assert.assertNotNull("Created login is null", response);
        Assert.assertEquals("User name is not matching", login.getUserName(), response.getUserName());
        Assert.assertEquals("password is not matching", login.getPassword(), response.getPassword());
        Assert.assertEquals("user id is not matching", login.getUserId(), response.getUserId());
    }

    private Login getDummyLogin() {
        Login login = new Login();
        login.setUserId("XXX");
        login.setPassword(Hasher.getCrypt("password", PASSWORD_SALT));
        login.setUserName("user");
        login.setId("0");
        return login;
    }
}