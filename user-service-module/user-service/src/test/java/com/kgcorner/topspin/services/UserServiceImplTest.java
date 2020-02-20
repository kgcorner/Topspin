package com.kgcorner.topspin.services;

import com.kgcorner.exceptions.ResourceNotFoundException;
import com.kgcorner.topspin.factory.UserServiceModelFactory;
import com.kgcorner.topspin.model.User;
import com.kgcorner.topspin.persistence.UserPersistenceLayer;
import com.kgcorner.topspin.util.UserServiceTestUtility;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

    @Test
    public void testGetUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i <20; i++) {
            users.add(UserServiceTestUtility.createDummyUser());
        }
        PowerMockito.when(userPersistenceLayer.getUsers(1, 20)).thenReturn(users);
        List<User> response = userService.getAllUsers(1, 20);
        assertNotNull("Response is null", response);
        assertEquals("User count isn't matching", users.size(), response.size());
    }

    @Test
    public void createUser() {
        UserServiceModelFactory userServiceModelFactory = PowerMockito.mock(UserServiceModelFactory.class);
        User mockedUser = UserServiceTestUtility.createDummyUser();
        Whitebox.setInternalState(userService, "userServiceModelFactory", userServiceModelFactory);
        PowerMockito.when(userServiceModelFactory.createUserModel()).thenReturn(mockedUser);
        PowerMockito.when(userPersistenceLayer.createUser(Matchers.any(User.class))).thenReturn(mockedUser);
        User response = userService.createUser("Gaurav","username",
            "email@email.com", "contact","other");
        assertNotNull(response);
        assertEquals("Name is not matching", "Gaurav", response.getName());
    }

    @Test
    public void createUserWithBlankName() {
        try {
            User response = userService.createUser("", "username",
                "email@email.com", "contact", "other");
        } catch (IllegalArgumentException x) {
            assertEquals("Name can't be blank", x.getMessage());
        }
    }

    @Test
    public void createUserWithBlankUserName() {
        try {
            User response = userService.createUser("name", "",
                "email@email.com", "contact", "other");
        } catch (IllegalArgumentException x) {
            assertEquals("Username can't be blank", x.getMessage());
        }
    }

    @Test
    public void createUserWithInvalidEmail() {
        try {
            User response = userService.createUser("name", "username",
                "email", "contact", "other");
        } catch (IllegalArgumentException x) {
            assertEquals("Invalid email", x.getMessage());
        }
    }


    @Test
    public void updateUser() {
        User mockedUser = UserServiceTestUtility.createDummyUser("0", "Gaurav");
        mockedUser.setEmail("email@domail.com");
        mockedUser.setContact("0909090909");
        mockedUser.setOthers("Other info");
        mockedUser.setUserName("username");
        String id = "0";
        String name = "new name";
        String email = "email@email.com";
        String contact = "new contact";
        String other = "new other info";
        PowerMockito.when(userPersistenceLayer.getUser("0")).thenReturn(mockedUser);
        PowerMockito.when(userPersistenceLayer.updateUser(Matchers.any(User.class))).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                User arg = (User) invocationOnMock.getArguments()[0];
                User user = UserServiceTestUtility.createDummyUser();
                user.setUserName(arg.getUserName());
                user.setOthers(arg.getOthers());
                user.setContact(arg.getContact());
                user.setEmail(arg.getEmail());
                user.setId(arg.getId());
                user.setName(arg.getName());
                return user;
            }
        });

        User response = userService.updateUser(id, name, email,contact,other);
        assertNotNull(response);
        assertEquals(name, response.getName());
        assertEquals(email, response.getEmail());
        assertEquals(contact, response.getContact());
        assertEquals(other, response.getOthers());
        assertEquals(mockedUser.getUserName(), response.getUserName());
        assertEquals(id, response.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateNonExistingUser() {
        PowerMockito.when(userPersistenceLayer.getUser("0")).thenReturn(null);
        userService.updateUser("0","","","","");
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateUserWithInvalidEmail() {
        PowerMockito.when(userPersistenceLayer.getUser("0")).thenReturn(UserServiceTestUtility.createDummyUser());
        userService.updateUser("0","","invalid email","","");
    }

    @Test
    public void updateUserWithOnlyName() {
        User mockedUser = UserServiceTestUtility.createDummyUser("0", "Gaurav");
        mockedUser.setEmail("email@domail.com");
        mockedUser.setContact("0909090909");
        mockedUser.setOthers("Other info");
        mockedUser.setUserName("username");
        String name = "New Name";
        PowerMockito.when(userPersistenceLayer.getUser("0")).thenReturn(mockedUser);
        PowerMockito.when(userPersistenceLayer.updateUser(Matchers.any(User.class))).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                User arg = (User) invocationOnMock.getArguments()[0];
                User user = UserServiceTestUtility.createDummyUser();
                user.setUserName(arg.getUserName());
                user.setOthers(arg.getOthers());
                user.setContact(arg.getContact());
                user.setEmail(arg.getEmail());
                user.setId(arg.getId());
                user.setName(arg.getName());
                return user;
            }
        });

        User response = userService.updateUser("0", name, "","","");
        assertNotNull(response);
        assertEquals(name, response.getName());
        assertEquals(mockedUser.getEmail(), response.getEmail());
        assertEquals(mockedUser.getContact(), response.getContact());
        assertEquals(mockedUser.getOthers(), response.getOthers());
        assertEquals(mockedUser.getUserName(), response.getUserName());
        assertEquals(mockedUser.getId(), response.getId());
    }

    @Test
    public void updateUserWithOnlyEmail() {
        User mockedUser = UserServiceTestUtility.createDummyUser("0", "Gaurav");
        mockedUser.setEmail("email@domail.com");
        mockedUser.setContact("0909090909");
        mockedUser.setOthers("Other info");
        mockedUser.setUserName("username");
        String email = "email@domain.com";
        PowerMockito.when(userPersistenceLayer.getUser("0")).thenReturn(mockedUser);
        PowerMockito.when(userPersistenceLayer.updateUser(Matchers.any(User.class))).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                User arg = (User) invocationOnMock.getArguments()[0];
                User user = UserServiceTestUtility.createDummyUser();
                user.setUserName(arg.getUserName());
                user.setOthers(arg.getOthers());
                user.setContact(arg.getContact());
                user.setEmail(arg.getEmail());
                user.setId(arg.getId());
                return user;
            }
        });

        User response = userService.updateUser("0", "", email,"","");
        assertNotNull(response);
        assertEquals(mockedUser.getName(), response.getName());
        assertEquals(email, response.getEmail());
        assertEquals(mockedUser.getContact(), response.getContact());
        assertEquals(mockedUser.getOthers(), response.getOthers());
        assertEquals(mockedUser.getUserName(), response.getUserName());
        assertEquals(mockedUser.getId(), response.getId());
    }
}