package com.kgcorner.topspin;

import com.kgcorner.topspin.model.User;
import com.kgcorner.topspin.services.UserService;
import com.kgcorner.topspin.util.UserServiceTestUtility;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;
/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/02/20
 */

public class UserResourceImplTest {

    private UserService userService;
    private UserResourceImpl userResource;
    @Before
    public void setUp() throws Exception {
        userService = PowerMockito.mock(UserService.class);
        userResource = new UserResourceImpl();
        Whitebox.setInternalState(userResource, "userService", userService);
    }

    @Test
    public void getUser() {
        User dummyUser = UserServiceTestUtility.createDummyUser();
        when(userService.getUser("0")).thenReturn(dummyUser);
        User user = userResource.getUser("0");
        assertEquals("User id isn't matching", dummyUser.getId(), user.getId());
        assertEquals("User name isn't matching", dummyUser.getName(), user.getName());
    }

    @Test
    public void getAllUsers() {
        User user1 = UserServiceTestUtility.createDummyUser("1", "Gaurav");
        User user2 = UserServiceTestUtility.createDummyUser("2", "Nakku");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        when(userService.getAllUsers(1, 2)).thenReturn(users);
        List<User> result = userResource.getAllUsers(1, 2);
        assertEquals("User count", users.size(), result.size());
        assertEquals("User name isn't matching", users.get(0).getName(), result.get(0).getName());
        assertEquals("User name isn't matching", users.get(1).getName(), result.get(1).getName());
        assertEquals("User id isn't matching", users.get(0).getId(), result.get(0).getId());
        assertEquals("User id isn't matching", users.get(1).getId(), result.get(1).getId());

    }

    @Test
    public void createUser() {
        User mockedUser = UserServiceTestUtility.createDummyUser("0","gaurav");
        when(userService.createUser("gaurav", "username", "email",
            "contact", "other", "male")).thenReturn(mockedUser);
        User response = userResource.createUser("gaurav", "username", "email",
            "contact", "male", "other");
        assertNotNull(response);
        assertEquals("Name is not matching", mockedUser.getName(), response.getName());
    }

    @Test
    public void updateUser() {
        User mockedUser = UserServiceTestUtility.createDummyUser("0","gaurav");
        when(userService.updateUser(Matchers.anyString(),
            Matchers.anyString(),
            Matchers.anyString(),
            Matchers.anyString(),
            Matchers.anyString())).thenReturn(mockedUser);
        User response = userResource.updateUser("","","","","");
        assertNotNull(response);
        assertEquals(mockedUser.getId(), response.getId());
        assertEquals(mockedUser.getName(), response.getName());
    }
}