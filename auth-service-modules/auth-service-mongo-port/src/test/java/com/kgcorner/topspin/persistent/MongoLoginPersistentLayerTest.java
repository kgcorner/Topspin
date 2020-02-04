package com.kgcorner.topspin.persistent;

import com.kgcorner.topspin.model.Login;
import com.kgcorner.topspin.model.LoginModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.when;


/*
Description : <Write class Description>
Author: kumar
Created on : 17/11/19
*/

@RunWith(PowerMockRunner.class)
public class MongoLoginPersistentLayerTest {

    private MongoLoginPersistentLayer mongoLoginPersistentLayer;
    private MongoTemplate mockedMongoTemplate;

    @Before
    public void setUp() throws Exception {
        mockedMongoTemplate = PowerMockito.mock(MongoTemplate.class);
        this.mongoLoginPersistentLayer = new MongoLoginPersistentLayer(mockedMongoTemplate);
    }

    @Test
    public void getLogin() {
        LoginModel loginModel = new LoginModel();
        loginModel.setUsername("uName");
        when(mockedMongoTemplate.findOne(any(), any())).thenReturn(loginModel);
        Login response = mongoLoginPersistentLayer.getLogin("uName");
        assertNotNull("Returned model is null", response);
        assertEquals("user name is not matching", "uName", response.getUsername());
    }

    @Test
    public void getLoginFailure() {
        when(mockedMongoTemplate.findOne(any(), any())).thenReturn(null);
        Login response = mongoLoginPersistentLayer.getLogin("uName");
        assertNull("Returned model is not null", response);
    }

    @Test
    public void createLogin() {
        LoginModel loginModel = new LoginModel();
        loginModel.setUserId("0");
        when(mockedMongoTemplate.insert(any(LoginModel.class))).thenReturn(loginModel);
        Login response = mongoLoginPersistentLayer.createLogin(loginModel);
        assertNotNull("Returned model is null", response);
        assertEquals("user id is not matching", "0", response.getUserId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createLoginFailure() {
        Login loginModel = PowerMockito.mock(Login.class);
        mongoLoginPersistentLayer.createLogin(loginModel);
    }

    @Test
    public void update() {
        Login loginModel = new LoginModel();
        loginModel.setUserId("0");
        when(mockedMongoTemplate.save(any(LoginModel.class))).thenReturn((LoginModel)loginModel);
        mongoLoginPersistentLayer.update(loginModel);
        assertNotNull(loginModel);
        assertEquals("Model's userId is not matching","0", loginModel.getUserId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateFailure() {
        Login loginModel = PowerMockito.mock(Login.class);
        mongoLoginPersistentLayer.update(loginModel);
    }
}