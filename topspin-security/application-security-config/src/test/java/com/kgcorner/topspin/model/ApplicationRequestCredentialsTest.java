package com.kgcorner.topspin.model;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 14/08/21
 */

public class ApplicationRequestCredentialsTest {
    private ApplicationRequestCredentials credentials;

    @Before
    public void setUp() throws Exception {
        credentials = new ApplicationRequestCredentials();
        credentials.setApplicationName("app");
    }

    @Test
    public void getAuthorities() {
        Collection<? extends GrantedAuthority> authorities = credentials.getAuthorities();
        assertEquals(1, authorities.size());
        assertEquals("ROLE_APPLICATION", ((SimpleGrantedAuthority)authorities.toArray()[0]).getAuthority());
    }

    @Test
    public void getCredentials() {
        assertEquals(credentials, credentials.getCredentials());
    }

    @Test
    public void getDetails() {
        assertNull(credentials.getDetails());
    }

    @Test
    public void getPrincipal() {
        String name = "application";
        credentials.setApplicationName(name);
        assertEquals(name, credentials.getPrincipal());
    }

    @Test
    public void isAuthenticated() {
        assertFalse(credentials.isAuthenticated());
    }

    @Test
    public void setAuthenticated() {
        credentials.setAuthenticated(true);
        assertTrue(credentials.isAuthenticated());
    }

    @Test
    public void getName() {
        String name = "application";
        credentials.setApplicationName(name);
        assertEquals(name, credentials.getName());
    }

    @Test
    public void allOthers() {

        credentials.setApplicationName("name");
        assertEquals("name", credentials.getApplicationName());

        credentials.setRequestSentAt("123");
        assertEquals("123", credentials.getRequestSentAt());

        credentials.setApplicationKey("key");
        assertEquals("key", credentials.getApplicationKey());

        credentials.setHash("hash");
        assertEquals("hash", credentials.getHash());
        assertNotEquals(0, credentials.hashCode());
        assertNotNull(credentials.toString());
        assertFalse(credentials.equals(new Object()));
    }
}