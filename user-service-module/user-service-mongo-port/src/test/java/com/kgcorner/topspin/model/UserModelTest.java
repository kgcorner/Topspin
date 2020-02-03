package com.kgcorner.topspin.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 24/11/19
 */

public class UserModelTest {

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new UserModel();
    }

    @Test
    public void getId() {
        user.setId("id");
        assertEquals("id",user.getId());
    }

    @Test
    public void setId() {
        user.setId("id");
        assertEquals("id",user.getId());
    }

    @Test
    public void getUserName() {
        user.setUserName("id");
        assertEquals("id",user.getUserName());
    }

    @Test
    public void setUserName() {
        user.setId("id");
        assertEquals("id",user.getId());
    }

    @Test
    public void getName() {
        user.setName("id");
        assertEquals("id",user.getName());
    }

    @Test
    public void setName() {
        user.setName("id");
        assertEquals("id",user.getName());
    }

    @Test
    public void getEmail() {
        user.setEmail("id");
        assertEquals("id",user.getEmail());
    }

    @Test
    public void setEmail() {
        user.setEmail("id");
        assertEquals("id",user.getEmail());
    }

    @Test
    public void getContact() {
        user.setContact("id");
        assertEquals("id",user.getContact());
    }

    @Test
    public void setContact() {
        user.setContact("id");
        assertEquals("id",user.getContact());
    }

    @Test
    public void getOthers() {
        user.setOthers("id");
        assertEquals("id",user.getOthers());
    }

    @Test
    public void setOthers() {
        user.setOthers("id");
        assertEquals("id",user.getOthers());
    }

    @Test
    public void isActive() {
        user.setActive(true);
        assertEquals(true,user.isActive());
    }

    @Test
    public void setActive() {
        user.setActive(false);
        assertEquals(false,user.isActive());
    }
}