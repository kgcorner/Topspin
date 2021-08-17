package com.kgcorner.topspin.services;

import com.kgcorner.exceptions.ResourceNotFoundException;
import com.kgcorner.topspin.dto.UserDTO;
import com.kgcorner.topspin.model.AbstractUser;
import com.kgcorner.topspin.persistence.UserPersistenceLayer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 14/02/20
 */

public class UserServiceTest {

    private UserService userService;
    private UserPersistenceLayer userPersistenceLayer;
    @Before
    public void setUp() throws Exception {
        userService = new UserService();
        userPersistenceLayer = PowerMockito.mock(UserPersistenceLayer.class);
        Whitebox.setInternalState(userService, "userPersistenceLayer", userPersistenceLayer);
    }

    @Test
    public void getUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId("1");
        userDTO.setName("Gaurav");
        when(userPersistenceLayer.getUser("1"))
            .thenReturn(userDTO);
        UserDTO response = userService.getUser("1");
        Assert.assertNotNull("response is null", response);
        Assert.assertEquals("Name is not matching", "Gaurav", response.getName());
        Assert.assertEquals("id is not matching", "1", response.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getUserFail() {
        when(userPersistenceLayer.getUser("1"))
            .thenReturn(null);
        userService.getUser("1");
    }

    @Test
    public void testGetUsers() {
        List<AbstractUser> users = new ArrayList<>();
        for (int i = 0; i <20; i++) {
            users.add(new UserDTO());
        }
        when(userPersistenceLayer.getUsers(1, 20)).thenReturn(users);
        List<UserDTO> response = userService.getAllUsers(1, 20);
        assertNotNull("Response is null", response);
        assertEquals("User count isn't matching", users.size(), response.size());
    }

    @Test
    public void createUser() {
        UserDTO  userDTO = new UserDTO();
        userDTO.setName("Gaurav");
        userDTO.setEmail("a@b.com");
        userDTO.setUserName("gaurav");
        AbstractUser mockedUser = mock(AbstractUser.class);
        when(mockedUser.getName()).thenReturn("Gaurav");
        when(userPersistenceLayer.createUser(userDTO)).thenReturn(mockedUser);
        UserDTO response = userService.createUser(userDTO);
        assertNotNull(response);
        assertEquals("Name is not matching", "Gaurav", response.getName());
    }

    @Test
    public void createUserWithBlankName() {
        try {
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail("a@b.com");
            UserDTO response = userService.createUser(userDTO);
        } catch (IllegalArgumentException x) {
            assertEquals("Name can't be blank", x.getMessage());
        }
    }

    @Test
    public void createUserWithBlankUserName() {
        try {
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail("a@b.com");
            userDTO.setName("name");
            userService.createUser(userDTO);
        } catch (IllegalArgumentException x) {
            assertEquals("Username can't be blank", x.getMessage());
        }
    }

    @Test
    public void createUserWithInvalidEmail() {
        try {
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail("email");
            userService.createUser(userDTO);
        } catch (IllegalArgumentException x) {
            assertEquals("Invalid email", x.getMessage());
        }
    }


    @Test
    public void updateUser() {
        UserDTO mockedUser = new UserDTO();
        mockedUser.setEmail("email@domail.com");
        mockedUser.setContact("0909090909");
        mockedUser.setOthers("Other info");
        mockedUser.setUserName("username");
        String id = "0";
        String name = "new name";
        String email = "email@email.com";
        String contact = "new contact";
        String other = "new other info";
        when(userPersistenceLayer.getUser("0")).thenReturn(mockedUser);
        when(userPersistenceLayer.updateUser(mockedUser, id)).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                UserDTO user = (UserDTO) invocationOnMock.getArguments()[0];
                user.setName(name);
                user.setEmail(email);
                user.setContact(contact);
                user.setOthers(other);
                user.setId(id);
                return user;
            }
        });

        UserDTO response = userService.updateUser(id, mockedUser);
        assertNotNull(response);
        assertEquals(name, response.getName());
        assertEquals(email, response.getEmail());
        assertEquals(contact, response.getContact());
        assertEquals(other, response.getOthers());
        assertEquals(mockedUser.getUserName(), response.getUserName());
        assertEquals(id, response.getId());
    }

    @Test
    public void testDelete() {
        String userId = "id";
        userService.deleteUser(userId);
        Mockito.verify(userPersistenceLayer).deleteUser(userId);
    }
}