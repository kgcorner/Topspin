package com.kgcorner.topspin;

import com.kgcorner.topspin.dto.UserDTO;
import com.kgcorner.topspin.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.when;
/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/02/20
 */

public class UserResourceTest {

    private UserService userService;
    private UserResource userResource;
    @Before
    public void setUp() throws Exception {
        userService = PowerMockito.mock(UserService.class);
        userResource = new UserResource();
        Whitebox.setInternalState(userResource, "userService", userService);
    }

    @Test
    public void getUser() {
        UserDTO dummyUser = new UserDTO();
        dummyUser.setId("0");
        when(userService.getUser("0")).thenReturn(dummyUser);
        ResponseEntity<UserDTO> userResponse = userResource.getUser("0");
        assertEquals("User id isn't matching", dummyUser.getId(), userResponse.getBody().getId());
        assertEquals("User name isn't matching", dummyUser.getName(), userResponse.getBody().getName());
    }

    @Test
    public void getAllUsers() {
        UserDTO user1 = new UserDTO();
        UserDTO user2 = new UserDTO();
        List<UserDTO> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        user1.setId("0");
        user2.setId("1");
        user1.setName("Gaurav");
        user2.setName("Nakku");
        when(userService.getAllUsers(1, 2)).thenReturn(users);
        ResponseEntity<Resources<UserDTO>> allUsers = userResource.getAllUsers(1, 2);
        Collection<UserDTO> allUsersCollection = allUsers.getBody().getContent();
        List<UserDTO> result = new ArrayList<>(allUsersCollection);
        assertEquals("User count", users.size(), result.size());
        assertEquals("User name isn't matching", users.get(0).getName(), result.get(0).getName());
        assertEquals("User name isn't matching", users.get(1).getName(), result.get(1).getName());
        assertEquals("User id isn't matching", users.get(0).getId(), result.get(0).getId());
        assertEquals("User id isn't matching", users.get(1).getId(), result.get(1).getId());

    }

    @Test
    public void createUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId("0");
        userDTO.setName("Gaurav");
        when(userService.createUser(userDTO)).thenReturn(userDTO);
        ResponseEntity<UserDTO> user = userResource.createUser(userDTO);
        assertNotNull(user);
        assertEquals("Name is not matching", userDTO.getName(), user.getBody().getName());
    }

    @Test
    public void updateUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId("0");
        userDTO.setName("Gaurav");
        when(userService.updateUser("0", userDTO)).thenReturn(userDTO);
        ResponseEntity<UserDTO> userDTOResponseEntity = userResource.updateUser("0", userDTO);
        assertNotNull(userDTOResponseEntity);
        assertEquals(userDTO.getId(), userDTOResponseEntity.getBody().getId());
        assertEquals(userDTO.getName(), userDTOResponseEntity.getBody().getName());
    }

    @Test
    public void deleteTest() {
        String userId = "abc";
        userResource.deleteUser(userId);
        Mockito.verify(userService).deleteUser(userId);
    }

    @Test
    public void testHealth() {
        assertEquals("Ok", userResource.getHealth());
    }

    @Test
    public void getUserByUsername() {
        String username = "username";
        UserDTO userDTO = new UserDTO();
        userDTO.setId("id");
        userDTO.setUserName(username);
        when(userService.getUserByUsername(username)).thenReturn(userDTO);
        ResponseEntity<UserDTO> userByUsername = userResource.getUserByUsername(username);
        assertEquals(username, userByUsername.getBody().getUserName());
    }
}