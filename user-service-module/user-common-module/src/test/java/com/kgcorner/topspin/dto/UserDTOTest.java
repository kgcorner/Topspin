package com.kgcorner.topspin.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 18/08/21
 */

public class UserDTOTest {
    private UserDTO userDTO;
    private String id = "id";
    private String userName = "userName";
    private String name = "name";
    private String email = "email";
    private String contact = "0987654321";
    private String others = "otherS";
    private String gender = "female";
    private boolean active = true;
    @Before
    public void setUp() throws Exception {
        userDTO = new UserDTO();
    }

    @Test
    public void getLinks() {
        userDTO.setActive(active);
        userDTO.setContact(contact);
        userDTO.setEmail(email);
        userDTO.setGender(gender);
        userDTO.setName(name);
        userDTO.setOthers(others);
        userDTO.setUserName(userName);
        userDTO.setId(id);
        userDTO.addLink("selfLink", "self");
        assertEquals(name, userDTO.getName());
        assertEquals(id, userDTO.getId());
        assertEquals(userName, userDTO.getUserName());
        assertEquals(email, userDTO.getEmail());
        assertEquals(contact, userDTO.getContact());
        assertEquals(others, userDTO.getOthers());
        assertEquals(gender, userDTO.getGender());
        assertEquals(active, userDTO.isActive());
        assertEquals(1, userDTO.getLinks().size());
        assertEquals("selfLink", userDTO.getLinks().get(0).getHref());
    }
}