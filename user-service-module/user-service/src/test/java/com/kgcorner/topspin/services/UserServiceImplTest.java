package com.kgcorner.topspin.services;

import com.kgcorner.topspin.model.User;
import com.kgcorner.topspin.persistence.UserPersistenceLayer;
import com.kgcorner.topspin.util.UserServiceTestUtility;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 14/02/20
 */

public class UserServiceImplTest {

    private UserServiceImpl userService;
    private UserPersistenceLayer userPersistenceLayer;
    @Before
    public void setUp() throws Exception {
        userService = new UserServiceImpl();
        userPersistenceLayer = PowerMockito.mock(UserPersistenceLayer.class);
        Whitebox.setInternalState(userService, "userPersistenceLayer", userPersistenceLayer);
    }

    @Test
    public void getUser() {
        PowerMockito.when(userPersistenceLayer.getUser("1"))
            .thenReturn(UserServiceTestUtility.createDummyUser("1", "Gaurav"));
        User response = userService.getUser("1");
        Assert.assertNotNull("response is null", response);
        Assert.assertEquals("Name is not matching", "Gaurav", response.getName());
        Assert.assertEquals("id is not matching", "1", response.getId());
    }
}